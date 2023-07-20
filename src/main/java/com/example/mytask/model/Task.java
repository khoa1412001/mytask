package com.example.mytask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {

  @Id
  @GeneratedValue
  private int id;
  private String title;
  private String status;
  @OneToOne()
  private String assignee;
  private Integer point;
  private Integer est;

  public Task(String title, Integer point) {
    this.title = title;
    this.point = point;
    this.status = "In Queue";
  }

//  public User getAssignee() {
//    return assignee ? "Undef";
//  }

  @PreUpdate
  @PrePersist
  public void preUpdate() {
    if (this.point == 8) {
      this.est = 10;
    } else {
      this.est = this.point;
    }
  }
}
