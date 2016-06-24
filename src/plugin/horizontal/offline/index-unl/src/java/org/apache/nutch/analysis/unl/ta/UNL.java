package org.apache.nutch.analysis.unl.ta;

import java.util.*;

public interface UNL
{
  public final static String X_POINT_ID = UNL.class.getName();
  public ArrayList process(String queryString);
}
