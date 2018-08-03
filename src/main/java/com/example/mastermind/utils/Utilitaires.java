package com.example.mastermind.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilitaires {

	public static final String URL_TEST = "http://172.16.37.129/api/test";
	public static final String URL_START = "http://172.16.37.129/api/start";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";
	public static final String TOKEN = "token";
	public static final String NAME = "name";
	public static final String RESULT = "result";
	public static final String GOOD = "good";
	public static final String SIZE = "size";
	public static final String ACC_OPEN = "{\"";
	public static final String ACC_CLOSE = "\"}";
	public static final String CONST1 = "\",\"";
	public static final String CONST2 = "\":\"";
	public static final String UTF8 = "UTF-8";
	public static final String APP_JSON = "application/json";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	public static final String NAME_VALUE = "Mastermind7";
	public static final String TOKEN_VALUE = "tokenmm7";

	private Utilitaires() {
	}

	public static Map<String, Object> sendWithMsgBody(String url, String methode, String msgCorps) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(methode);
		if (!METHOD_GET.equalsIgnoreCase(methode)) {
			con.setDoOutput(true);
		}
		con.setRequestProperty(CONTENT_TYPE, APP_JSON);
		con.setRequestProperty(ACCEPT, APP_JSON);

		OutputStreamWriter out = null;
		if (!METHOD_GET.equalsIgnoreCase(methode)) {
			out = new OutputStreamWriter(con.getOutputStream(), UTF8);
			out.write(msgCorps);
			out.flush();
			out.close();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), UTF8));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		Map<String, Object> linkedHashMapFromString = getLinkedHashMapFromString(response.toString());
		in.close();
		con.disconnect();
		return linkedHashMapFromString;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getLinkedHashMapFromString(String value) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(value, LinkedHashMap.class);
	}

	public static String buildMsg(String token, String result, String propName) {
		StringBuilder build = new StringBuilder();
		build.append(ACC_OPEN);
		build.append(TOKEN);
		build.append(CONST2);
		build.append(token).append(CONST1);
		build.append(propName).append(CONST2).append(result);
		build.append(ACC_CLOSE);
		return build.toString();
	}

	public static String tabToString(String[] tab) {
		StringBuilder result = new StringBuilder();
		for (String str : tab) {
			result.append(str);
		}
		return result.toString();
	}

	public static String getString(int[] tab) {
		StringBuilder result = new StringBuilder();
		for (int i : tab) {
			result.append(i);
		}
		return result.toString();
	}

	public static String padding(String s, int n, int c) {
		StringBuilder sb = new StringBuilder(s);
		int sbLength = sb.length();
		if (n > 0 && n > sbLength) {
			for (int i = 0; i < (n - sbLength); i++) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
