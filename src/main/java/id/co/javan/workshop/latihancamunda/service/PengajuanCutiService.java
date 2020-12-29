package id.co.javan.workshop.latihancamunda.service;

import id.co.javan.workshop.latihancamunda.model.PengajuanCuti;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface PengajuanCutiService {
    PengajuanCuti start(String nama, Date tanggal);

    Page<PengajuanCuti> listApproveAtasan(Pageable pageable);

    PengajuanCuti approveAtasan(Long id);

    PengajuanCuti rejectAtasan(Long id);
}
