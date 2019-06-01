package net.surfm.crypto.bitfinex.api.dto;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.surfm.exception.SurfmRuntimeException;
import net.surfm.infrastructure.ReflectionTool;

public class SortMapper {

	private static final ObjectMapper jsonMapper = new ObjectMapper();

	public static <R> R convert(JSONArray source, Class<R> rC)
			 {
		JSONObject obj = new JSONObject();
		List<String> fNames = ReflectionTool.getFieldNames(rC);
		for (int i = 0; i < fNames.size(); i++) {
			Object o = source.get(i);
			obj.putOnce(fNames.get(i), o);
		}
		try {
			return  jsonMapper.readValue(obj.toString(), rC);
		} catch (Exception e) {
			throw new SurfmRuntimeException(e);
		}

	}

}
