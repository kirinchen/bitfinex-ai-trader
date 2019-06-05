package net.surfm.crypto.bitfinex.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.surfm.crypto.bitfinex.api.dto.Currency;
import net.surfm.crypto.bitfinex.model.TradeRow;


public interface TradeRowDao extends CrudRepository<TradeRow, Integer> {

	Optional<TradeRow> findFirstByInCurrencyOrderByTimestampAsc(Currency c);
	
}
