package id.co.javan.workshop.latihancamunda.service.impl;

import id.co.javan.workshop.latihancamunda.model.PengajuanCuti;
import id.co.javan.workshop.latihancamunda.repository.PengajuanCutiRepository;
import id.co.javan.workshop.latihancamunda.service.CamundaProcessService;
import id.co.javan.workshop.latihancamunda.service.PengajuanCutiService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PengajuanCutiServiceImpl implements PengajuanCutiService {
    private PengajuanCutiRepository pengajuanCutiRepository;
    private CamundaProcessService camundaProcessService;

    @Autowired
    public PengajuanCutiServiceImpl(PengajuanCutiRepository pengajuanCutiRepository, CamundaProcessService camundaProcessService) {
        this.pengajuanCutiRepository = pengajuanCutiRepository;
        this.camundaProcessService = camundaProcessService;
    }

    @Override
    public PengajuanCuti start(String nama, Date tanggal) {
        PengajuanCuti cuti = new PengajuanCuti();
        cuti.setNama(nama);
        cuti.setTanggal(tanggal);
        cuti = pengajuanCutiRepository.save(cuti);

        Map<String, Object> variables = new HashMap<>();
        variables.put("tanggal", tanggal);
        ProcessInstance pe = camundaProcessService.startProcess("pengajuan_cuti", String.valueOf(cuti.getId()), variables);
        cuti.setProcessInstanceId(pe.getProcessInstanceId());

        return pengajuanCutiRepository.save(cuti);
    }
}
