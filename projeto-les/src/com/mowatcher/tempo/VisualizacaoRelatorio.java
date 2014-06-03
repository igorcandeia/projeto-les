package com.mowatcher.tempo;

public class VisualizacaoRelatorio implements Comparable<VisualizacaoRelatorio>{
	private String atividade;
	private float tempoInvestido;
	private EnumTipo tipo;
	private EnumPrioridade prioridade;

	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public float getTempoInvestido() {
		return tempoInvestido;
	}
	public void setTempoInvestido(float tempoInvestido) {
		this.tempoInvestido = tempoInvestido;
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
	public VisualizacaoRelatorio(String atividade, float tempoInvestido,
			EnumTipo tipo, EnumPrioridade prioridade) {
		super();
		this.atividade = atividade;
		this.tempoInvestido = tempoInvestido;
		this.tipo = tipo;
		this.prioridade = prioridade;
	}
	@Override
	public int compareTo(VisualizacaoRelatorio outra) {
		if (this.tempoInvestido > outra.getTempoInvestido()){
			return -1;
		}
		else if (this.tempoInvestido < outra.getTempoInvestido()){
			return 1;
		}
		return 0;
	}
}
