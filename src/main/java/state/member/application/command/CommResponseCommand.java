package state.member.application.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CommResponseCommand {
    private String version;
    private Map<String, Object> meta;
    private List<Map<String, Object>> data;

    public Boolean isEmpty() {
        if(version == null || version.isBlank()) return true;
        else if(meta == null || meta.isEmpty()) return true;
        else if(data == null || data.isEmpty()) return true;
        return false;
    }
}
