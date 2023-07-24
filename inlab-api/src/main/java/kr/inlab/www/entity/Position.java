package kr.inlab.www.entity;

import kr.inlab.www.dto.common.PositionDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    @NotNull
    private String positionName;

    @OneToMany(mappedBy = "position")
    private List<Question> questionList;

    @Builder
    public Position(Integer positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public void updateName(String positionName) {
        this.positionName = positionName;
    }

    public PositionDto toDto() {
        return PositionDto.builder()
                .positionId(this.positionId)
                .positionName(this.positionName)
                .build();
    }
}
