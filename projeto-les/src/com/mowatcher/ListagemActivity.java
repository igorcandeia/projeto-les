package com.mowatcher;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mowatcher.tempo.GerenciadorTempo;

public class ListagemActivity extends BaseActivity {

	GerenciadorTempo gerenciador;
	ListView listField;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listagem);

		gerenciador = new GerenciadorTempo();
		
		handler = new Handler();
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				gerenciador.loadAndSyncronizedTIS("jose@gmail.com"); // carrega os tis remotamente
				listField = (ListView) findViewById(R.id.listagem);
				List<String> atividades = gerenciador.getAtividades(); 
				String[] atvs = new String[atividades.size()];
				atividades.toArray(atvs);
				for (String atv : atvs) {
					adapter.add(atv);
				}
				listField.setAdapter(adapter);
			}
		}).start();
		
	}

}
