package org.example;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.awspring.cloud.sqs.listener.SqsHeaders.MessageSystemAttributes.SQS_MESSAGE_GROUP_ID_HEADER;

@Component
public class Producer {

    private final SqsTemplate sqsTemplate;
    private final String QUEUE = "product.fifo";
    private final int SCALE = 100;

    public Producer(final SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Scheduled(fixedDelay = 1)
    public void product() {
        List<Message<UUID>> batch = new ArrayList<>(10);

        for(int i=0; i<10; i++) {
            UUID id = UUID.randomUUID();
            int hashCode = id.hashCode();
            int positiveHash = (hashCode % SCALE + SCALE) % SCALE;
            System.out.println(positiveHash);
            Message<UUID> message = MessageBuilder.withPayload(UUID.randomUUID())
                    .setHeader(SQS_MESSAGE_GROUP_ID_HEADER, String.valueOf(positiveHash))
                    .build();
            batch.add(message);
        }

        SendResult.Batch<UUID> result = sqsTemplate.sendMany(QUEUE, batch);
        System.out.println(result);
    }

}
