package com.mowatcher.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.mowatcher.tempo.EnumPrioridade;
import com.mowatcher.tempo.EnumTipo;
import com.mowatcher.tempo.TempoInvestido;

public class DatabaseSeed {
	
	public void populaBD () {
		// se não existir essa devida atividade, então popula o BD
		// evitar de popular o BD com os mesmos dados
		if (TempoInvestido.getAtividadeByNome("atividade 1 teste").isEmpty()) {
			populaSemanas();
		}
	}
	
	private void populaSemanas() {
		
		Calendar cal = Calendar.getInstance();
		int anoAtual = cal.get(Calendar.YEAR);
		int mesAtual = cal.get(Calendar.MONTH);
		int diaSemanaPassada = cal.get(Calendar.DAY_OF_MONTH) - 7;
		int diaSemanaRetrasada = cal.get(Calendar.DAY_OF_MONTH) - 14;
		
		// Atividades dessa semana
		TempoInvestido t1 = new TempoInvestido("atividade 1 teste", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXA, 
				new GregorianCalendar());
		
		TempoInvestido t2 = new TempoInvestido("atividade 2", 
				30f, EnumTipo.LAZER, EnumPrioridade.ALTA, new GregorianCalendar());
		
		TempoInvestido t3 = new TempoInvestido("atividade 3", 
				10f, EnumTipo.TRABALHO, EnumPrioridade.BAIXISSIMA, new GregorianCalendar());
		
		TempoInvestido t4 = new TempoInvestido("atividade 4", 
				10f, EnumTipo.TRABALHO, EnumPrioridade.BAIXISSIMA, new GregorianCalendar());
		
		// Atividades da semana passada
		TempoInvestido t5 = new TempoInvestido("atividade 5", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaPassada));
		TempoInvestido t6 = new TempoInvestido("atividade 6", 
				20f, EnumTipo.TRABALHO, EnumPrioridade.MEDIA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaPassada+1));
		TempoInvestido t7 = new TempoInvestido("atividade 7", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaPassada+2));
		TempoInvestido t8 = new TempoInvestido("atividade 8", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXISSIMA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaPassada+3));
		
		// Atividades da semana retrasada
		TempoInvestido t9 = new TempoInvestido("atividade 9", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaRetrasada));
		TempoInvestido t10 = new TempoInvestido("atividade 9", 
				20f, EnumTipo.TRABALHO, EnumPrioridade.MEDIA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaRetrasada+1));
		TempoInvestido t11 = new TempoInvestido("atividade 10", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaRetrasada+2));
		TempoInvestido t12 = new TempoInvestido("atividade 11", 
				20f, EnumTipo.LAZER, EnumPrioridade.BAIXISSIMA, 
				new GregorianCalendar(anoAtual, mesAtual, diaSemanaRetrasada+3));
		
		// salva tudo no BD
		t1.save();
		t2.save();
		t3.save();
		t4.save();
		t5.save();
		t6.save();
		t7.save();
		t8.save();
		t9.save();
		t10.save();
		t11.save();
		t12.save();
	}

}
