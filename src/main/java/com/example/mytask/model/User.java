package com.example.mytask.model;

import com.example.mytask.constant.Role;
import java.util.List;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)

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

  @PreUpdate
  @PrePersist
  public void pre() {
    if (!isLegitRole()) {
      this.role = "Other";
    }
    String[] temp = this.dob.split("/");
    String finalDob = "";
    for (String s : temp) {
      if (s.length() < 2) {
        finalDob = finalDob.concat("0" + s);
      } else {
        finalDob = finalDob.concat(s);
      }
    }
    this.dob = finalDob;
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
    if (this.phone == null) {
      return "";
    }
    String pno = this.phone;
    return (pno.substring(0, 4) + " " + pno.substring(4, 7) + " " + pno.substring(7));
  }

  @Override
  public String toString() {
    return String.format("Id: %s, name: %s, dob: %s, email: %s, phone: %s, office: %s, role: %s"
        , getId(), getName(), getDob(), getEmail(), getPhone(), getOffice(), getRole());
  }
}
