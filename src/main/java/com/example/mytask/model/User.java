package com.example.mytask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Integer id;
  private String name;
  private String dob;
  private String email;
  @Getter(AccessLevel.NONE)
  private String phone;
  private String office;
  private String role;

  public User() {

  }

  @PreUpdate
  @PrePersist
  public void pre() {
    if (this.role == null) {
      this.role = "Other";
    }
    switch (this.role) {
      case "Scrum master":
        this.role = "Scrum master";
        break;
      case "Project owner":
        this.role = "Project owner";
        break;
      case "Member":
        this.role = "Member";
        break;
      default:
        this.role = "Other";
    }
  }

  public String getPhone() {
    String pno = this.phone;
    return (pno.substring(0, 4) + " " + pno.substring(4, 7) + " " + pno.substring(7));
  }

  @Override
  public String toString() {
    return String.format("Id: %s, name: %s, dob: %s, email: %s, phone: %s, office: %s, role: %s"
        , getId(), getName(), getDob(), getEmail(), getPhone(), getOffice(), getRole());
  }
}
