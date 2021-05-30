package com.dauhunon.utils;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;

public class App {
  public static final String getWorkingDirectory() {
    String workingDir = Paths.get("")
      .toAbsolutePath()
      .toString();
    workingDir = workingDir.replace("\\", "/");

    return workingDir;
  }

  public static final String getUrlSite(HttpServletRequest req) {
    String url = req.getRequestURL().toString();
    return url.replace(req.getServletPath(), "");
  }

  public static final String appName = "dauhunon";

  public static final String actionFailed = "actionFailed";
  public static final String actionSuccess = "actionSuccess";


}
