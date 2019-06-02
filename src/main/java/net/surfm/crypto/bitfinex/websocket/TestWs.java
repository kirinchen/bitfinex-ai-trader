package net.surfm.crypto.bitfinex.websocket;

import java.util.function.BiConsumer;
import java.util.logging.Logger;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.jnidzwetzki.bitfinex.v2.BitfinexClientFactory;
import com.github.jnidzwetzki.bitfinex.v2.BitfinexWebsocketClient;
import com.github.jnidzwetzki.bitfinex.v2.entity.BitfinexOrderBookEntry;
import com.github.jnidzwetzki.bitfinex.v2.entity.currency.BitfinexCurrencyPair;
import com.github.jnidzwetzki.bitfinex.v2.manager.OrderbookManager;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexOrderBookSymbol;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexOrderBookSymbol.Frequency;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexOrderBookSymbol.Precision;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexSymbols;

@Component
public class TestWs implements ApplicationListener<ApplicationReadyEvent> {

	private final static Logger LOG = Logger.getLogger(TestWs.class.getName());

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOG.info("ApplicationReadyEvent!!!");
		BitfinexWebsocketClient client = BitfinexClientFactory.newSimpleClient();
		client.connect();
		BitfinexCurrencyPair.register("BTC","USD", 0.001);
		final BitfinexOrderBookSymbol orderbookConfiguration = BitfinexSymbols
				.orderBook(BitfinexCurrencyPair.of("BTC", "USD"), Precision.P0, Frequency.F0, 25);

		final OrderbookManager orderbookManager = client.getOrderbookManager();

		final BiConsumer<BitfinexOrderBookSymbol, BitfinexOrderBookEntry> callback = (orderbookConfig, entry) -> {
			System.out.format("Got entry (%s) for orderbook (%s)\n", entry, orderbookConfig);
		};

		orderbookManager.registerOrderbookCallback(orderbookConfiguration, callback);
		orderbookManager.subscribeOrderbook(orderbookConfiguration);

	}

}
