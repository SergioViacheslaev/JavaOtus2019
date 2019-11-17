package ru.otus.springmvcwebapp.api.hibernatedao;

public class UserDaoException extends RuntimeException {
  public UserDaoException(Exception ex) {
    super(ex);
  }
}
