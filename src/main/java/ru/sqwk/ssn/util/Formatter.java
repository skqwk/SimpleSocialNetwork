package ru.sqwk.ssn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
  private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static String format(Date timestamp) {
    return formatter.format(timestamp);
  }
}
