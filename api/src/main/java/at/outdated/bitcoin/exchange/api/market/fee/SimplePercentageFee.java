package at.outdated.bitcoin.exchange.api.market.fee;

import at.outdated.bitcoin.exchange.api.currency.CurrencyValue;
import at.outdated.bitcoin.exchange.api.market.OrderType;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ebirn
 * Date: 09.06.13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class SimplePercentageFee extends Fee {

    BigDecimal percentage = BigDecimal.ZERO;

    public SimplePercentageFee() {

    }

    public SimplePercentageFee(String percentage) {
        this.percentage = new BigDecimal(percentage);
    }

    public SimplePercentageFee(BigDecimal percentage) {
       this.percentage = percentage;
    }

    @Deprecated
    public SimplePercentageFee(double percentage) {
        this.percentage = new BigDecimal(percentage);
    }

    @Override
    public CurrencyValue calculate(OrderType type, CurrencyValue volume) {

        CurrencyValue fee = new CurrencyValue(volume);
        fee.multiply(percentage);

        return fee;
    }

    @Override
    public String toString() {
        NumberFormat format = NumberFormat.getPercentInstance();

        format.setMinimumFractionDigits(2);

        String percentStr = format.format(percentage.doubleValue());

        return super.toString() + percentStr;
    }
}
