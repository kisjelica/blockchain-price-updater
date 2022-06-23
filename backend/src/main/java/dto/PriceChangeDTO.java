package dto;

import java.math.BigInteger;

public class PriceChangeDTO {
    private BigInteger price;
    private long timestamp;

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
