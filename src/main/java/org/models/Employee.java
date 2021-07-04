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
  @Column
  private String email;
  @Column
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "manager_id", referencedColumnName = "id")
  private Manager manager;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  @Override
  public String toString() {
    return "Employee [email=" + email + ", id=" + id + ", name=" + name + "]";
  }
}
