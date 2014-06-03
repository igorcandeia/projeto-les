package com.mowatcher.tempo;

import java.util.ArrayList;
import java.util.Calendar;
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
	public void adicionaTI(TempoInvestido tI){
		tIs.add(tI);
		tI.save(); // salva no BD o tempo investido
	}
	public String[] getAtividadesMaisRecentes(){
		//TODO
		return null;
	}
	public VisualizacaoRelatorio getRelatorioSemanal(int semana){
		//TODO
		return null;
	}
	public float[] getPercentualUso() {
		//TODO
		return null;
	}
	public float[] getPercentualPrioridade() {
		//TODO
		return null;
	}
	
	/**
	 * Retorna os Tempos Investidos de uma certa semana come�ando do domingo 
	 * Ex:
	 * semana=0 => tempos da semana atual
	 * semana=1 => tempos da semana passada
	 */
	public List<TempoInvestido> getTemposSemana(int semana) {
		return TempoInvestido.getTemposDaSemana(semana);
	}
}