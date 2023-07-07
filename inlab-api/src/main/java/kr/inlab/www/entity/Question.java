package kr.inlab.www.entity;

import java.util.ArrayList;
import java.util.List;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(1) default 'N'")
    private YesNo isDeleted;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionVersion> questionVersionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_type_id")
    private QuestionType questionType;

    @Builder
    public Question(Position position, QuestionType questionType) {
        this.position = position;
        this.questionType = questionType;
    }

    // update 메서드
    public void updatePosition(Position newPosition) {
        this.position = newPosition;
    }

    public void updateQuestionType(QuestionType newQuestionType) {
        this.questionType = newQuestionType;
    }

}
