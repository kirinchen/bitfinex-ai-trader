package net.surfm.crypto.bitfinex.api;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.surfm.crypto.bitfinex.api.dto.CurPair;
import net.surfm.crypto.bitfinex.api.dto.SortMapper;
import net.surfm.crypto.bitfinex.api.dto.TradeBookDto;
import net.surfm.crypto.bitfinex.api.dto.TradeDto;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.StrPlacer;

@Service
public class PublicApi {
	
	@Inject
	private RestTemplate rest;
	@Value("${bitfinexPublicPath}")
	private String accountSdkHost;	
	
	
	
	public List<TradeDto> listTrades(CurPair cp,long start,long end){
		String urlTemp = accountSdkHost+"trades/t${type}/hist?start=${start}&end=${end}";
		String url = StrPlacer.build(urlTemp).
				place("type", cp) .
				place("start", start).
				place("end", end).
				replace();
		CommUtils.pl(url);
		HttpEntity<String> resp = rest.getForEntity(url, String.class);
		return  SortMapper.convertList(resp.getBody(), TradeDto.class) ;
	}
	
	public enum Precision{ 
		P0, P1, P2, P3, P4, R0
	}
	
	public List<TradeBookDto> listTradeBook(CurPair cp,Precision p,int len){
		String urlTemp = accountSdkHost+"book/t${cp}/${p}${len}";
		String url = StrPlacer.build(urlTemp).
				place("cp", cp) .
				place("p", p).
				place("len",  len>0 ?  "?len="+len : "").replace();
		CommUtils.pl(url);
		HttpEntity<String> resp = rest.getForEntity(url, String.class);
		return  SortMapper.convertList(resp.getBody(), TradeBookDto.class) ;
	}

}
