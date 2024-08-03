package com.company.rabbitmq;

import com.company.dtos.MessageSentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {
        @Value("${rabbitmq.exchange.name}")
        private String exchangeName;

        @Value("${rabbitmq.routing.key}")
        private String routingKey;
        private final RabbitTemplate rabbitTemplate;
    public void sendMessage(MessageSentDto dto) {
        log.info("Sending json message: {}", dto.toString());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, dto);
    }

}
