package com.mowatcher.activity;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Set;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.mowatcher.R;
import com.mowatcher.tempo.GerenciadorTempo;

public class RelatorioSemanal extends BaseActivity {

	GerenciadorTempo gerenciador = new GerenciadorTempo();
	WebView wvGrafico;
    String strURL;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relatorio_semanal);
				
		new AtualizaListagem().execute();
	}
	
	public void drawGrafic() {
		Set<String> atividades = gerenciador.getAtividadesMaisRecentes().keySet();
		Collection<Float> tempos = gerenciador.getAtividadesMaisRecentes().values();
		String parserAtv = "";
		String parserTmp = "";
		
		for (String a: atividades) {
			try {
				parserAtv += URLEncoder.encode(a, "utf-8") + "|";
			} catch (UnsupportedEncodingException e) {
				Log.d("URL Encoder erro", e.getMessage());
			}
		}
		int i =0;
		for (Float t: tempos) {
			parserTmp += t.intValue();
			if (i < tempos.size() -1) {
				parserTmp += ",";
			}
			i++;
		}

		strURL = "https://chart.googleapis.com/chart?" +
				"cht=p3&" + // define o
							// tipo
							// do
							// gráfico
				"chxt=x,y&" + // imprime os valores dos eixos X, Y
				"chs=" + 700 + "x" + (300) + "&" + // define o tamanho da imagem
				"chd=t:" + parserTmp +"&" + // valor de cada coluna do gráfico
				"chl=" + parserAtv + "&" + // rótulo para cada coluna
				"chdl=Atividades&" + // legenda do gráfico
				"chxr=1,0,50&" + // define o valor de início e fim do eixo
				"chds=0,50&" + // define o valor de escala dos dados
				"chg=0,5,0,0&" + // desenha linha horizontal na grade
				"chco=3D7930&" + // cor da linha do gráfico
				"chtt=Ranking+de+Atividades+Da+Semana&" + // cabeçalho do gráfico
				"chm=B,C5D4B5BB,0,0,0"; // fundo verde
		
		Log.d("url_graph", strURL);
		
		wvGrafico = (WebView) findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
		
	}
	
	/**
	 * Tarefa Assyncrona para atualizar a listagem referente ao adapter Adapter
	 * e com base nos Tis recebidos remotamente
	 * 
	 */
	class AtualizaListagem extends AsyncTask<URL, Integer, Long> {
		/**
		 * É executado em uma nova thread, para fazer a requisição.
		 */
		protected Long doInBackground(URL... urls) {
			// carrega os tis remotamente com base no usuario de id=1
			gerenciador.loadAndSyncronizedTIS(1L); 
			return 0L;
		}

		protected void onProgressUpdate(Integer... progress) {
			// setProgressPercen(progress[0]);
		}
		/**
		 * É executado ao fim do doInBackground e é chamado na thread original
		 * da activity.
		 */
		protected void onPostExecute(Long result) {
			TextView horas = (TextView) findViewById(R.id.horasSemana);
			horas.setText(String.valueOf(gerenciador.getHorasSemana(0)) +  "Horas Gastas");
			drawGrafic();
		}
	}
}
