package state.member.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PositionResponse {
    String positionCode;
    String positionName;
}
