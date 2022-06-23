package service;

import config.Configuration;
import contract.PriceUpdater;
import io.reactivex.disposables.Disposable;
import kong.unirest.Unirest;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;
import java.time.LocalDateTime;

public class PublisherService {

    public void listenToEvents() {
        WebSocketService wss = new WebSocketService(Configuration.INFURA_WSS_URL, false);
        try {
            wss.connect();
        } catch (ConnectException e) {
            System.out.println("Error in WSS connection!");
            e.printStackTrace();
        }
        Web3j web3j = Web3j.build(wss);
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.EARLIEST, Configuration.CONTRACT_ADDRESS_KOVAN);
        String encodedEventSignature = EventEncoder.encode(PriceUpdater.PRICEUPDATED_EVENT);
        filter.addSingleTopic(encodedEventSignature);

        web3j.ethLogFlowable(filter).subscribe(log -> {
            System.out.println("[" + LocalDateTime.now() + "] " + "EVENT RECORDED:");
            System.out.println(log);
            Unirest.post("http://localhost:8090/events").body(log.toString());
        }, throwable -> {
            System.out.println("Error: " + throwable.getMessage());
        });
    }
}
