package com.sv.tumi.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "personalCapacitacion")
@NamedQueries({
    @NamedQuery(name = "PersonalCapacitacion.findAll", query = "SELECT p FROM PersonalCapacitacion p")})
public class PersonalCapacitacion implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
	@Column(name = "fechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuarioRegistro")
    private String usuarioRegistro;
    @Column(name = "fechaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuarioModificacion")
    private String usuarioModificacion;
    @JoinColumn(name = "codigoPersonal", referencedColumnName = "codigo")
    @ManyToOne
    private Personal codigoPersonal;
    @JoinColumn(name = "codigoSolicitudCapacitacion", referencedColumnName = "codigo")
    @ManyToOne
    private Solicitudcapacitacion codigoSolicitudCapacitacion;
    @JoinColumn(name = "codigoEstado", referencedColumnName = "codigo")
    @ManyToOne
    private Estado codigoEstado;
    
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}
	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	public Personal getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(Personal codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public Solicitudcapacitacion getCodigoSolicitudCapacitacion() {
		return codigoSolicitudCapacitacion;
	}
	public void setCodigoSolicitudCapacitacion(
			Solicitudcapacitacion codigoSolicitudCapacitacion) {
		this.codigoSolicitudCapacitacion = codigoSolicitudCapacitacion;
	}
	
	public Estado getCodigoEstado() {
		return codigoEstado;
	}
	
	public void setCodigoEstado(Estado codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

}
