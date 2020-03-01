package tr.com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.stereotype.Component;

@Component("udpReceiving")
public class UdpReceiving {

    @Autowired
    IntegrationFlowContext integrationFlowContext;

    public void registerReceiving(int port){
        IntegrationFlow flow = IntegrationFlows
                .from(Udp.inboundAdapter(port))
                .channel("udpInChannel")
                .get();
        integrationFlowContext.registration(flow).register();
    }

}
