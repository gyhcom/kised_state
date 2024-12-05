package state.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.member.presentation.response.DepartmentResponse;
import state.member.presentation.response.PositionResponse;

import java.util.ArrayList;
import java.util.List;

@ToString
@Builder
@Setter
@Getter
@Table(name = "POSITION")
@Entity
public class Position {
    @Id
    @Column(name = "PSIT_CD")
    String positionCode;

    @Column(name = "PSIT_NM")
    String positionName;

    public Position() {}

    public Position(String positionCode, String positionName) {
        this.positionCode = positionCode;
        this.positionName = positionName;
    }

    public List<PositionResponse> toCommand(List<Position> positions) {
        List<PositionResponse> positionCommand = new ArrayList<>();
        for (Position value : positions) {
            positionCommand.add(
                    PositionResponse.builder()
                            .positionCode(value.getPositionCode())
                            .positionName(value.getPositionName())
                            .build()
            );
        }

        return positionCommand;
    }
}
