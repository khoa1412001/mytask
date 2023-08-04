package com.example.mytask.dto;

import com.example.mytask.validation.dob.DobValidation;
import com.example.mytask.validation.email.EmailValidation;
import com.example.mytask.validation.phone.PhoneValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

  private String name;
  @DobValidation
  private String dob;
  @EmailValidation
  private String email;
  @PhoneValidation
  private String phone;
  private String office;
  private String role;
}
