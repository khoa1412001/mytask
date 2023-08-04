package com.example.mytask.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TaskStatus {
  IN_QUEUE("In Queue"),
  NEED_REVIEW("Need Review"),
  PENDING("Pending"),
  IN_PROGRESS("In_Progress"),
  DONE("Done");

  private final String status;
}
