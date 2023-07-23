package com.example.mytask.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
  private Integer id;
  private String title;
  private String status;
  @OneToOne()
  @JoinColumn(name = "userId")
  @Getter(AccessLevel.NONE)
  private User assignee;
  private Integer point;
  private Integer est;
  @OneToMany(mappedBy = "task")
  private List<Logwork> logwork;

  public Task(String title, Integer point) {
    this.title = title;
    this.point = point;
    this.status = "In Queue";
  }

  public Task() {
  }

  public String getAssignee() {
    return this.assignee.getName();
  }

  @PreUpdate
  @PrePersist
  public void preUpdateTask() {
    if (this.point == 8) {
      this.est = 10;
    } else {
      this.est = this.point;
    }
  }

  @Override
  public String toString() {
    return String.format("Id: %s, title: %s, status: %s, assignee: %s, point: %s, est: %s"
        , getId(), getTitle(), getTitle(), getAssignee(), getPoint(), getEst());
  }
}
