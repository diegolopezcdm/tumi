package com.sv.tumi.view;

import java.util.ArrayList;
import java.util.List;

import com.sv.tumi.db.entity.Tema;

public class CursoView {
	
	private String nombre;
	private String alcance;
	private List<TemaView> tema = new ArrayList<TemaView>();
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAlcance() {
		return alcance;
	}
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
	public List<TemaView> getTema() {
		return tema;
	}
	public void setTema(List<TemaView> tema) {
		this.tema = tema;
	}	
	
	public int getRows(){
	int count=1;
	for (TemaView tema : getTema()) {
		count++;
		for (SubtemaView subtema : tema.getSubtemas()) {
			count++;
		}
		
	}
	return count;
}

}
