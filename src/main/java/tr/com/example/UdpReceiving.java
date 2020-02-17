package tr.com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.stereotype.Component;

@Component("UdpReceiving")
public class UdpReceiving {

    @Value("${port}")
    private Integer port;

    @Bean
    public UnicastReceivingChannelAdapter udpIn(){
        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(port);
        adapter.setOutputChannelName("udpInChannel");
        return adapter;
    }
}
