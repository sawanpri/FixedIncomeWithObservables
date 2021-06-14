package com.jeff.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;

public class Trade  {

    public Timestamp timestamp;

    @Override
    public String toString() {
        return "Trade{" +
                "timestamp=" + timestamp +
                ", price=" + price +
                ", size=" + size +
                ", status='" + status + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    public BigDecimal price;
    public int size;
    public String status;
    public String symbol;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public String getStatus() {
        return status;
    }

    public String getSymbol() {
        return symbol;
    }
}
