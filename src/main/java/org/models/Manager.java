package org.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "proj01_mgrs")
public class Manager extends Person {
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJ01_MGR_SEQ")
  @SequenceGenerator(name = "PROJ01_MGR_SEQ", sequenceName = "PROJ01_MGR_S", allocationSize = 1)
  @Column
  private Integer id;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
  private List<Employee> managedEmployees;

  @Transient
  private Boolean isManager;

  public Manager() {
    isManager = true;
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

  @JsonIgnore
  public List<Employee> getManagedEmployees() {
    return managedEmployees;
  }

  public void setManagedEmployees(List<Employee> managedEmployees) {
    this.managedEmployees = managedEmployees;
  }

  @Override
  public String toString() {
    return "Manager [id=" + id + ", managedEmployees=" + managedEmployees + "]";
  }
}
