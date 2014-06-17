package com.mowatcher.tempo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mowatcher.util.AppConfig;
import com.mowatcher.util.RequestManager;

/**
 * Classe controladora do tempo investido em uma atividade
 */
public class GerenciadorTempo {

	private List<TempoInvestido> tIs;

	public GerenciadorTempo() {
		tIs = new ArrayList<TempoInvestido>();
	}

	public void loadAndSyncronizedTIS(Long userId) {
		if (AppConfig.BD_LOCAL) {
			tIs = TempoInvestido.getAll();
		}
		if (AppConfig.BD_REMOTE) {
			tIs = new RequestManager().loadTIS(userId);
		}
	}

	public List<TempoInvestido> getTIs() {
		return tIs;
	}

	public void adicionaTI(TempoInvestido tI, Long userID) {
		tIs.add(tI);
		if (AppConfig.BD_LOCAL) {
			tI.getAtividade().save();
			tI.save(); // salva no BD o T.I.
		}
		if (AppConfig.BD_REMOTE) {
			new RequestManager().saveTI(tI, userID);
		}
	}

	public Map<String, Float> getAtividadesMaisRecentes() {
		Map<String,Float> mapaAtividades = new HashMap<String, Float>();
		Calendar calendario = new GregorianCalendar();
		for (TempoInvestido t : tIs) {

			if ((t.getSemanaDoAno() == calendario.get(Calendar.WEEK_OF_YEAR))
					&& (t.getAno() == calendario.get(Calendar.YEAR)))
				if (mapaAtividades.get(t.getAtividade()) == null) {
					mapaAtividades.put(t.getAtividade().getNome(), t.getTempo());
				} else {
					mapaAtividades.put(t.getAtividade().getNome(),
							mapaAtividades.get(t.getAtividade()) + t.getTempo());
				}
		}
		return mapaAtividades;
	}

	public float[] getPercentualUso(int semana) {
		List<TempoInvestido> tempos = getTemposSemana(semana);
		float[] percentuais = { 0, 0 };
		float total = 0;
		for (TempoInvestido t : tempos) {
			switch (t.getAtividade().getTipo()) {
			case LAZER:
				percentuais[0] += t.getTempo();
				break;
			case TRABALHO:
				percentuais[1] += t.getTempo();
				break;
			default:
				break;
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
	public float[] getPercentualUso(List<Atividade> atividades, int semana) {
		List<TempoInvestido> tempos = getTemposSemana(semana);
		float[] percentuais = { 0, 0 };
		float total = 0;
		for (TempoInvestido t : tempos) {
			if (atividades.contains(t.getAtividade())){
				switch (t.getAtividade().getTipo()) {
				case LAZER:
					percentuais[0] += t.getTempo();
					break;
				case TRABALHO:
					percentuais[1] += t.getTempo();
					break;
				default:
					break;
				}
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
			switch (t.getAtividade().getPrioridade()) {
			case BAIXISSIMA:
				percentuais[0] += t.getTempo();
				break;
			case BAIXA:
				percentuais[1] += t.getTempo();
				break;
			case MEDIA:
				percentuais[2] += t.getTempo();
				break;
			case ALTA:
				percentuais[3] += t.getTempo();
				break;
			case ALTISSIMA:
				percentuais[4] += t.getTempo();
				break;
			default:
				break;
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
	
	public float[] getPercentualPrioridade(List<Atividade> atividades, int semana) {
		List<TempoInvestido> tempos = getTemposSemana(semana);
		float[] percentuais = { 0, 0, 0, 0, 0 };
		float total = 0;
		for (TempoInvestido t : tempos) {
			if (atividades.contains(t.getAtividade())){
				switch (t.getAtividade().getPrioridade()) {
				case BAIXISSIMA:
					percentuais[0] += t.getTempo();
					break;
				case BAIXA:
					percentuais[1] += t.getTempo();
					break;
				case MEDIA:
					percentuais[2] += t.getTempo();
					break;
				case ALTA:
					percentuais[3] += t.getTempo();
					break;
				case ALTISSIMA:
					percentuais[4] += t.getTempo();
					break;
				default:
					break;
				}
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
			if (t.getAno() == ano && t.getSemanaDoAno() == sem) {
				tempos.add(t);
			}
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
			if (t.getAtividade().getNome().equals(atividade)) {
				tempos.add(t);
			}
		}
		return tempos;
	}

	public float getHorasSemana(int semana) {
		float sum = 0;
		List<TempoInvestido> tempos = getTemposSemana(semana);
		for (TempoInvestido t: tempos) {
			sum += t.getTempo();
		}
		return sum;
	}
}