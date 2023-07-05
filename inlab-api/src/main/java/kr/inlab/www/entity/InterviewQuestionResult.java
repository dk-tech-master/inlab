package kr.inlab.www.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class InterviewQuestionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewQuestionResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_id")
    private InterviewQuestion interviewQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interivew_result_id")
    private InterviewResult interviewResult;

    @Builder
    public InterviewQuestionResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult) {
        this.interviewQuestion = interviewQuestion;
        this.interviewResult = interviewResult;
    }
}
