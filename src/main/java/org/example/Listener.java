package org.example;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.BatchAcknowledgement;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class Listener {

    @SqsListener(value = "product.fifo",
            maxMessagesPerPoll = "20",
            maxConcurrentMessages = "20",
            acknowledgementMode = "MANUAL",
            pollTimeoutSeconds = "30"
    )
    public void listen(List<Message<UUID>> messages, BatchAcknowledgement<UUID> batchAcknowledgement) {
        System.out.println(String.format("listener %s, values %s", messages.size(), messages));

        batchAcknowledgement.acknowledge(messages);
    }

}
