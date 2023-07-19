package com.example.mytask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String dob;
  private String email;
  private String phone;
  private String office;
  private String role;

  public User() {
    this.name = "sheesh";
    this.dob = "sheesh";
    this.email = "sheesh";
    this.phone = "sheesh";
    this.office = "sheesh";
    this.role = "sheesh";
  }
}
