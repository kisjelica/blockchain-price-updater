package contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class PriceUpdater extends Contract {
    public static final String BINARY = "6080604052600460005534801561001557600080fd5b50610542806100256000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80634a432a461461003b578063e19cb38014610050575b600080fd5b61004e61004936600461025f565b61008d565b005b61007b61005e3660046102ed565b805160208183018101805160018252928201919093012091525481565b60405190815260200160405180910390f35b6001838360405161009f92919061039e565b9081526020016040518091039020546000146101975760026000546100c491906103c4565b6100cf90600a6104bf565b6100da9060026104cb565b610146600185856040516100ef92919061039e565b908152602001604051809103902054610140600054600a61011091906104bf565b61013a6001898960405161012592919061039e565b90815260200160405180910390205487610209565b9061023b565b90610247565b116101975760405162461bcd60e51b815260206004820152601f60248201527f507269636520646966666572656e6365206c6573736572207468616e20322500604482015260640160405180910390fd5b80600184846040516101aa92919061039e565b908152604051908190036020018120919091556101ca908490849061039e565b604051908190038120828252907fc07960f70ff69ed2cd4edb97f29b18339ee276f4c4f93b1eb17e08f90a69608d9060200160405180910390a2505050565b600080828411156102255761021e8484610253565b9050610232565b61022f8385610253565b90505b90505b92915050565b600061023282846104cb565b600061023282846104ea565b600061023282846103c4565b60008060006040848603121561027457600080fd5b833567ffffffffffffffff8082111561028c57600080fd5b818601915086601f8301126102a057600080fd5b8135818111156102af57600080fd5b8760208285010111156102c157600080fd5b6020928301989097509590910135949350505050565b634e487b7160e01b600052604160045260246000fd5b6000602082840312156102ff57600080fd5b813567ffffffffffffffff8082111561031757600080fd5b818401915084601f83011261032b57600080fd5b81358181111561033d5761033d6102d7565b604051601f8201601f19908116603f01168101908382118183101715610365576103656102d7565b8160405282815287602084870101111561037e57600080fd5b826020860160208301376000928101602001929092525095945050505050565b8183823760009101908152919050565b634e487b7160e01b600052601160045260246000fd5b6000828210156103d6576103d66103ae565b500390565b600181815b808511156104165781600019048211156103fc576103fc6103ae565b8085161561040957918102915b93841c93908002906103e0565b509250929050565b60008261042d57506001610235565b8161043a57506000610235565b8160018114610450576002811461045a57610476565b6001915050610235565b60ff84111561046b5761046b6103ae565b50506001821b610235565b5060208310610133831016604e8410600b8410161715610499575081810a610235565b6104a383836103db565b80600019048211156104b7576104b76103ae565b029392505050565b6000610232838361041e565b60008160001904831182151516156104e5576104e56103ae565b500290565b60008261050757634e487b7160e01b600052601260045260246000fd5b50049056fea26469706673582212204498acf1e4275a788f3bf606a0feb0b07864f5a0a9417a681e080498d9d439cb64736f6c634300080f0033";

    public static final String FUNC_TOKENPRICES = "tokenPrices";

    public static final String FUNC_UPDATEPRICE = "updatePrice";

    public static final Event PRICEUPDATED_EVENT = new Event("priceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PriceUpdater(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PriceUpdater(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PriceUpdater(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PriceUpdater(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<PriceUpdatedEventResponse> getPriceUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEUPDATED_EVENT, transactionReceipt);
        ArrayList<PriceUpdatedEventResponse> responses = new ArrayList<PriceUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceUpdatedEventResponse typedResponse = new PriceUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.symbol = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceUpdatedEventResponse> priceUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceUpdatedEventResponse>() {
            @Override
            public PriceUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICEUPDATED_EVENT, log);
                PriceUpdatedEventResponse typedResponse = new PriceUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse.symbol = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceUpdatedEventResponse> priceUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEUPDATED_EVENT));
        return priceUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> tokenPrices(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENPRICES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updatePrice(String symbol, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEPRICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(symbol), 
                new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PriceUpdater load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PriceUpdater(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PriceUpdater load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PriceUpdater(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PriceUpdater load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PriceUpdater(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PriceUpdater load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PriceUpdater(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PriceUpdater> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PriceUpdater.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PriceUpdater> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PriceUpdater.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PriceUpdater> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PriceUpdater.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PriceUpdater> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PriceUpdater.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class PriceUpdatedEventResponse extends BaseEventResponse {
        public byte[] symbol;

        public BigInteger price;
    }
}
