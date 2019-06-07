package net.surfm.crypto.bitfinex.wsclient;

import java.util.function.BiConsumer;
import java.util.logging.Logger;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.jnidzwetzki.bitfinex.v2.BitfinexClientFactory;
import com.github.jnidzwetzki.bitfinex.v2.BitfinexWebsocketClient;
import com.github.jnidzwetzki.bitfinex.v2.entity.BitfinexOrderBookEntry;
import com.github.jnidzwetzki.bitfinex.v2.manager.OrderbookManager;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexOrderBookSymbol;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexOrderBookSymbol.Frequency;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexOrderBookSymbol.Precision;
import com.github.jnidzwetzki.bitfinex.v2.symbol.BitfinexSymbols;

import net.surfm.crypto.bitfinex.api.dto.CurPair;
import net.surfm.crypto.bitfinex.api.dto.Currency;

@Component
public class TradeChannel implements ApplicationListener<ApplicationReadyEvent> , BiConsumer<BitfinexOrderBookSymbol, BitfinexOrderBookEntry> {

	private final static Logger LOG = Logger.getLogger(TradeChannel.class.getName());
	private long nextShotAt = System.currentTimeMillis();

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		subscribe();

	}
	
	public void subscribe() {
		BitfinexWebsocketClient client = BitfinexClientFactory.newSimpleClient();
		client.connect();
		CurPair cp = CurPair.gen(Currency.BTC, Currency.USD);
		cp.register(0.01);
		final BitfinexOrderBookSymbol orderbookConfiguration = BitfinexSymbols
				.orderBook(cp.genCurrencyPair(), Precision.P0, Frequency.F0, 25);
		final OrderbookManager orderbookManager = client.getOrderbookManager();
		orderbookManager.registerOrderbookCallback(orderbookConfiguration, this);
		
		orderbookManager.subscribeOrderbook(orderbookConfiguration);		
	}

	@Override
	public void accept(BitfinexOrderBookSymbol orderbookConfig, BitfinexOrderBookEntry entry) {
		if(System.currentTimeMillis() > nextShotAt) {
			nextShotAt = System.currentTimeMillis() + (1000*60*1);
			System.out.format("Got entry (%s) for orderbook (%s)\n", entry, orderbookConfig);
		}
	}

}
