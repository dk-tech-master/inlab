package kr.inlab.www.entity;

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
}
