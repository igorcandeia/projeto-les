package com.mowatcher;

import java.util.GregorianCalendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mowatcher.tempo.EnumPrioridade;
import com.mowatcher.tempo.EnumTipo;
import com.mowatcher.tempo.GerenciadorTempo;
import com.mowatcher.tempo.TempoInvestido;
import com.mowatcher.util.DatabaseSeed;

public class MainActivity extends BaseActivity {

	GerenciadorTempo gerenciador;
	ListView listField;
	EditText horasField;
	EditText atividadeField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gerenciador = new GerenciadorTempo();
		
		// popula o BD com o atividades já pré-cadestradas
		new DatabaseSeed().populaBD();
		
		Log.d("DB TST semana atual", String.valueOf(gerenciador.getTemposSemana(0).size()));
		Log.d("DB TST semana passada", String.valueOf(gerenciador.getTemposSemana(1).size()));
		Log.d("DB TST semana retrasada", String.valueOf(gerenciador.getTemposSemana(2).size()));
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
			
			gerenciador = new GerenciadorTempo();
			gerenciador.adicionaTI(ti);
			
			//lança alert
			Toast.makeText(MainActivity.this, "Atividade Adicionada", Toast.LENGTH_LONG).show();
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

}