package at.outdated.bitcoin.exchange.vircurex;

import at.outdated.bitcoin.exchange.api.ExchangeApiClient;
import at.outdated.bitcoin.exchange.api.Market;
import at.outdated.bitcoin.exchange.api.currency.Currency;
import at.outdated.bitcoin.exchange.api.market.transfer.TransferMethod;
import at.outdated.bitcoin.exchange.api.market.transfer.TransferType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebirn on 11.10.13.
 */
public class VircurexMarket extends Market {

    public VircurexMarket() {
        super("vircurex", "http://vircurex.com", "Vircurex", Currency.USD);

        withdrawals.add(new TransferMethod(Currency.BTC, TransferType.VIRTUAL, null));
        withdrawals.add(new TransferMethod(Currency.LTC, TransferType.VIRTUAL, null));
        withdrawals.add(new TransferMethod(Currency.NMC, TransferType.VIRTUAL, null));
        withdrawals.add(new TransferMethod(Currency.NVC, TransferType.VIRTUAL, null));

        deposits.add(new TransferMethod(Currency.BTC, TransferType.VIRTUAL, null));
        deposits.add(new TransferMethod(Currency.EUR, TransferType.BANK, null));
        deposits.add(new TransferMethod(Currency.USD, TransferType.BANK, null));

    }

    @Override
    public Currency[] getFiatCurrencies() {
        return new Currency[]{ Currency.USD, Currency.EUR };
    }

    @Override
    public Currency[] getCryptoCurrencies() {
        return new Currency[]{ Currency.BTC };
    }

    @Override
    public ExchangeApiClient getApiClient() {
        return new VircurexApiClient(this);
    }


}
