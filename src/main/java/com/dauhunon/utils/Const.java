package com.dauhunon.utils;

import java.util.HashMap;
import java.util.Map;

public class Const {
  public static final Map<String, String> CLOUDINARY = new HashMap<String, String>()
  {{
    put("cloud_name", "dwnj15uud");
    put("api_key", "221752539721262");
    put("api_secret", "8xQfarN3gHbWi0deJavzjZ7GzKA");
  }};

  public static final String PENDING = "PENDING";
  public static final String IN_PROCESS = "IN_PROCESS";
  public static final String DELIVERING = "DELIVERING";
  public static final String SUCCESS = "SUCCESS";
  public static final String CANCEL = "CANCEL";
  public static final String REJECT = "REJECT";

  public static final String EMPLOYEE = "EMPLOYEE";
  public static final String MANAGER = "MANAGER";
  public static final String ADMIN = "ADMIN";

  public static final int PASSWORD_TOKEN_LENGTH = 45;
}
