package kr.inlab.www.entity;

import kr.inlab.www.dto.common.QuestionLevelDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionLevelId;

    @NotNull
    private String questionLevelName;

    @Builder
    public QuestionLevel(String questionLevelName) {
        this.questionLevelName = questionLevelName;
    }

    public QuestionLevelDto toDto() {
        return QuestionLevelDto.builder()
                .questionLevelId(this.questionLevelId)
                .questionLevelName(this.questionLevelName)
                .build();
    }
}
