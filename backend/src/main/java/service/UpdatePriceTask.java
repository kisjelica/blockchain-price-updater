package service;

import config.Configuration;
import contract.PriceUpdater;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimerTask;

public class UpdatePriceTask extends TimerTask {
    @Override
    public void run() {
        PriceUpdater priceUpdater = Configuration.priceUpdaterSC;
        HttpResponse<JsonNode> response = Unirest.get(Configuration.CMC_API_URL + "?symbol=BTC")
                .header("X-CMC_PRO_API_KEY", Configuration.CMC_API_KEY)
                .header("accept", "application/json").asJson();
        BigDecimal apiBtcPrice = new JSONObject(response.getBody().toString()).getJSONObject("data").getJSONArray("BTC").getJSONObject(0).getJSONObject("quote").getJSONObject("USD").getBigDecimal("price");
        System.out.println("[" + LocalDateTime.now() + "] API BTC PRICE: " + apiBtcPrice);
        BigInteger onchainBtcPrice = null;
        try {
            onchainBtcPrice = priceUpdater.tokenPrices("BTC").send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(onchainBtcPrice.compareTo(BigInteger.ZERO) == 0){
            onchainBtcPrice = BigInteger.ONE;
        }
        System.out.println("[" + LocalDateTime.now() + "] ONCHAIN BTC PRICE: " + onchainBtcPrice);
        BigDecimal priceDifferenceBtc = (new BigDecimal(onchainBtcPrice).subtract(apiBtcPrice)).abs();
        System.out.println("[" + LocalDateTime.now() + "] Price difference is: " + priceDifferenceBtc);
        BigDecimal priceDifferencePercentageBtc = (priceDifferenceBtc).divide(new BigDecimal(onchainBtcPrice), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        System.out.println("[" + LocalDateTime.now() + "] Price difference of BTC is: " + priceDifferencePercentageBtc +"%");
        if (priceDifferencePercentageBtc.compareTo(BigDecimal.valueOf(2)) == -1) {
            System.out.println("[" + LocalDateTime.now() + "] BTC Price difference too small, transaction skipped");
        } else {
            try {
                TransactionReceipt transactionReceipt = priceUpdater.updatePrice("BTC", apiBtcPrice.toBigInteger()).send();
                System.out.println("[" + LocalDateTime.now() + "] Price updated at tx. hash " + transactionReceipt.getTransactionHash());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response = Unirest.get(Configuration.CMC_API_URL + "?symbol=ETH")
                .header("X-CMC_PRO_API_KEY", Configuration.CMC_API_KEY)
                .header("accept", "application/json").asJson();
        BigDecimal apiEthPrice = new JSONObject(response.getBody().toString()).getJSONObject("data").getJSONArray("ETH").getJSONObject(0).getJSONObject("quote").getJSONObject("USD").getBigDecimal("price");
        System.out.println("[" + LocalDateTime.now() + "] API ETH PRICE: " + apiEthPrice);
        BigInteger onchainEthPrice = null;
        try {
            onchainEthPrice = priceUpdater.tokenPrices("ETH").send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(onchainEthPrice.compareTo(BigInteger.ZERO) == 0){
            onchainEthPrice = BigInteger.ONE;
        }
        System.out.println("[" + LocalDateTime.now() + "] ONCHAIN ETH PRICE: " + onchainEthPrice);
        BigDecimal priceDifferenceEth = (new BigDecimal(onchainEthPrice).subtract(apiEthPrice)).abs();
        System.out.println("[" + LocalDateTime.now() + "] Price difference is: " + priceDifferenceEth);
        BigDecimal priceDifferencePercentageEth = (priceDifferenceEth).divide(new BigDecimal(onchainEthPrice), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        System.out.println("[" + LocalDateTime.now() + "] Price difference of ETH is: " + priceDifferencePercentageEth +"%");
        if (priceDifferencePercentageEth.compareTo(BigDecimal.valueOf(2)) == -1) {
            System.out.println("[" + LocalDateTime.now() + "] ETH Price difference too small, transaction skipped");
        } else {
            try {
                TransactionReceipt transactionReceipt = priceUpdater.updatePrice("ETH", apiEthPrice.toBigInteger()).send();
                System.out.println("[" + LocalDateTime.now() + "] Price updated at tx. hash " + transactionReceipt.getTransactionHash());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
