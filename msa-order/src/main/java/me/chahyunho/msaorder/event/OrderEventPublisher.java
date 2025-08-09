package me.chahyunho.msaorder.event;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private static final String ORDER_CREATED = "order-event";
  private static final String ORDER_CANCELLED  = "order-cancelled";

  public void publishOrderCreated(Long orderId) {
    log.info("Kafka 메시지 전송 시도: {}", orderId);
    try {
      kafkaTemplate.send(ORDER_CREATED, orderId.toString())
          .addCallback(
              result -> System.out.println(
                  "Message sent successfully to topic: " + ORDER_CREATED + ", partition: " +
                      Objects.requireNonNull(result).getRecordMetadata().partition()),
              ex -> log.debug("Failed to send message to topic: {}", ORDER_CREATED, ex)
          );
    } catch (Exception e) {
      log.error("Error publishing order created event", e);
      throw new RuntimeException("Failed to publish order event", e);
    }
  }

  public void publishOrderCancel(Long orderId) {
    log.info("Kafka 메시지 전송 시도: {}", orderId);
    try {
      kafkaTemplate.send(ORDER_CANCELLED, orderId.toString())
          .addCallback(
              result -> System.out.println(
                  "Message sent successfully to topic: " + ORDER_CANCELLED + ", partition: " +
                      Objects.requireNonNull(result).getRecordMetadata().partition()),
              ex -> log.debug("Failed to send message to topic: {}", ORDER_CANCELLED, ex)
          );
    } catch (Exception e) {
      log.error("Error publishing order created event", e);
      throw new RuntimeException("Failed to publish order event", e);
    }
  }
}
