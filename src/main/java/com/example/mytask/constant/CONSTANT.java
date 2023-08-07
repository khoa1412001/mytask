package com.example.mytask.constant;

import java.util.List;

public class CONSTANT {

  private CONSTANT() {
  }

  public static final List<Integer> POINT_LIST = List.of(1, 2, 3, 5, 8);
  public static final List<String> STATUS_LIST = List.of(
      "In Queue",
      "Need Review",
      "Pending",
      "In_Progress",
      "Done");
  public static final int CHECK_OUT_TIME = 17;

  public static final String UNDEFINED = "Undefined";

}
