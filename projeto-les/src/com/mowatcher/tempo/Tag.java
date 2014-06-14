package com.mowatcher.tempo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tag")
public class Tag extends Model{
	
	@Column
	Atividade atividade;
	
	@Column
	String nome;
	
	public Tag() {
		super();
	}

}
