package com.mowatcher.activity;

import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mowatcher.R;
import com.mowatcher.tempo.GerenciadorTempo;
import com.mowatcher.tempo.TempoInvestido;

public class ListagemActivity extends BaseActivity {

	GerenciadorTempo gerenciador = new GerenciadorTempo();
	ListView listField;
	ArrayAdapter<TempoInvestido> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listagem);
		
		listField = (ListView) findViewById(R.id.listagem);
		adapter = new ArrayAdapter<TempoInvestido>(this, android.R.layout.simple_list_item_1, 
				android.R.id.text1, new ArrayList<TempoInvestido>());
		listField.setAdapter(adapter);
		
		new AtualizaListagem().execute();
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
			for(TempoInvestido t: gerenciador.getTIs()) {
				adapter.add(t);
			}
		}
	}
}