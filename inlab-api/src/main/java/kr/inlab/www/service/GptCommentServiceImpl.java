package kr.inlab.www.service;

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

        String gptResponse = chatgptService.sendMessage(gptComment.getRequestContent());
        gptComment.saveGptResponse(gptResponse);

        return ResponseGptCommentDto.builder()
                .gptCommentId(gptCommentId)
                .requestContent(gptComment.getRequestContent())
                .responseContent(gptResponse)
                .build();
    }

    private String getRequestContent(String title, List<Checklist> checklists, String content) {
        String result = "";

        result += "면집 질문, 그에대한 답변, 답변을 평가할 수 있는 체크리스트를 줄게. 너가 체크리스트를 기준으로 질문에 대한 답변을 잘 했는지 평가해줘. 평가에 대한 응답은 json 형태로 부탁해.\n";
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
