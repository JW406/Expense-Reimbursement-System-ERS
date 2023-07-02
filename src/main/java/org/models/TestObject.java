package org.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TestObject_test_")
public class TestObject {
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "testObj_id")
  // , columnDefinition = "primary key"
  private int id;
  @Column(name = "name")
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TestObject(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public TestObject() {
  }

  @Override
  public String toString() {
    return "TestObject [id=" + id + ", name=" + name + "]";
  }
}
