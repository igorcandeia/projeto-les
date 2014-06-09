package com.mowatcher.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mowatcher.tempo.EnumPrioridade;
import com.mowatcher.tempo.EnumTipo;
import com.mowatcher.tempo.TempoInvestido;

/**
 * Responsável por gerenciar as requisições para o servidor remoto.
 */
public class RequestManager {
	
	/**
	 * Usada unicamente para debug
	 */
	public static String getJsonTest() {
		StringBuilder s = new StringBuilder();
		s.append("{\"atividades\":[");
		s.append("{\"atividade\":\"algumacoisa\",\"tempo\":\"20\",\"tipo\":\"LAZER\",\"prioridade\":\"ALTA\",\"data\":\"1401839455670\" },");
		s.append("{\"atividade\":\"outracoisa\",\"tempo\":\"20\",\"tipo\":\"LAZER\",\"prioridade\":\"ALTA\",\"data\":\"1401839455670\" }");
		s.append("]}");
		return s.toString();
	}
	
	public static String simulateRequest() {
		JSONObject json = null;
		try {
			json = JsonRead.readJsonFromUrl("https://graph.facebook.com/19292868552");
		} catch (IOException e) {
			Log.d("Request Erro", e.getMessage());
		} catch (JSONException e) {
			Log.d("Request Erro", e.getMessage());
		}
		return json.toString();
	}

	/**
	 * Envia uma requisição do tipo GET para o servidor, com o intuido de salvar
	 * determinado TI de um usuário
	 */
	public boolean saveTI(TempoInvestido ti, Long usuarioId) {
		try {
			
			StringBuilder s = new StringBuilder(AppConfig.HOST_PORT + "save_db?");
			s.append("ano=" + ti.getAno());
			s.append("&atividade='" + URLEncoder.encode(ti.getAtividade() , "utf-8")  + "'");
			s.append("&data='" + ti.getData().getTime() + "'");
			s.append("&prioridade='" + ti.getPrioridade() + "'");
			s.append("&semanaDoAno=" + ti.getSemanaDoAno());
			s.append("&tempo=" + ti.getTempo());
			s.append("&tipo='" + ti.getTipo() + "'");
			s.append("&user_id='" + usuarioId + "'");
			
			Log.d("request_save", s.toString());
	
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(s.toString());
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			String response = client.execute(get, responseHandler);
			Log.d("Save TI Success", response);
			return true;
		} catch (UnsupportedEncodingException e1) {
			Log.d("Save TI Erro", e1.getMessage());
		} catch (ClientProtocolException e) {
			Log.d("Save TI Erro", e.getMessage());
		} catch (IOException e) {
			Log.d("Save TI Erro", e.getMessage());
		}
		return false;
	}

	/**
	 * Carrega todos os tempos investidos de determinado usuário baseado no seu
	 * email.
	 * 
	 * O json resultante da requisição para a url é transformado em um
	 * JSONObject, e convertido em uma lista de TIs
	 * 
	 * O Json deve estar no seguinte formato: 
	 * {"atividades":[
	 * {"atividade":"algumacoisa","tempo":"20","tipo":"LAZER","prioridade":"ALTA","data":"1401839455670"}, 
	 * {"atividade":"outracoisa","tempo":"20","tipo":"LAZER","prioridade":"ALTA","data":"1401839455670" } ]}
	 */
	public List<TempoInvestido> loadTIS(Long userID) {
		List<TempoInvestido> tempos = null;
		JSONObject json;
		try {	
			json = JsonRead.readJsonFromUrl(AppConfig.HOST_PORT + "get_db?user_id="+ userID);
			//json = new JSONObject(getJsonTest());
		
			JSONArray tis = json.getJSONArray("atividades");
			tempos = new ArrayList<TempoInvestido>();

			for (int i = 0; i < tis.length(); i++) {
				JSONObject child = tis.getJSONObject(i);

				String atividade = child.getString("atividade");
				float tempo = Float.parseFloat(child.getString("tempo"));
				EnumTipo tipo = EnumTipo.valueOf( child.getString("tipo").toUpperCase());
				EnumPrioridade prioridade = EnumPrioridade.valueOf(child.getString("prioridade").toUpperCase());
				Long milli = Long.parseLong(child.getString("data"));

				GregorianCalendar c = new GregorianCalendar();
				c.setTimeInMillis(milli);

				TempoInvestido t = new TempoInvestido(atividade, tempo, tipo,
						prioridade, c);
				tempos.add(t);
			}

		} catch (IOException e) {
			Log.d("Load TIs Erro IO", e.getMessage());
		} catch (JSONException e) {
			Log.d("Load TIs Erro JSON", e.getMessage());
		}
		return tempos;
	}
}