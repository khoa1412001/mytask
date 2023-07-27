package com.example.mytask.model;

import com.example.mytask.constant.Role;
import java.util.stream.Stream;
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
  @GeneratedValue(generator = "user_seq")
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
    if (!isLegitRole()) {
      this.role = "Other";
    }
  }

  private boolean isLegitRole() {
    for (Role r : Role.values()) {
      if (r.label.equals(this.role)) {
        return true;
      }
    }
    return false;
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
