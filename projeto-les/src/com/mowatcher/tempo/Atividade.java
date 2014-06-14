package com.mowatcher.tempo;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Atividades")
public class Atividade extends Model {

	@Column
	private String nome;

	@Column
	private EnumTipo tipo;
	
	@Column
	private EnumPrioridade prioridade;

	public Atividade() {
		super();
	}
	
	public Atividade(String nome, EnumTipo tipo, EnumPrioridade prioridade) {
		this.nome = nome;
		this.tipo = tipo;
		this.prioridade = prioridade;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
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
	
	public List<Tag> tags() {
		return getMany(Tag.class, "Atividade");
	}
}