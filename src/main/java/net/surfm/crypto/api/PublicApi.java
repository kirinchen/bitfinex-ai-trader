package net.surfm.crypto.api;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.surfm.crypto.api.dto.SortMapper;
import net.surfm.crypto.api.dto.TradeDto;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.StrPlacer;

@Service
public class PublicApi {
	
	@Inject
	private RestTemplate rest;
	@Value("${bitfinexPublicPath}")
	private String accountSdkHost;	
	
	
	
	public List<TradeDto> listTrades(String type,long start,long end){
		String urlTemp = accountSdkHost+"trades/t${type}/hist?start=${start}&end=${end}";
		String url = StrPlacer.build(urlTemp).
				place("type", type) .
				place("start", start).
				place("end", end).
				replace();
		CommUtils.pl(url);
		HttpEntity<String> resp = rest.getForEntity(url, String.class);
		return  SortMapper.convertList(resp.getBody(), TradeDto.class) ;
	}
	

}
