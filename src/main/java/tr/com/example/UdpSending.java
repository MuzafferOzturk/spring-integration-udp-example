package tr.com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component("udpSending")
public class UdpSending {

    @Value("${port}")
    private Integer port;
    @Bean
    @ServiceActivator(inputChannel = "udpOutChannel")
    public UnicastSendingMessageHandler handler(){
        return new UnicastSendingMessageHandler("localhost", port);
    }

    @Bean
    public MessageChannel udpOutChannel(){
        return new DirectChannel();
    }
}
