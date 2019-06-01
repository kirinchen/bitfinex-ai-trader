package net.surfm.crypto.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.surfm.exception.SurfmRuntimeException;
import net.surfm.infrastructure.ReflectionTool;

public class SortMapper {

	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
	
	public static <R> List<R> convertList(String source,Class<R> rc){
		JSONArray  ja = new JSONArray(source);
		return convertList(ja, rc);
	}
	
	public static <R> List<R> convertList(JSONArray  ja , Class<R> rc){
		List<R> ans = new ArrayList<R>();
		for (int i = 0; i < ja.length(); i++) {
			ans.add(SortMapper.convert(ja.getJSONArray(i), rc));
		}
		return ans;
	}

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
