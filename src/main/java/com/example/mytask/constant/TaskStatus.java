package com.example.mytask.constant;

public enum TaskStatus {
  In_Queue("In Queue"),
  Need_Review("Need Review"),
  Pending("Pending"),
  In_Progress("In_Progress"),
  Done("Done");
  private final String status;

  private TaskStatus(String status) {
    this.status = status;
  }
}
