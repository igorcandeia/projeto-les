package com.mowatcher.tempo;

import java.util.ArrayList;
import java.util.Calendar;

public class GerenciadorTempo {
	private ArrayList<String> atividades;
	private Calendar calendario;
	private ArrayList<TempoInvestido> tIs;
	
	public ArrayList<String> getAtividades() {
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
}