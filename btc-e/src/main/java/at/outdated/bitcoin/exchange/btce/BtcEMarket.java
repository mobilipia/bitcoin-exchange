package at.outdated.bitcoin.exchange.btce;

import at.outdated.bitcoin.exchange.api.ExchangeApiClient;
import at.outdated.bitcoin.exchange.api.Market;
import at.outdated.bitcoin.exchange.api.currency.Currency;
import at.outdated.bitcoin.exchange.api.market.transfer.TransferMethod;
import at.outdated.bitcoin.exchange.api.market.transfer.TransferType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ebirn
 * Date: 26.05.13
 * Time: 23:41
 * To change this template use File | Settings | File Templates.
 */
public class BtcEMarket extends Market {


    public BtcEMarket() {
        super("btce", "http://btc-e.com", "BTC-E", Currency.EUR);

        withdrawals.add(new TransferMethod(Currency.BTC, TransferType.VIRTUAL, null));
        withdrawals.add(new TransferMethod(Currency.LTC, TransferType.VIRTUAL, null));
        withdrawals.add(new TransferMethod(Currency.NMC, TransferType.VIRTUAL, null));

        deposits.add(new TransferMethod(Currency.BTC, TransferType.VIRTUAL, null));
        deposits.add(new TransferMethod(Currency.LTC, TransferType.VIRTUAL, null));
        deposits.add(new TransferMethod(Currency.NMC, TransferType.VIRTUAL, null));


    }

    @Override
    public Currency[] getFiatCurrencies() {
        return new Currency[] {Currency.USD, Currency.EUR};  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Currency[] getCryptoCurrencies() {
        return new Currency[] { Currency.BTC, Currency.LTC, Currency.NMC };  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ExchangeApiClient getApiClient() {
        return new BtcEApiClient(this);  //To change body of implemented methods use File | Settings | File Templates.
    }


}
