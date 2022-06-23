package config;

import contract.PriceUpdater;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

public class Configuration {
    public static final String CMC_API_KEY = "be7ae13a-dbe4-448c-819b-c20a4c45497b";
    public static final String CMC_API_URL = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest";
    public static final String CONTRACT_ADDRESS_KOVAN = "0xc518e70ac25519d15ead25720cfb8adbaa24c8ec";
    public static final String KOVAN_WEB3_URL = "https://speedy-nodes-nyc.moralis.io/ef1cb151d10fa0096daca6eb/eth/kovan/archive";
    public static final String ADMIN_PRIVATE_KEY = "0xa8e0a1ee53bb35b89f1558251d9c634cf42e4037d63a8c6e708da9015baf271d";
    public static final String INFURA_WSS_URL = "wss://kovan.infura.io/ws/v3/8af93ac695e0470ab229356dc438dae0";
    public static final Web3j web3j = Web3j.build(new HttpService(Configuration.KOVAN_WEB3_URL));
    public static PriceUpdater priceUpdaterSC;
    public static void loadSmartContract(){
        Credentials credentials = Credentials.create(Configuration.ADMIN_PRIVATE_KEY);
        priceUpdaterSC = PriceUpdater.load(Configuration.CONTRACT_ADDRESS_KOVAN, web3j, new RawTransactionManager(web3j, credentials, 42), new DefaultGasProvider());
    }
}
