package id.co.javan.workshop.latihancamunda.controller;

import id.co.javan.workshop.latihancamunda.service.CamundaProcessService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
}
