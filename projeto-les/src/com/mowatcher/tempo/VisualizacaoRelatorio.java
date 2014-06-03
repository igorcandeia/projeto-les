package com.mowatcher.tempo;

public class VisualizacaoRelatorio implements Comparable<VisualizacaoRelatorio> {
	private String atividade;
	private float tempoInvestido;


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

	public VisualizacaoRelatorio(String atividade, float tempoInvestido,
			EnumTipo tipo, EnumPrioridade prioridade) {
		super();
		this.atividade = atividade;
		this.tempoInvestido = tempoInvestido;
	}

	@Override
	public int compareTo(VisualizacaoRelatorio outra) {
		if (this.tempoInvestido > outra.getTempoInvestido()) {
			return -1;
		} else if (this.tempoInvestido < outra.getTempoInvestido()) {
			return 1;
		}
		return 0;
	}
}
