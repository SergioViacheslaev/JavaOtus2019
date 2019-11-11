package ru.otus.springmvcwebapp.api.sessionmanager;


public class SessionManagerException extends RuntimeException {
  public SessionManagerException(String msg) {
    super(msg);
  }

  public SessionManagerException(Exception ex) {
    super(ex);
  }
}
