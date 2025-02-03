package state.member.infrastructure;

import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;

public class WebClientUtil {
    /**
     * param에 담긴 값을 쿼리 파라미터로 변경
     * @param uriBuilder
     * @param path
     * @param params
     * @return
     */
    public static URI buildUriWithParams(UriBuilder uriBuilder, String path, Map<String, Object> params) {
        UriBuilder builder = uriBuilder.path(path);
        params.forEach(builder::queryParam); // Map의 키-값 쌍을 쿼리 파라미터로 추가
        return builder.build(); // String이 아닌 URI를 반환
    }
}
