package tr.com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("udpOutChannel")
    MessageChannel outputChannel;

    @Override
    public void run(String... args) throws Exception {
        outputChannel.send(messageBuilder("test Message"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    Message<?> messageBuilder(String message){
        return MessageBuilder.withPayload(message).build();
    }

    @ServiceActivator(inputChannel = "udpInChannel")
    public void handleMessage(Message<?> message){
        System.out.println("--> " + new String((byte[])message.getPayload()));
    }
}
