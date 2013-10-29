import at.outdated.bitcoin.exchange.api.BaseTest;
import at.outdated.bitcoin.exchange.api.account.AccountInfo;
import at.outdated.bitcoin.exchange.api.currency.Currency;
import at.outdated.bitcoin.exchange.api.currency.CurrencyValue;
import at.outdated.bitcoin.exchange.api.market.*;
import at.outdated.bitcoin.exchange.kraken.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ebirn
 * Date: 17.09.13
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class KrakenTest extends BaseTest {


    @Before
    public void init() {
        market = Markets.getMarket("kraken");
        client = new KrakenClient(market);
        log = LoggerFactory.getLogger(getClass());
    }


    @Test
    public void testTickerclient() {

        TickerValue ticker = client.getTicker(new AssetPair(Currency.BTC, Currency.EUR));
        System.out.println("ticker: " + ticker);

        assertTicker(ticker);
    }

    @Test
    public void testDepthClient() {
        MarketDepth depth = client.getMarketDepth(new AssetPair(Currency.BTC, Currency.EUR));

        assertDepth(depth);
    }


    @Test
    public void testMarketDepthPrice() {


        MarketDepth depth = client.getMarketDepth(new AssetPair(Currency.BTC, Currency.LTC));


        CurrencyValue tradeVolume = new CurrencyValue(90.0, Currency.LTC);


        CurrencyValue resultPrice = null;

        resultPrice = depth.getPrice(TradeDecision.SELL, tradeVolume);
        log.info("SELL {} get {}", tradeVolume, resultPrice);

        resultPrice = depth.getPrice(TradeDecision.BUY, tradeVolume);
        log.info("BUY {} get {}", tradeVolume, resultPrice);

    }

}
