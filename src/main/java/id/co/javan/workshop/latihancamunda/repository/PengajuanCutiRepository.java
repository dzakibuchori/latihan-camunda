package id.co.javan.workshop.latihancamunda.repository;

import id.co.javan.workshop.latihancamunda.model.PengajuanCuti;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PengajuanCutiRepository extends JpaRepository<PengajuanCuti, Long> {
    @Query("select c from PengajuanCuti c where c.processInstanceId in (:ids)")
    Page<PengajuanCuti> findAllByProcessInstanceId(@Param("ids") List<String> processInstanceIds, Pageable pageable);
}
