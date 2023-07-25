package com.example.mytask.controller;

import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.model.dto.AssignDTO;
import com.example.mytask.payload.DataResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/assign")
public class AssignController {

  @Autowired
  private IntegrationGateway integrationGateway;

  @PostMapping
  public DataResponse assignTask(@Valid @RequestBody AssignDTO assignDTO) {
    Map<String, Integer> map = new HashMap<>();
    map.put("userId", assignDTO.getUserId());
    map.put("taskId", assignDTO.getTaskId());
    return integrationGateway.invoke(map, "ASSIGN_TASK");
  }
}
