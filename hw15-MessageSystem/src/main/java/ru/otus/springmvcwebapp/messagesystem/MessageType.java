package ru.otus.springmvcwebapp.messagesystem;

public enum MessageType {
  USER_DATA("UserData");

  private final String value;

  MessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
