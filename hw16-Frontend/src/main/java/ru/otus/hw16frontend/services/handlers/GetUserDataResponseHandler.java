package ru.otus.hw16frontend.services.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16frontend.messagesystem.RequestHandler;
import ru.otus.hw16frontend.messagesystem.common.Serializers;
import ru.otus.hw16frontend.services.frontendservice.FrontendService;
import ru.otus.message.Message;

import java.util.Optional;
import java.util.UUID;


public class GetUserDataResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetUserDataResponseHandler.class);

  private final FrontendService frontendService;


  public GetUserDataResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      String userJsonData = Serializers.deserialize(msg.getPayload(), String.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));

      frontendService.takeConsumer(sourceMessageId, String.class).ifPresent(consumer -> consumer.accept(userJsonData));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
