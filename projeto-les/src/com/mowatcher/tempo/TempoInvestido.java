package com.mowatcher.tempo;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="tempo_investido")
public class TempoInvestido extends Model{
	@Column
	private String atividade;
	@Column
	private float tempo;
	@Column
	private EnumTipo tipo;
	@Column
	private EnumPrioridade prioridade;
	@Column
	private int semana;
	@Column
	private int ano;
	
	private String[] tags;
	
	public TempoInvestido() {
	}
	public TempoInvestido(String atividade, float tempo, EnumTipo tipo,
			EnumPrioridade prioridade, int semana, int ano) {
		this.atividade = atividade;
		this.tempo = tempo;
		this.tipo = tipo;
		this.prioridade = prioridade;
		this.semana = semana;
		this.ano = ano;
	}
	
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
	
	/**
	 * Retorna a lista de todos os Tempos Investidos existentes no BD
	 */
	public static List<TempoInvestido> getAll(){
		return new Select().from(TempoInvestido.class).execute();
	}
	
	/**
	 * Recupera uma lista de todas as atividades cadastradas no BD.
	 */
	public static List<String> getAllAtividades() {
		List<TempoInvestido> tempos = new Select().from(TempoInvestido.class)
				.execute();

		List<String> atividades = new ArrayList<String>();
		for (TempoInvestido t : tempos) {
			atividades.add(t.getAtividade());
		}
		return atividades;
	}
}
