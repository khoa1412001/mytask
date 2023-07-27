package com.example.mytask.model;

import com.example.mytask.constant.Role;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
  @GeneratedValue(generator = "task_seq")
  private Integer id;
  private String title;
  private String status = "In queue";
  @OneToOne()
  @JoinColumn(name = "userId")
  @Getter(AccessLevel.NONE)
  private User assignee;
  private Integer point;
  private Integer est;
  @OneToMany(mappedBy = "task")
  private List<Logwork> logwork;
  private Date deadline;

  public Task() {
  }

  public String getAssignee() {
    if (this.assignee == null) {
      return "Undefined";
    }
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
