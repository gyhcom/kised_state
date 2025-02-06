package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.GslsEsbManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("/gslsEsb")
@Controller
public class GslsEsbApi {
    private final GslsEsbManager gslsEsbManager;

    public GslsEsbApi(GslsEsbManager gslsEsbManager) {
        this.gslsEsbManager = gslsEsbManager;
    }

    @GetMapping("/pms")
    public String gslsEsbPmsView() {
        return "/services/gslsEsb/gslsEsb-pms";
    }

    @GetMapping("/mis")
    public String gslsEsbMisView() {
        return "/services/gslsEsb/gslsEsb-mis";
    }

    @ResponseBody
    @GetMapping("/getAllGslsEsbCnt")
    public Mono<List<List<Map<String, Object>>>> getAllGslsEsbCnt(@RequestParam String year) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();

        list.add(gslsEsbManager.getInfoPubNotiAnnCnt());     //연도별 정보공시내역 건수
        list.add(gslsEsbManager.getInfoPubNotiMonCnt(year)); //월별 정보공시내역 건수
        list.add(gslsEsbManager.getRveExptrAnnCnt());        //연도별 세입세출내역 건수
        list.add(gslsEsbManager.getRveExptrMonCnt(year));    //월별 세입세출내역 건수
        list.add(gslsEsbManager.getFnlsttAnnCnt());          //연도별 재무제표결산 내역 건수
        list.add(gslsEsbManager.getFnlsttMonCnt(year));      //월별 재무제표결산 내역 건수
        list.add(gslsEsbManager.getExcutAnnCnt());           //연도별 수급자집행정보 건수
        list.add(gslsEsbManager.getExcutMonCnt(year));       //월별 수급자집행정보 건수
        list.add(gslsEsbManager.getDtlBsnsInfoAnnCnt());     //연도별 상세내역사업정보 건수
        list.add(gslsEsbManager.getDtlBsnsInfoMonCnt(year)); //월별 상세내역사업정보 건수

        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    @ResponseBody
    @GetMapping("/getInfoPubNotiMonCnt")
    public Flux<Map<String, Object>> getInfoPubNotiMonCnt(@RequestParam String year) {
        return gslsEsbManager.getInfoPubNotiMonCnt(year);
    }

    @ResponseBody
    @GetMapping("/getRveExptrMonCnt")
    public Flux<Map<String, Object>> getRveExptrMonCnt(@RequestParam String year) {
        return gslsEsbManager.getRveExptrMonCnt(year);
    }

    @ResponseBody
    @GetMapping("/getFnlsttMonCnt")
    public Flux<Map<String, Object>> getFnlsttMonCnt(@RequestParam String year) {
        return gslsEsbManager.getFnlsttMonCnt(year);
    }

    @ResponseBody
    @GetMapping("/getExcutMonCnt")
    public Flux<Map<String, Object>> getExcutMonCnt(@RequestParam String year) {
        return gslsEsbManager.getExcutMonCnt(year);
    }

    @ResponseBody
    @GetMapping("/getDtlBsnsInfoMonCnt")
    public Flux<Map<String, Object>> getDtlBsnsInfoMonCnt(@RequestParam String year) {
        return gslsEsbManager.getDtlBsnsInfoMonCnt(year);
    }
}
