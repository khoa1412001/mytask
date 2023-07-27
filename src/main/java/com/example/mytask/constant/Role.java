package com.example.mytask.constant;


public enum Role {
  SM("Scrum master"),
  PO("Project owner"),
  Member("Member"),
  Other("Other");

  public final String label;

  private Role(String label) {
    this.label = label;
  }

}
