package id.co.javan.workshop.latihancamunda.controller;

import id.co.javan.workshop.latihancamunda.service.CamundaProcessService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pengajuan-cuti")
public class PengajuanCutiController {
    private CamundaProcessService camundaProcessService;

    @Autowired
    public PengajuanCutiController(CamundaProcessService camundaProcessService) {
        this.camundaProcessService = camundaProcessService;
    }

    @PostMapping
    public String start(@RequestParam("nama") String nama,
                        @RequestParam("tanggal")
                        @DateTimeFormat(pattern = "dd-MM-yyyy") Date tanggal) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("tanggal", tanggal);
        ProcessInstance pe = camundaProcessService.startProcess("pengajuan_cuti", nama, variables);
        return pe.getBusinessKey();
    }

    @GetMapping("/approval-atasan")
    public List<String> listApprovalAtasan() {
        List<Task> tasks = camundaProcessService.getActiveTasks("pengajuan_cuti", "approval_atasan");
        return tasks.stream().map(Task::getProcessInstanceId).collect(Collectors.toList());
    }
}
