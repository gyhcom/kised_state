package state.member.presentation.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Deprecated
@ToString
@Setter
@Getter
public class TempRequestDto {
    String year;
    String month;
    String week;
    double usage;
}
