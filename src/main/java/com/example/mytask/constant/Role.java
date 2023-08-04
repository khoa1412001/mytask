package com.example.mytask.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
  SM("Scrum master"),
  PO("Project owner"),
  MEMBER("Member"),
  OTHER("Other");

  public final String label;
}
