package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import state.member.application.processor.gslsEsb.*;

import java.util.Map;

@Transactional
@Service
public class GslsEsbManager {
    private final GslsEsbGetInfoPubNotiProcessor gslsEsbGetInfoPubNotiProcessor;
    private final GslsEsbGetRveExptrProcessor gslsEsbGetRveExptrProcessor;
    private final GslsEsbGetFnlsttProcessor gslsEsbGetFnlsttProcessor;
    private final GslsEsbGetExcutKstupProcessor gslsEsbGetExcutKstupProcessor;
    private final GslsEsbGetDtlBsnsInfoProcessor gslsEsbGetDtlBsnsInfoProcessor;

    public GslsEsbManager(GslsEsbGetInfoPubNotiProcessor gslsEsbGetInfoPubNotiProcessor,
                          GslsEsbGetRveExptrProcessor gslsEsbGetRveExptrProcessor,
                          GslsEsbGetFnlsttProcessor gslsEsbGetFnlsttProcessor,
                          GslsEsbGetExcutKstupProcessor gslsEsbGetExcutKstupProcessor,
                          GslsEsbGetDtlBsnsInfoProcessor gslsEsbGetDtlBsnsInfoProcessor) {
        this.gslsEsbGetInfoPubNotiProcessor = gslsEsbGetInfoPubNotiProcessor;
        this.gslsEsbGetRveExptrProcessor = gslsEsbGetRveExptrProcessor;
        this.gslsEsbGetFnlsttProcessor = gslsEsbGetFnlsttProcessor;
        this.gslsEsbGetExcutKstupProcessor = gslsEsbGetExcutKstupProcessor;
        this.gslsEsbGetDtlBsnsInfoProcessor = gslsEsbGetDtlBsnsInfoProcessor;
    }

    public Flux<Map<String, Object>> getInfoPubNotiAnnCnt() {
        return gslsEsbGetInfoPubNotiProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getInfoPubNotiMonCnt(String year) {
        return gslsEsbGetInfoPubNotiProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getInfoPubNoti(String year, String month, String searchValue) {
        return gslsEsbGetInfoPubNotiProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getRveExptrAnnCnt() {
        return gslsEsbGetRveExptrProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getRveExptrMonCnt(String year) {
        return gslsEsbGetRveExptrProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getRveExptr(String year, String month, String searchValue) {
        return gslsEsbGetRveExptrProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getFnlsttAnnCnt() {
        return gslsEsbGetFnlsttProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getFnlsttMonCnt(String year) {
        return gslsEsbGetFnlsttProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getFnlstt(String year, String month, String searchValue) {
        return gslsEsbGetFnlsttProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getExcutAnnCnt() {
        return gslsEsbGetExcutKstupProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getExcutMonCnt(String year) {
        return gslsEsbGetExcutKstupProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getExcutKstup(String year, String month, String searchValue) {
        return gslsEsbGetExcutKstupProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getDtlBsnsInfoAnnCnt() {
        return gslsEsbGetDtlBsnsInfoProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getDtlBsnsInfoMonCnt(String year) {
        return gslsEsbGetDtlBsnsInfoProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getDtlBsnsInfo(String year, String month, String searchValue) {
        return gslsEsbGetDtlBsnsInfoProcessor.dtlInfoExecute(year, month, searchValue);
    }
}
