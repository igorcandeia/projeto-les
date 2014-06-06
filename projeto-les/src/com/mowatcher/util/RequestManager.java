package com.mowatcher.util;

import java.io.IOException;
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
	
	private StringBuilder s;
	private boolean result;
	private JSONObject json;

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

	/**
	 * Envia uma requisição do tipo GET para o servidor, com o intuido de salvar
	 * determinado TI de um usuário
	 */
	public boolean saveTI(TempoInvestido ti, int usuarioId) {
		result = false;
		s = new StringBuilder(AppConfig.HOST_PORT + "save_db?");
		s.append("ano=" + ti.getAno());
		s.append("&atividade=" + ti.getAtividade());
		s.append("&data=" + ti.getData().getTime());
		s.append("&prioridade=" + ti.getPrioridade());
		s.append("&semanaDoAno=" + ti.getSemanaDoAno());
		s.append("&tempo=" + ti.getTempo());
		s.append("&tipo=" + ti.getTipo());
		s.append("&user_id=" + usuarioId);

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(s.toString());
				ResponseHandler<String> responseHandler = new BasicResponseHandler();

				try {
					String response = client.execute(get, responseHandler);
					Log.d("Save TI Success", response);
					result = true;
				} catch (ClientProtocolException e) {
					Log.d("Save TI Erro", e.getMessage());
				} catch (IOException e) {
					Log.d("Save TI Erro", e.getMessage());
				}

			}
		}).start();
		return result;
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
	public List<TempoInvestido> loadTIS(final String email) {
		List<TempoInvestido> tempos = null;

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					json = JsonRead.readJsonFromUrl(AppConfig.HOST_PORT + "email="
							+ email);
				} catch (IOException e) {
					Log.d("Load TIs Erro", e.getMessage());
				} catch (JSONException e) {
					Log.d("Load TIs Erro", e.getMessage());
				}
			}
		}).start();
		// json = new JSONObject(getJsonTest());
		try {
			JSONArray tis = json.getJSONArray("atividades");
			tempos = new ArrayList<TempoInvestido>();

			for (int i = 0; i < tis.length(); i++) {
				JSONObject child = tis.getJSONObject(i);

				String atividade = child.getString("atividade");
				float tempo = Float.parseFloat(child.getString("tempo"));
				EnumTipo tipo = EnumTipo.valueOf(child.getString("tipo"));
				EnumPrioridade prioridade = EnumPrioridade.valueOf(child.getString("prioridade"));
				Long milli = Long.parseLong(child.getString("data"));

				GregorianCalendar c = new GregorianCalendar();
				c.setTimeInMillis(milli);

				TempoInvestido t = new TempoInvestido(atividade, tempo, tipo,
						prioridade, c);
				tempos.add(t);
			}

		} catch (JSONException e) {
			Log.d("Load TIs Erro", e.getMessage());
		}
		return tempos;
	}
}