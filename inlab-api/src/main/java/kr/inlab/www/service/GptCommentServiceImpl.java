package kr.inlab.www.service;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import kr.inlab.www.common.exception.GptCommentNotFoundException;
import kr.inlab.www.dto.response.ResponseGptCommentDto;
import kr.inlab.www.dto.response.ResponseGptCommentIdDto;
import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.GptComment;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.repository.GptCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GptCommentServiceImpl implements GptCommentService {

    private final GptCommentRepository gptCommentRepository;
    private final ChecklistService checklistService;
    private final ChatgptService chatgptService;

    @Transactional
    @Override
    public void createGptComment(InterviewQuestion interviewQuestion, InterviewQuestionResult interviewQuestionResult, String interviewAnswerContent) {
        String questionVersionTitle = interviewQuestion.getQuestionVersion().getTitle();
        List<Checklist> checklists = checklistService.getChecklists(interviewQuestion.getQuestionVersion());
        GptComment gptComment = GptComment.builder()
                .requestContent(getRequestContent(questionVersionTitle, checklists, interviewAnswerContent))
                .interviewQuestionResult(interviewQuestionResult)
                .build();

        gptCommentRepository.save(gptComment);
    }

    @Override
    public ResponseGptCommentDto getInterviewResultGptComment(InterviewQuestionResult interviewQuestionResult) {
        GptComment gptComment = gptCommentRepository.findByInterviewQuestionResult(interviewQuestionResult)
                .orElseThrow(GptCommentNotFoundException::new);

        return gptComment.toResponseGptCommentDto();
    }

    @Transactional
    @Override
    public ResponseGptCommentDto getGptComment(Long gptCommentId) {
        GptComment gptComment = gptCommentRepository.findById(gptCommentId)
                .orElseThrow(GptCommentNotFoundException::new);

        ChatResponse chatResponse = chatgptService.sendChatRequest(new ChatRequest("text-davinci-003", gptComment.getRequestContent(), 2000, 1.0, 1.0));
        String gptResponse = chatResponse.getChoices().get(0).getText();
        gptComment.saveGptResponse(gptResponse);

        return gptComment.toResponseGptCommentDto();
    }

    private String getRequestContent(String title, List<Checklist> checklists, String content) {
        String result = "";

        result += "다음 제공된 조건으로 개발자 채용 면접의 질문과 답변과 체크리스트를 분석하고, 평가 결과를 반환해주세요.\n1. 질문에 대한 답변 안에 각 체크리스트의 내용이 포함되어 있는지 확인하고, 질문과 체크리스트를 기준으로 답변을 평가한 후 피드백을 해주세요.\n2. 면접자의 답변이 각 체크리스트에 충족되는지 여부(yes,no,partially)를 알려주세요.\n3. 면접자의 답변의 피드백을 해주세요.\n4. 면접자의 답변을 최종 평가해주세요.\n\n";
        result += "면접 질문: ";
        result += title + "\n";
        result += "답변: ";
        result += content + "\n";
        result += "체크리스트: \n";
        for (int i = 0; i < checklists.size(); i++) {
            result += (i + 1) + ". " + checklists.get(i).getContent() + "\n";
        }

        return result;
    }
}
