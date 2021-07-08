package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "proj01_emps")
public class Employee extends Person {
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJ01_EMP_SEQ")
  @SequenceGenerator(name = "PROJ01_EMP_SEQ", sequenceName = "PROJ01_EMP_S", allocationSize = 1)
  @Column
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "manager_id", referencedColumnName = "id")
  private Manager manager;

  @Transient
  private Boolean isManager;

  public Employee() {
    isManager = false;
  }

  public Boolean getIsManager() {
    return isManager;
  }

  public void setIsManager(Boolean isManager) {
    this.isManager = isManager;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + "]";
  }
}
