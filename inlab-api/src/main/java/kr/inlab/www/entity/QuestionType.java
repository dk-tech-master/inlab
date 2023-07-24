package kr.inlab.www.entity;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionTypeId;

    @NotNull
    private String questionTypeName;

    @OneToMany(mappedBy = "questionType" , fetch = FetchType.LAZY)
    private List<Question> questionList;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Builder
    public QuestionType(Position position, String questionTypeName) {
        this.position = position;
        this.questionTypeName = questionTypeName;
    }

    public void updateQuestionTypeName(String questionTypeName){
        this.questionTypeName = questionTypeName;
    }
}
