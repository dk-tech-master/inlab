package kr.inlab.www.entity;

import kr.inlab.www.common.type.InterviewQuestionStatus;
import kr.inlab.www.common.type.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_version_id")
    private QuestionVersion questionVersion;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(value = EnumType.STRING)
    private InterviewQuestionStatus interviewQuestionStatus;

    @Builder
    public InterviewQuestion(Interview interview, Question question, QuestionVersion questionVersion,InterviewQuestionStatus interviewQuestionStatus) {
        this.interview = interview;
        this.question = question;
        this.questionVersion = questionVersion;
        this.interviewQuestionStatus = interviewQuestionStatus;
    }

    public void updateInterviewQuestion() {
        this.interviewQuestionStatus = InterviewQuestionStatus.DELETE;
    }
}
