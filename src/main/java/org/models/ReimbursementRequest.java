package org.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Entity
@Table(name = "proj01_reimb_req")
public class ReimbursementRequest {
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

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
  @JoinColumn(name = "handled_by", referencedColumnName = "id")
  private Manager handledByManager;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date tsDate;

  @Column
  @Enumerated(EnumType.STRING)
  private ReimbursementState state;

  @Column
  private String mgrComment;

  @Column(name = "request_reason")
  private String requestReason;

  public String getRequestReason() {
    return requestReason;
  }

  public void setRequestReason(String requestReason) {
    this.requestReason = requestReason;
  }

  public String getMgrComment() {
    return mgrComment;
  }

  public void setMgrComment(String mgrComment) {
    this.mgrComment = mgrComment;
  }

  public Employee getRequestedByEmployee() {
    return requestedByEmployee;
  }

  public void setRequestedByEmployee(Employee requestedByEmployee) {
    this.requestedByEmployee = requestedByEmployee;
  }

  public ReimbursementState getState() {
    return state;
  }

  public void setState(ReimbursementState state) {
    this.state = state;
  }

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

  public Double getReqAmnt() {
    return reqAmnt;
  }

  public void setReqAmnt(Double reqAmnt) {
    this.reqAmnt = reqAmnt;
  }

  public Manager getHandledByManager() {
    return handledByManager;
  }

  public void setHandledByManager(Manager handledByManager) {
    this.handledByManager = handledByManager;
  }

  @Override
  public String toString() {
    return "ReimbursementRequest [handledByManager=" + handledByManager + ", id=" + id + ", mgrComment=" + mgrComment
        + ", reqAmnt=" + reqAmnt + ", requestReason=" + requestReason + ", requestedByEmployee=" + requestedByEmployee
        + ", state=" + state + ", tsDate=" + tsDate + "]";
  }
}
