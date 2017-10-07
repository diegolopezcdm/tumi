package com.sv.tumi.view;

import java.util.ArrayList;
import java.util.List;

public class TemaView {
	
	private String nombre;
	private String alcance;
	private int orden;
	private List<SubtemaView> subtemas = new ArrayList<SubtemaView>();
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
	public int getOrden() {
		return orden;
	}
	public void setOrden(int order) {
		this.orden = order;
	}
	public List<SubtemaView> getSubtemas() {
		return subtemas;
	}
	public void setSubtemas(List<SubtemaView> subtemas) {
		this.subtemas = subtemas;
	}
	
	/*public int getRows(){
		int count=0;
			for (SubtemaView subtema : subtemas) {
				count++;
			}
			
		
		return count;
	}*/

}
