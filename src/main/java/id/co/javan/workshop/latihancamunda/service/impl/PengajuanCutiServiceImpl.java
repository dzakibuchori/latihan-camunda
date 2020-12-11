package id.co.javan.workshop.latihancamunda.service.impl;

import id.co.javan.workshop.latihancamunda.model.PengajuanCuti;
import id.co.javan.workshop.latihancamunda.repository.PengajuanCutiRepository;
import id.co.javan.workshop.latihancamunda.service.CamundaProcessService;
import id.co.javan.workshop.latihancamunda.service.PengajuanCutiService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PengajuanCutiServiceImpl implements PengajuanCutiService {
    private PengajuanCutiRepository pengajuanCutiRepository;
    private CamundaProcessService camundaProcessService;

    private static final String PROCESS_DEF_KEY = "pengajuan_cuti";

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
        ProcessInstance pe = camundaProcessService.startProcess(PROCESS_DEF_KEY, String.valueOf(cuti.getId()), variables);
        cuti.setProcessInstanceId(pe.getProcessInstanceId());

        return pengajuanCutiRepository.save(cuti);
    }

    @Override
    public Page<PengajuanCuti> listApproveAtasan(Pageable pageable) {
        List<Task> tasks = camundaProcessService.getActiveTasks(PROCESS_DEF_KEY, "approval_atasan");
        List<String> processInstanceIds = tasks.stream().map(Task::getProcessInstanceId).collect(Collectors.toList());
        return pengajuanCutiRepository.findAllByProcessInstanceId(processInstanceIds, pageable);
    }
}
