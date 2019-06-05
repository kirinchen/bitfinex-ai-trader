package net.surfm.crypto.bitfinex.dao;

import org.springframework.data.repository.CrudRepository;

import net.surfm.crypto.bitfinex.model.TradeAvg;

public interface TradeAvgDao extends CrudRepository<TradeAvg, Integer> {

}
