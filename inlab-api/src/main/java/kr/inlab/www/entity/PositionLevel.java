package kr.inlab.www.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionLevelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_level_id")
    private QuestionLevel questionLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PositionLevel(Position position, QuestionLevel questionLevel, User user) {
        this.position = position;
        this.questionLevel = questionLevel;
        this.user = user;
    }
}
