package tr.com.example;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@PropertySource(value = "classpath:config.properties")
public class Main implements CommandLineRunner {

    //region Spring
    @Value("${port1}")
    private String port1;
    @Value("${port2}")
    private String port2;

    @Autowired
    @Qualifier("udpOutChannel")
    MessageChannel outputChannel;
    @Autowired
    @Qualifier("udpReceiving")
    UdpReceiving udpReceiving;
    //endregion
    private static final String headerFormat = "udp://%s:%s";
    @Override
    public void run(String... args) throws Exception {
        udpReceiving.registerReceiving(Integer.parseInt(port1));
        udpReceiving.registerReceiving(Integer.parseInt(port2));
        outputChannel.send(messageBuilder("Message1", "localhost", port1));
        outputChannel.send(messageBuilder("Message2", "localhost", port2));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    Message<?> messageBuilder(String message, String host, String port){
        return MessageBuilder
                .withPayload(message)
                .setHeader("address", StringFormatter.format(headerFormat, host, port).getValue())
                .build();
    }

    @ServiceActivator(inputChannel = "udpInChannel")
    public void handleMessage(Message<?> message){
        System.out.println("--> " + new String((byte[])message.getPayload()));
    }
}
