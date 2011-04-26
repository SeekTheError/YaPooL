package com.jsar.client.http;

public class EntryImpl implements HttpDataFormatter.Entry {

  String name;
  String value;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getValue() {
    return value;
  }

  public EntryImpl(String name, String value) {
    this.name = name;
    this.value = value;
  }

}
