package ru.otus.hw16frontend.messagesystem;

public enum MessageType {
    USER_DATA("UserData"), USERS_LIST("UsersList");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
