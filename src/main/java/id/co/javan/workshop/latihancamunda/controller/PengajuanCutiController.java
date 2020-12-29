package id.co.javan.workshop.latihancamunda.controller;

import id.co.javan.workshop.latihancamunda.model.PengajuanCuti;
import id.co.javan.workshop.latihancamunda.service.CamundaProcessService;
import id.co.javan.workshop.latihancamunda.service.PengajuanCutiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pengajuan-cuti")
public class PengajuanCutiController {
    private CamundaProcessService camundaProcessService;
    private PengajuanCutiService pengajuanCutiService;

    @Autowired
    public PengajuanCutiController(CamundaProcessService camundaProcessService, PengajuanCutiService pengajuanCutiService) {
        this.camundaProcessService = camundaProcessService;
        this.pengajuanCutiService = pengajuanCutiService;
    }

    @PostMapping
    public PengajuanCuti start(@RequestParam("nama") String nama,
                        @RequestParam("tanggal")
                        @DateTimeFormat(pattern = "dd-MM-yyyy") Date tanggal) {
       PengajuanCuti cuti = pengajuanCutiService.start(nama, tanggal);
       return cuti;
    }

    @GetMapping("/approval-atasan")
    public Page<PengajuanCuti> listApprovalAtasan(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return pengajuanCutiService.listApproveAtasan(PageRequest.of(page, size));
    }

    @PostMapping("/approval-atasan/{taskId}")
    public String submitApprovalAtasan(@PathVariable("taskId") String taskId,
                                       @RequestParam("approved") boolean approved) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);
        camundaProcessService.completeTask(taskId, variables);

        return taskId;
    }

    @PostMapping("/approval-atasan/{id}/approve")
    public PengajuanCuti approveAtasan(@PathVariable("id") Long id) {
        return pengajuanCutiService.approveAtasan(id);
    }

    @PostMapping("/approval-atasan/{id}/reject")
    public PengajuanCuti rejectAtasan(@PathVariable("id") Long id) {
        return pengajuanCutiService.rejectAtasan(id);
    }
}
