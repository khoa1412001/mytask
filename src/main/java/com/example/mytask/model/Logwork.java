package com.example.mytask.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "logwork")
@Getter
@Setter
@NoArgsConstructor
public class Logwork {

  @Id
  @GeneratedValue(generator = "logwork_seq")
  private Integer id;
  private Timestamp timeStart;
  private Timestamp timeEnd;
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "taskId")
  private Task task;

  public Logwork(Timestamp timeStart, Timestamp timeEnd) {
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
  }
}
