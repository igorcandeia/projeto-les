package com.mowatcher.activity;

import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mowatcher.R;
import com.mowatcher.tempo.Atividade;
import com.mowatcher.tempo.EnumPrioridade;
import com.mowatcher.tempo.EnumTipo;
import com.mowatcher.tempo.GerenciadorTempo;
import com.mowatcher.tempo.TempoInvestido;

public class CadastrarTIActivity extends BaseActivity {

	GerenciadorTempo gerenciador = new GerenciadorTempo();
	ListView listField;
	EditText horasField;
	EditText atividadeField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getWindow().setSoftInputMode(
		         WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	public void cadastrarAtividade(View v) {
		
		horasField = (EditText) findViewById(R.id.campoHoras);
		atividadeField = (EditText) findViewById(R.id.campoAtividade);
		// verifica se realmente foi digitado um float
		if (validaHora(horasField)) {
			
			float horas = Float.parseFloat(horasField.getText().toString());
			String nome = atividadeField.getText().toString();
			
			Atividade a = new Atividade(nome, EnumTipo.LAZER, 
					EnumPrioridade.BAIXA);
			TempoInvestido ti = new TempoInvestido(a, horas,
						new GregorianCalendar());
			
			// Todas as transações que envolvem requisição tem quer ser seguidas por
			// uma tarefa assyncrona nesse estilo, para evitar nullpointerexception
			new SaveTI().execute(ti);
			Intent rel_semanal = new Intent(this, RelatorioSemanal.class);
			startActivity(rel_semanal);
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
			Toast.makeText(CadastrarTIActivity.this, "Atividade Adicionada", Toast.LENGTH_LONG).show();
		}
	}
}