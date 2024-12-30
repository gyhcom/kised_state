package state.member.presentation.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class TempRequestDto {
    String year;
    double usage;
}
