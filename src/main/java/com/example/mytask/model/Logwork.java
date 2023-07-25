package com.example.mytask.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logwork")
@Getter
@Setter
public class Logwork {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Integer id;
  private Date timeStart;
  private Date timeEnd;
  @ManyToOne
  @JoinColumn(name = "taskId")
  private Task task;

}
