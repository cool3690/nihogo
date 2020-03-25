package com.nihon.aki2.control;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Rate {
    private String coin, buy, sell;
    public Rate(String coin, String buy, String sell)
    {
        this.setCoin(coin);
        this.setBuy(buy);
        this.setSell(sell);

    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
