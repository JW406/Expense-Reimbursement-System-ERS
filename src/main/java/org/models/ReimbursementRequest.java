package org.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "proj01_reimb_req")
public class ReimbursementRequest {
  @JsonIgnore
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJ01_REIMB_REQ_SEQ")
  @SequenceGenerator(name = "PROJ01_REIMB_REQ_SEQ", sequenceName = "PROJ01_REIMB_REQ_S", allocationSize = 1)
  private Integer id;
  @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "requested_by", referencedColumnName = "id")
  private Employee requestedByEmployee;
  @Column(name = "request_amnt")
  private Double reqAmnt;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "approved_by", referencedColumnName = "id")
  private Manager approvedByManager;
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date tsDate;

  public Date getTsDate() {
    return tsDate;
  }

  public void setTsDate(Date tsDate) {
    this.tsDate = tsDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Employee getRequestedbyEmployee() {
    return requestedByEmployee;
  }

  public void setRequestedbyEmployee(Employee requestedbyEmployee) {
    this.requestedByEmployee = requestedbyEmployee;
  }

  public Double getReqAmnt() {
    return reqAmnt;
  }

  public void setReqAmnt(Double reqAmnt) {
    this.reqAmnt = reqAmnt;
  }

  public Manager getApprovedByManager() {
    return approvedByManager;
  }

  public void setApprovedByManager(Manager approvedByManager) {
    this.approvedByManager = approvedByManager;
  }

  @Override
  public String toString() {
    return "ReimbursementRequest [approvedByManager=" + approvedByManager + ", id=" + id + ", reqAmnt=" + reqAmnt
        + ", requestedbyEmployee=" + requestedByEmployee + ", tsDate=" + tsDate + "]";
  }
}
