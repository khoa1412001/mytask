package com.example.mytask;

import com.example.mytask.constant.Role;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MytaskApplication {

  public static void main(String[] args) {
//    System.out.println(Role.SM);
    SpringApplication.run(MytaskApplication.class, args);
  }

}
