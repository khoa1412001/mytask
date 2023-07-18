package com.example.mytask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

  @Id
  @GeneratedValue
  private int id;
  private String title;
  private String status;
  private String assignee;
  private Integer point;
  private Integer est;

  public Task(String title, Integer point) {
    this.title = title;
    this.point = point;
    if (point == 8) {
      this.est = 10;
    } else {
      this.est = point;
    }
    this.status = "In Queue";
    this.assignee = "Undefined";
  }
}
