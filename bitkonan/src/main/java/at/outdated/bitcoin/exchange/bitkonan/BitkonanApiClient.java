package at.outdated.bitcoin.exchange.bitkonan;

import at.outdated.bitcoin.exchange.api.ExchangeApiClient;
import at.outdated.bitcoin.exchange.api.Market;
import at.outdated.bitcoin.exchange.api.account.AccountInfo;
import at.outdated.bitcoin.exchange.api.currency.Currency;
import at.outdated.bitcoin.exchange.api.currency.CurrencyValue;
import at.outdated.bitcoin.exchange.api.market.*;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: ebirn
 * Date: 26.05.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class BitkonanApiClient extends ExchangeApiClient {

    public BitkonanApiClient(Market market) {
        super(market);
    }

    @Override
    public AccountInfo getAccountInfo() {

        //  https://bitkonan.com/api/balance/
        // https://bitkonan.com/api/open_orders

        WebTarget balanceTarget = client.target("https://bitkonan.com/api/balance/");
        WebTarget ordersTarget = client.target("https://bitkonan.com/api/open_orders");


        Future<String> rawBalance =  asyncRequest(balanceTarget, String.class, "GET", null, true);
        Future<String> rawOrders = asyncRequest(ordersTarget, String.class , "GET", null, true);

        try {
            log.info("rawBalance: " + rawBalance.get());
            log.info("rawOrders: " + rawOrders.get());
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        BitkonanAccountInfo info = new BitkonanAccountInfo();

        return info;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public MarketDepth getMarketDepth(Currency base, Currency quote) {

        // https://bitkonan.com/api/orderbook/?group=0

        WebTarget orderbook = client.target("https://bitkonan.com/api/orderbook/");

        String obString = super.simpleGetRequest(orderbook, String.class);

        JsonObject konanDepth = jsonFromString(obString);

        double[][] asks = parseNestedArray(konanDepth.getJsonArray("asks"));
        double[][] bids = parseNestedArray(konanDepth.getJsonArray("bids"));


        MarketDepth depth = new MarketDepth();

        depth.setBaseCurrency(base);

        for(double[] bid : bids) {
            double price = bid[0];
            double volume = bid[1];
            depth.getBids().add(new MarketOrder(TradeDecision.BUY, new CurrencyValue(volume, base), new CurrencyValue(price, quote)));
        }

        for(double[] ask : asks) {
            double price = ask[0];
            double volume = ask[1];
            depth.getAsks().add(new MarketOrder(TradeDecision.SELL, new CurrencyValue(volume, base), new CurrencyValue(price, quote)));
        }

        return depth;
    }

    @Override
    protected <R> R simpleGetRequest(WebTarget target, Class<R> resultClass) {

        R result = null;

        String resultStr = super.simpleGetRequest(target, String.class);

        result = BitkonanJsonResolver.convertFromJson(resultStr, resultClass);

        return result;
    }

    @Override
    public TickerValue getTicker(AssetPair asset) {


        WebTarget tickerResource = client.target("https://bitkonan.com/api/ticker/");

        BitkonanTickerValue response = simpleGetRequest(tickerResource, BitkonanTickerValue.class);


        TickerValue value = response.getTickerValue();
        value.setCurrency(asset.getQuote());

        return value;
    }

    @Override
    public Number getLag() {
        return 0.12345678910;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    protected <Form> Invocation.Builder setupProtectedResource(WebTarget res, Entity<Form> entity) {

        Invocation.Builder builder = res.request();

        // Api-Key: The same as generated by our system when you created the API.
        // Api-Secret: message digest as lowercase hexits, generated using HMAC-SHA256 algorithm. Constructed from the the following concatenated strings: [POST Parameters]:[Timestamp].
        // Api-Timestamp: current timestamp in UNIX format.

        String apiKey = getUserId();
        String apiSecret = getSecret();
        long apiTimestamp = (new Date()).getTime()/1000L;

        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secret_spec = new SecretKeySpec(apiSecret.getBytes("UTF-8"), "HmacSHA256");
            mac.init(secret_spec);

            // path + NUL + POST (incl. nonce)
            String content = entity == null ? "" : entity.getEntity().toString();

            String payload = content + ":" + Long.toString(apiTimestamp);

            byte[] rawSignature = mac.doFinal(payload.getBytes());

            String signature = new String(Hex.encodeHex(rawSignature, true));

            builder.header("Api-Key", apiKey);
            //log.debug("Api-Key: {}", apiKey);

            builder.header("Api-Sign", signature);
            //log.debug("Api-Sign: {}", signature);

            builder.header("Api-Timestamp", apiTimestamp);
            //log.debug("Api-Timestamp: {}", apiTimestamp);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return builder;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
