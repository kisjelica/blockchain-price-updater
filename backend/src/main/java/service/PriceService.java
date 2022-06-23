package service;

import config.Configuration;
import contract.PriceUpdater;
import dto.CurrentPriceDTO;
import dto.PriceChangeDTO;
import http.Response;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import org.web3j.abi.*;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PriceService {

    public Response getPricesInPeriod(String symbol, Long from, Long to) {

        if(from == null){
            from = 0l;
        }

        if(to == null){
            to = Long.MAX_VALUE;
        }

        if(from > to || (!symbol.equals("BTC") && !symbol.equals("ETH") )){
            Response response = new Response();
            response.setCode(-1);
            response.setMessage("Invalid request data");
            return response;
        }
        List<PriceChangeDTO> dtos = new ArrayList<>();
        Uint256 symbolHash = null;
        try {
            symbolHash = new Uint256(Numeric.toBigInt(Hash.sha3(new String(symbol).getBytes("UTF-8"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST, Configuration.CONTRACT_ADDRESS_KOVAN).addSingleTopic(EventEncoder.encode(PriceUpdater.PRICEUPDATED_EVENT)).addOptionalTopics("0x" + TypeEncoder.encode(symbolHash));
        EthLog ethLog = null;
        try {
            ethLog = Configuration.web3j.ethGetLogs(filter).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (EthLog.LogResult log : ethLog.getLogs()) {
           // System.out.println(log);
            Uint256 priceArg = (Uint256) FunctionReturnDecoder.decodeIndexedValue(((Log) log).getData(), new TypeReference<Uint256>() {
            });

            BigInteger price = priceArg.getValue();
            BigInteger blockNumber = ((Log) log).getBlockNumber();
            BigInteger timestamp = null;
            try {
                timestamp = Configuration.web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), false).send().getBlock().getTimestamp();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(timestamp.longValue() >= from && timestamp.longValue() <= to){
                PriceChangeDTO priceChangeDTO = new PriceChangeDTO();
                priceChangeDTO.setPrice(price);
                priceChangeDTO.setTimestamp(timestamp.longValue());
                dtos.add(priceChangeDTO);
            }
        }
        Response response = new Response();
        response.setData(dtos);
        return response;
    }

    public Response getCurrentPrice(String symbol){
        PriceUpdater priceUpdater = Configuration.priceUpdaterSC;
        Response response = new Response();
        if(!symbol.equals("BTC") && !symbol.equals("ETH")){
            response.setCode(-1);
            response.setMessage("Unsupported currency");
            return response;
        }
        HttpResponse<JsonNode> result = Unirest.get(Configuration.CMC_API_URL + "?symbol=" +symbol)
                .header("X-CMC_PRO_API_KEY", Configuration.CMC_API_KEY)
                .header("accept", "application/json").asJson();
        BigDecimal apiPrice = new JSONObject(result.getBody().toString()).getJSONObject("data").getJSONArray(symbol).getJSONObject(0).getJSONObject("quote").getJSONObject("USD").getBigDecimal("price");
        BigInteger onchainPrice;

        try {
            onchainPrice = priceUpdater.tokenPrices(symbol).send();

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(-1);
            response.setMessage("Error in SC call");
            return response;
        }

        CurrentPriceDTO dto = new CurrentPriceDTO();
        dto.setApiPrice(apiPrice);
        dto.setOnchainPrice(onchainPrice);

        response.setData(dto);
        return response;
    }
}
