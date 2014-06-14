package com.mowatcher.tempo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="tempo_investido")
public class TempoInvestido extends Model{
	
	@Column(name = "Atividade")
	private Atividade atividade;
	
	@Column
	private float tempo;
	
	@Column
	private int ano;
	
	@Column
	private int semanaDoAno;
	
	@Column
	private Date data;
	
	public TempoInvestido() {
		super();
	}
	
	public TempoInvestido(Atividade atividade, float tempo, GregorianCalendar data) {
		this.atividade = atividade;
		this.tempo = tempo;
		this.ano = data.get(Calendar.YEAR);
		this.semanaDoAno = data.get(Calendar.WEEK_OF_YEAR);
		this.data = data.getTime();
	}
	
	public float getTempo() {
		return tempo;
	}
	public void setTempo(float tempo) {
		this.tempo = tempo;
	}
	public int getAno() {
		return this.ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getSemanaDoAno() {
		return this.semanaDoAno;
	}
	public void setSemanaDoAno(int semanaDoAno) {
		this.semanaDoAno = semanaDoAno;
	}
	public Date getData() {
		return this.data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public Atividade getAtividade() {
		return this.atividade;
	}

	@Override
	public String toString() {
		return "TempoInvestido [atividade=" + atividade.getNome() + ", tempo=" + tempo + ", semana=" + semanaDoAno
				+ "]";
	}

	// Consultas ao BD Local Na Tabela TempoInvestido
	// ============================================================
	
	/**
	 * Retorna a lista de todos os Tempos Investidos existentes no BD 
	 * ordenados por data
	 */
	public static List<TempoInvestido> getAll(){
		return new Select().from(TempoInvestido.class).orderBy("data ASC").execute();
	}
}
