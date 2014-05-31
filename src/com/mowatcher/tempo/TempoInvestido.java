package com.mowatcher.tempo;

public class TempoInvestido {
	private String atividade;
	private float tempo;
	private EnumTipo tipo;
	private EnumPrioridade prioridade;
	private int semana;
	private int ano;
	private String[] tags;
	
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public float getTempo() {
		return tempo;
	}
	public void setTempo(float tempo) {
		this.tempo = tempo;
	}
	public EnumTipo getTipo() {
		return tipo;
	}
	public void setTipo(EnumTipo tipo) {
		this.tipo = tipo;
	}
	public EnumPrioridade getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(EnumPrioridade prioridade) {
		this.prioridade = prioridade;
	}
	public int getSemana() {
		return semana;
	}
	public void setSemana(int semana) {
		this.semana = semana;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public TempoInvestido(String atividade, float tempo, EnumTipo tipo,
			EnumPrioridade prioridade, int semana, int ano, String[] tags) {
		super();
		this.atividade = atividade;
		this.tempo = tempo;
		this.tipo = tipo;
		this.prioridade = prioridade;
		this.semana = semana;
		this.ano = ano;
		this.tags = tags;
	}
}
