package com.jsar.client.http;

import com.google.gwt.http.client.URL;
/**
 * 
 * @author rem
 *
 */
public class HttpDataFormatter {

  public static interface Entry {
    String getName();

    String getValue();
  }

  public static String buildQueryString(Entry[] queryEntries) {
    StringBuffer sb = new StringBuffer();

    for (int i = 0, n = queryEntries.length; i < n; ++i) {
      Entry queryEntry = queryEntries[i];

      if (i > 0) {
	sb.append(URL.encode("&"));
      }

      // encode the characters in the name
      String encodedName = URL.encode(queryEntry.getName());
      
      sb.append(encodedName);

      sb.append(URL.encode("="));

      // encode the characters in the value
      String encodedValue = URL.encode(queryEntry.getValue());
      sb.append(encodedValue);
    }

    return sb.toString();
  }
}
