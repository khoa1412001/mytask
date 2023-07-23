package com.example.mytask.model.dto;

import com.example.mytask.validation.dob.DobValidation;
import com.example.mytask.validation.email.EmailValidation;
import com.example.mytask.validation.phone.PhoneValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
