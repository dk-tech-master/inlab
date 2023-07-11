package kr.inlab.www.entity;

import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelatedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatedQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_question_id")
    private Question headQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tail_question_id")
    private Question tailQuestion;

    @Builder
    public RelatedQuestion(Question headQuestion, Question tailQuestion) {
        this.headQuestion = headQuestion;
        this.tailQuestion = tailQuestion;
    }

    public ResponseGetQuestionsDto toResponseQuestionsDto() {
        QuestionVersion result = this.tailQuestion.getQuestionVersionList().stream()
            .filter(questionVersion -> questionVersion.getIsLatest().equals(YesNo.Y)).findFirst().get();
        return ResponseGetQuestionsDto.builder()
            .title(result.getTitle())
            .questionTypeId(this.tailQuestion.getQuestionType().getQuestionTypeId())
            .questionTypeName(this.tailQuestion.getQuestionType().getQuestionTypeName())
            .positionId(this.tailQuestion.getPosition().getPositionId())
            .positionName(this.tailQuestion.getPosition().getPositionName())
            .questionLevelId(result.getQuestionLevel().getQuestionLevelId())
            .questionLevelName(result.getQuestionLevel().getQuestionLevelName())
            .version(result.getVersion())
            .build();
    }
}
