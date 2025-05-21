package state.member.infrastructure;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.BatchHis;
import state.member.domain.repository.BatchHisRepository;
import state.member.infrastructure.JPA.JpaBatchHisRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BatchHisRepositoryAdapter implements BatchHisRepository {
    private final JpaBatchHisRepository jpaBatchHisRepository;

    @Override
    public List<BatchHis> batchHisList(String stepName, String status, String startTime, String endTime) {
        Specification<BatchHis> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(stepName != null && !stepName.isBlank()) {
                predicates.add(cb.like(root.get("stepName"), "%" + stepName + "%"));
            }

            if(status != null && !status.isBlank()) {
                predicates.add(cb.like(root.get("status"), "%" + status + "%"));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            if(startTime != null && endTime != null) {
                predicates.add(
                        cb.between(root.get("startTime"), LocalDate.parse(startTime, formatter), LocalDate.parse(endTime, formatter))
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "startTime");

        return jpaBatchHisRepository.findAll(spec, sort);
    }
}
