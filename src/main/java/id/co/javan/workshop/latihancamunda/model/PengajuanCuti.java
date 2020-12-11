package id.co.javan.workshop.latihancamunda.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pengajuan_cuti")
public class PengajuanCuti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;

    @Temporal(TemporalType.DATE)
    private Date tanggal;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
