package tr.com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component("udpSending")
public class UdpSending {
    @Bean
    public MessageChannel udpOutChannel(){
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow udpEcho(){
        return IntegrationFlows
                .from("udpOutChannel")
                .handle(Udp.outboundAdapter(message -> message.getHeaders().get("address")))
                .get();
    }

}
