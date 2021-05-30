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

  public static final int DEFAULT_CART_STATUS = 0;
  public static final Map<Integer, String> CART_STATUSES = new HashMap<Integer, String>()
  {{
    put(0, "pending");
    put(1, "cancel");
    put(2, "reject");
    put(3, "delivering");
    put(4, "success");
  }};

  public static final String EMPLOYEE = "EMPLOYEE";
  public static final String MANAGER = "MANAGER";
  public static final String ADMIN = "ADMIN";

  public static final int PASSWORD_TOKEN_LENGTH = 45;
}
