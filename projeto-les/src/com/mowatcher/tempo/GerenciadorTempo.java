package com.mowatcher.tempo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Classe controladora do tempo investido em uma atividade
 */
public class GerenciadorTempo {
	// private List<String> atividades;
	private Calendar calendario;
	private List<TempoInvestido> tIs;

	public GerenciadorTempo() {
		tIs = TempoInvestido.getAll();
	}

	public List<String> getAtividades() {
		List<String> atividades = new ArrayList<String>();
		for (TempoInvestido t : tIs) {
			atividades.add(t.getAtividade());
		}
		return atividades;
	}

	public Calendar getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}

	public GerenciadorTempo(Calendar calendario) {
		super();
		this.calendario = calendario;
	}

	public void adicionaTI(TempoInvestido tI) {
		tIs.add(tI);
		tI.save(); // salva no BD o tempo investido
	}

	public String[] getAtividadesMaisRecentes() {
		List<String> atividades = new ArrayList<String>();
		calendario = new GregorianCalendar();
		for (TempoInvestido t : tIs) {
			if ((t.getSemanaDoAno() == calendario.get(Calendar.WEEK_OF_YEAR))
					&& (t.getAno() == calendario.get(Calendar.YEAR)))
				atividades.add(t.getAtividade());
		}
		String[] array = {};
		return atividades.toArray(array);
	}

	public List<VisualizacaoRelatorio> getRelatorioSemanal(int semana) {
		// TODO
		return null;
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
			if (t.getTipo().equals(EnumPrioridade.BAIXISSIMA)) {
				percentuais[0] += t.getTempo();
			} else if (t.getTipo().equals(EnumPrioridade.BAIXA)) {
				percentuais[1] += t.getTempo();
			} else if (t.getTipo().equals(EnumPrioridade.MEDIA)) {
				percentuais[2] += t.getTempo();
			} else if (t.getTipo().equals(EnumPrioridade.ALTA)) {
				percentuais[3] += t.getTempo();
			} else if (t.getTipo().equals(EnumPrioridade.ALTISSIMA)) {
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
	 * Retorna os Tempos Investidos de uma certa semana come�ando do domingo Ex:
	 * semana=0 => tempos da semana atual semana=1 => tempos da semana passada
	 */
	public List<TempoInvestido> getTemposSemana(int semana) {
		return TempoInvestido.getTemposDaSemana(semana);
	}
}