package at.outdated.bitcoin.exchange.api.market;

import at.outdated.bitcoin.exchange.api.currency.Currency;
import at.outdated.bitcoin.exchange.api.currency.CurrencyValue;

/**
 * Created by ebirn on 22.09.13.
 */
public class MarketOrder {

    protected Currency baseCurrency;

    protected CurrencyValue price;

    protected float volume;

    protected TradeDecision decision;

    public MarketOrder() {

    }

    public MarketOrder(TradeDecision decision, float volume, Currency base,  CurrencyValue price) {
        this.decision = decision;
        this.volume = volume;
        this.baseCurrency = base;
        this.price = price;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyValue getPrice() {
        return price;
    }

    public void setPrice(CurrencyValue price) {
        this.price = price;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Order: " + decision + " " + volume + " " + baseCurrency + " @ " + price;
    }
}