package com.example.demo.stream;


import com.example.demo.dto.ProductOrderDto;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class NotificationSender {

    StreamBridge streamBridge;

    public NotificationSender(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendNotification(ProductOrderDto productOrderDto){
        streamBridge.send("notifications-topic", productOrderDto, MimeTypeUtils.APPLICATION_JSON);
    }
}
