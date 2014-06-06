package com.mowatcher.tempo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import com.mowatcher.util.ConfigBD;
import com.mowatcher.util.RequestManager;

/**
 * Classe controladora do tempo investido em uma atividade
 */
public class GerenciadorTempo {
	
	private List<TempoInvestido> tIs;

	public GerenciadorTempo(String emailUser) {
		if (ConfigBD.BD_LOCAL) {
			tIs = TempoInvestido.getAll();
		}
		if (ConfigBD.BD_REMOTE) {
			tIs = new RequestManager().loadTIS(emailUser);
		}
	}

	public List<String> getAtividades() {
		List<String> atividades = new ArrayList<String>();
		for (TempoInvestido t : tIs) {
			atividades.add(t.getAtividade());
		}
		return atividades;
	}

	public void adicionaTI(TempoInvestido tI, int userID) {
		tIs.add(tI);
		if (ConfigBD.BD_LOCAL) {
			tI.save(); // salva no BD o T.I.
		}
		if (ConfigBD.BD_REMOTE) {
			new RequestManager().saveTI(tI, userID);
		}
	}

	public String[] getAtividadesMaisRecentes() {
		List<String> atividades = new ArrayList<String>();
		Calendar calendario = new GregorianCalendar();
		for (TempoInvestido t : tIs) {
			if ((t.getSemanaDoAno() == calendario.get(Calendar.WEEK_OF_YEAR))
					&& (t.getAno() == calendario.get(Calendar.YEAR)))
				atividades.add(t.getAtividade());
		}
		String[] array = {};
		return atividades.toArray(array);
	}

	/**
	 * semana=0 => semana atual, semana=1 => semana passada.
	 */
	public List<VisualizacaoRelatorio> getRelatorioSemanal(int semana) {
		String[] atividades = getAtividadesMaisRecentes();
		List<VisualizacaoRelatorio> relatorioSemanal = new ArrayList<VisualizacaoRelatorio>();
		List<TempoInvestido> tIs;
		for (int i = 0; i< atividades.length; i++){
			tIs = getTISAtividadeSemana(atividades[i], semana);
			float total = 0;
			for (TempoInvestido t : tIs) {
				total += t.getTempo();
			}
			relatorioSemanal.add(new VisualizacaoRelatorio(atividades[i], total));
		}
		Collections.sort(relatorioSemanal);
		return relatorioSemanal;
	}

	public float[] getPercentualUso(int semana) {
		List<TempoInvestido> tempos = getTemposSemana(semana);
		float[] percentuais = { 0, 0 };
		float total = 0;
		for (TempoInvestido t : tempos) {
			if (t.getTipo().equals(EnumTipo.LAZER)) {
				percentuais[0] += t.getTempo();
			} else if (t.getTipo().equals(EnumTipo.TRABALHO)) {
				percentuais[1] += t.getTempo();
			}
		}
		for (int i = 0; i <= 1; i++) {
			total += percentuais[i];
		}
		for (int i = 0; i <= 1; i++) {
			percentuais[i] /= total;
		}
		return percentuais;
	}

	public float[] getPercentualPrioridade(int semana) {
		List<TempoInvestido> tempos = getTemposSemana(semana);
		float[] percentuais = { 0, 0, 0, 0, 0 };
		float total = 0;
		for (TempoInvestido t : tempos) {
			if (t.getPrioridade().equals(EnumPrioridade.BAIXISSIMA)) {
				percentuais[0] += t.getTempo();
			} else if (t.getPrioridade().equals(EnumPrioridade.BAIXA)) {
				percentuais[1] += t.getTempo();
			} else if (t.getPrioridade().equals(EnumPrioridade.MEDIA)) {
				percentuais[2] += t.getTempo();
			} else if (t.getPrioridade().equals(EnumPrioridade.ALTA)) {
				percentuais[3] += t.getTempo();
			} else if (t.getPrioridade().equals(EnumPrioridade.ALTISSIMA)) {
				percentuais[4] += t.getTempo();
			}
		}
		for (int i = 0; i <= 4; i++) {
			total += percentuais[i];
		}
		for (int i = 0; i <= 4; i++) {
			percentuais[i] /= total;
		}
		return percentuais;
	}
	
	/**
	 * Retorna os tempos de um determinado dia de uma semana
	 * dia = 0 => domingo
	 * semana = 0 => semana atual
	 */
	public List<TempoInvestido> getTemposDiaSemana(int dia, int semana) {
		List<TempoInvestido> tempos = getTemposSemana(semana);
		List<TempoInvestido> novosTempos = new ArrayList<TempoInvestido>();
		for (TempoInvestido t: tempos ){
			Calendar c = Calendar.getInstance();
			c.setTime(t.getData());
			if (c.get(Calendar.DAY_OF_WEEK) == dia) 
				novosTempos.add(t);
		}
		return novosTempos;
	}

	/**
	 * Retorna os Tempos Investidos de uma certa semana começando do domingo.
	 * 
	 * semana=0 => tempos da semana atual semana=1 => tempos da semana passada
	 */
	public List<TempoInvestido> getTemposSemana(int semana) {
		List<TempoInvestido> tempos = new ArrayList<TempoInvestido>();
		
		Calendar cal = Calendar.getInstance();
		// data de X semanas atrás
		Calendar c = new GregorianCalendar(
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH) - (7*semana)
				);
		int sem = c.get(Calendar.WEEK_OF_YEAR);
		int ano = c.get(Calendar.YEAR);
		for (TempoInvestido t: tIs) {
			if (t.getAno() == ano && t.getSemanaDoAno() == sem)
				tempos.add(t);
		}
		return tempos;
	}
	
	/**
	 * Retorna os Tempos Investidos de uma certa semana começando do domingo que
	 * tenham como atividade a atividade passada como parâmetro.
	 * 
	 * semana=0 => semana atual semana=1 => semana passada
	 */
	public List<TempoInvestido> getTISAtividadeSemana(String atividade, int semana) {
		List<TempoInvestido> tempos = new ArrayList<TempoInvestido>();
		List<TempoInvestido> temposDaSemana = getTemposSemana(semana);
		for (TempoInvestido t: temposDaSemana) {
			if (t.getAtividade() == atividade)
				tempos.add(t);
		}
		return tempos;
	}
}