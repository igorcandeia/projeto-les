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
	@Column
	private String atividade;
	@Column
	private float tempo;
	@Column
	private EnumTipo tipo;
	@Column
	private EnumPrioridade prioridade;
	@Column
	private int ano;
	@Column
	private int semanaDoAno;
	@Column
	private Date data;
	
	private String[] tags;
	
	public TempoInvestido() {
	}
	
	public TempoInvestido(String atividade, float tempo, EnumTipo tipo,
			EnumPrioridade prioridade, GregorianCalendar data) {
		this.atividade = atividade;
		this.tempo = tempo;
		this.tipo = tipo;
		this.prioridade = prioridade;
		this.ano = data.get(Calendar.YEAR);
		this.semanaDoAno = data.get(Calendar.WEEK_OF_YEAR);
		this.data = data.getTime();
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
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
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

	@Override
	public String toString() {
		return "TempoInvestido [atividade=" + atividade + ", tempo=" + tempo + ", semana=" + semanaDoAno
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

	/**
	 * Retorna os Tempos investidos que tenham como atividade a string passada
	 * como parametro
	 */
	public static List<TempoInvestido> getAtividadeByNome( String nome ) {
		return new Select()
		.from(TempoInvestido.class)
		.where("atividade = ?", nome)
		.execute();
	}
	
	/**
	 * Retorna os tempos investidos em uma determinada semana
	 * 
	 * Ex: 
	 * semana=0 => tempos da semana atual
	 * semana=1 => tempos da semana passada
	 */
	public static List<TempoInvestido> getTemposDaSemana(int semana) {
		Calendar cal = Calendar.getInstance();
		// data de X semanas atrás
		Calendar c = new GregorianCalendar(
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH) - (7*semana)
				);
		int sem = c.get(Calendar.WEEK_OF_YEAR);
		int ano = c.get(Calendar.YEAR);
		
		return new Select()
		.from(TempoInvestido.class)
		.where("ano = ?", ano)
		.where("semanaDoAno = ?", sem)
		.execute();
	}
}
