package com.student.entities;

import javax.persistence.*;

@Entity
@Table
public class Student {

  @Id
  @GeneratedValue(generator = "uuid")
  private Long id;

  private String name;

  private String rollNumber;

  private Integer subject1;

  private Integer subject2;

  private Integer subject3;

  private Integer subject4;

  private Integer subject5;

  private Integer total;

  private String grade;

  public Student() {

  }

  public Student(Long id, String name, String rollNumber, Integer subject1, Integer subject2, Integer subject3,
                 Integer subject4, Integer subject5, Integer total, String grade) {
    this.id = id;
    this.name = name;
    this.rollNumber = rollNumber;
    this.subject1 = subject1;
    this.subject2 = subject2;
    this.subject3 = subject3;
    this.subject4 = subject4;
    this.subject5 = subject5;
    this.total = total;
    this.grade = grade;
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRollNumber() {
    return rollNumber;
  }

  public void setRollNumber(String rollNumber) {
    this.rollNumber = rollNumber;
  }

  public Integer getSubject1() {
    return subject1;
  }

  public void setSubject1(Integer subject1) {
    this.subject1 = subject1;
  }

  public Integer getSubject2() {
    return subject2;
  }

  public void setSubject2(Integer subject2) {
    this.subject2 = subject2;
  }

  public Integer getSubject3() {
    return subject3;
  }

  public void setSubject3(Integer subject3) {
    this.subject3 = subject3;
  }

  public Integer getSubject4() {
    return subject4;
  }

  public void setSubject4(Integer subject4) {
    this.subject4 = subject4;
  }

  public Integer getSubject5() {
    return subject5;
  }

  public void setSubject5(Integer subject5) {
    this.subject5 = subject5;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  @Override
  public String toString() {
    return "Student [id=" + id + ", name=" + name + ", roll Number=" + rollNumber + ", subject1=" + subject1 +
            ",subject2=" + subject2 + ", subject3=" + subject3 + ", subject4=" + subject4 + ", subject5=" +
            subject5 + ",total=" + total + ", grade= "+grade+"]";
  }

}
