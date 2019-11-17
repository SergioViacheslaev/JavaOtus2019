package ru.otus.springmvcwebapp.messagesystem;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Message {
  static final Message VOID_MESSAGE = new Message();

  private final UUID id = UUID.randomUUID();
  private final String from;
  private final String to;
  private final Optional<UUID> sourceMessageId;
  private final String type;
  private final int payloadLength;
  private final byte[] payload;

  private Message() {
    this.from = null;
    this.to = null;
    this.sourceMessageId = Optional.empty();
    this.type = "voidTechnicalMessage";
    this.payload = new byte[1];
    this.payloadLength = payload.length;
  }

  public Message(String from, String to, Optional<UUID> sourceMessageId, String type, byte[] payload) {
    this.from = from;
    this.to = to;
    this.sourceMessageId = sourceMessageId;
    this.type = type;
    this.payloadLength = payload.length;
    this.payload = payload;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Message message = (Message) o;
    return id == message.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", from='" + from + '\'' +
        ", to='" + to + '\'' +
        ", sourceMessageId=" + sourceMessageId +
        ", type='" + type + '\'' +
        ", payloadLength=" + payloadLength +
        '}';
  }

  public UUID getId() {
    return id;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getType() {
    return type;
  }

  public byte[] getPayload() {
    return payload;
  }

  public int getPayloadLength() {
    return payloadLength;
  }


  public Optional<UUID> getSourceMessageId() {
    return sourceMessageId;
  }
}
