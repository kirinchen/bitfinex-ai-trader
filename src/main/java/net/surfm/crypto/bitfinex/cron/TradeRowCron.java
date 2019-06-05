package net.surfm.crypto.bitfinex.cron;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.surfm.crypto.bitfinex.api.PublicApi;
import net.surfm.crypto.bitfinex.api.dto.CurPair;
import net.surfm.crypto.bitfinex.api.dto.Currency;
import net.surfm.crypto.bitfinex.api.dto.TradeDto;
import net.surfm.crypto.bitfinex.dao.TradeAvgDao;
import net.surfm.crypto.bitfinex.dao.TradeRowDao;
import net.surfm.crypto.bitfinex.model.TradeRow;
import net.surfm.infrastructure.CommUtils;

@Component
public class TradeRowCron {

	@Inject
	private TradeRowDao dao;
	@Inject
	private PublicApi publicApi;
	@Inject
	private DozerBeanMapper mapper;
	@Inject 
	private TradeAvgDao avgDao;
	
	@Value("#{'${monitor.currency}'.split(',')}") 
	private List<String> monitorCurrencyList;

	@Scheduled(fixedRate = 1000 * 60 * 10)
	public void fixedLoad() {
		monitorCurrencyList.stream().map(cs-> Currency.valueOf(cs)).forEach(c-> record(c));
	}
	
	private void record(Currency in) {
		Optional<TradeRow> lastO = dao.findFirstByInCurrencyOrderByTimestampAsc(in);
		long start = lastO.isPresent() ? lastO.get().getTimestamp() : 0;
		long end = start == 0 ? 0 : System.currentTimeMillis();
		Currency out = Currency.USD;
		List<TradeDto> list = publicApi.listTrades(CurPair.gen(in,out ), start, end);
		List<TradeRow> entityList = list.stream().map(dto-> { 
			TradeRow ans = mapper.map(dto, TradeRow.class);
			ans.setInCurrency(in);
			ans.setOutCurrency(out);
			ans.setDoneAt(new Date(ans.getTimestamp()));
			return ans;
		}).collect(Collectors.toList());
		dao.saveAll(entityList);
		
		CommUtils.pl("record done");
	}

}
