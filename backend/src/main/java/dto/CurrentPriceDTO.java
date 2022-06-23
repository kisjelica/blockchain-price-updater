package dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CurrentPriceDTO {
    private BigDecimal apiPrice;
    private BigInteger onchainPrice;

    public BigDecimal getApiPrice() {
        return apiPrice;
    }

    public void setApiPrice(BigDecimal apiPrice) {
        this.apiPrice = apiPrice;
    }

    public BigInteger getOnchainPrice() {
        return onchainPrice;
    }

    public void setOnchainPrice(BigInteger onchainPrice) {
        this.onchainPrice = onchainPrice;
    }
}
