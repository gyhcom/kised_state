package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import state.member.domain.entity.BatchHis;

public interface JpaBatchHisRepository extends JpaRepository<BatchHis, Long>, JpaSpecificationExecutor<BatchHis> {

}
