package com.mowatcher;

import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mowatcher.activity.BaseActivity;
import com.mowatcher.tempo.EnumPrioridade;
import com.mowatcher.tempo.EnumTipo;
import com.mowatcher.tempo.GerenciadorTempo;
import com.mowatcher.tempo.TempoInvestido;
import com.mowatcher.util.DatabaseSeed;

public class MainActivity extends BaseActivity {

	GerenciadorTempo gerenciador = new GerenciadorTempo();
	ListView listField;
	EditText horasField;
	EditText atividadeField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// popula o BD com o atividades já pré-cadastradas
		new DatabaseSeed().populaBD();
		
		Button cancel = (Button)findViewById(R.id.btnCancel);
	    cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, PreActivity.class);
				startActivity(i);
			}
		});
	}
	
	public void cadastrarAtividade(View v) {
		
		horasField = (EditText) findViewById(R.id.campoHoras);
		atividadeField = (EditText) findViewById(R.id.campoAtividade);
		// verifica se realmente foi digitado um float
		if (validaHora(horasField)) {
			
			float horas = Float.parseFloat(horasField.getText().toString());
			String nome = atividadeField.getText().toString();
			
			TempoInvestido ti = new TempoInvestido(nome, 
						horas, 
						EnumTipo.LAZER, 
						EnumPrioridade.BAIXA,
						new GregorianCalendar());
			
			// Todas as transações que envolvem requisição tem quer ser seguidas por
			// uma tarefa assyncrona nesse estilo, para evitar nullpointerexception
			new SaveTI().execute(ti);
		}
	}

	public boolean validaHora(EditText horasField) {
		try {
			Float.parseFloat(horasField.getText().toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Tarefa Assyncrona para salvar um Tempo Investido remotamente.
	 */
	class SaveTI extends AsyncTask<TempoInvestido, Integer, Long> {
		/**
		 * É executado em uma nova thread, para fazer a requisição.
		 */
		protected Long doInBackground(TempoInvestido... tempos) {
			for (TempoInvestido t: tempos) {
				gerenciador.adicionaTI(t, 1L); // salva o ti remotamente.
			}
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
			Toast.makeText(MainActivity.this, "Atividade Adicionada", Toast.LENGTH_LONG).show();
		}
	}
}