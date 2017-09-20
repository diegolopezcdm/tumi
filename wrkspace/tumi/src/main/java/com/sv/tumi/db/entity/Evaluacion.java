/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sv.tumi.db.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hector Santos
 */
@Entity
@Table(name = "evaluacion")
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e")})
public class Evaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "numeroPregunta")
    private Integer numeroPregunta;
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
    @JoinColumn(name = "codigoTipo", referencedColumnName = "codigo")
    @ManyToOne
    private Tipoevaluacion codigoTipo;
    @JoinColumn(name = "codigoEstado", referencedColumnName = "codigo")
    @ManyToOne
    private Estado codigoEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoEvaluacion")
    private List<Cursoevaluacion> cursoevaluacionList;
    @Column(name = "minutosDuracion")
    private Integer minutosDuracion;
    @JoinColumn(name = "codigoPersonalCapacitacion", referencedColumnName = "codigo")
    @ManyToOne
    private PersonalCapacitacion codigoPersonalCapacitacion;

    public Evaluacion() {
    }

    public Evaluacion(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getNumeroPregunta() {
        return numeroPregunta;
    }

    public void setNumeroPregunta(Integer numeroPregunta) {
        this.numeroPregunta = numeroPregunta;
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

    public Tipoevaluacion getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(Tipoevaluacion codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public Estado getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Estado codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public List<Cursoevaluacion> getCursoevaluacionList() {
        return cursoevaluacionList;
    }

    public void setCursoevaluacionList(List<Cursoevaluacion> cursoevaluacionList) {
        this.cursoevaluacionList = cursoevaluacionList;
    }

    public Integer getMinutosDuracion() {
		return minutosDuracion;
	}

	public void setMinutosDuracion(Integer minutosDuracion) {
		this.minutosDuracion = minutosDuracion;
	}
	
	public String getMinutosDuracionFormarted() {
		return getDateFromMillis(minutosDuracion);
	}
	
	public static String getDateFromMillis(long millis) {
        return new SimpleDateFormat("mm:ss").format(new Date(millis));
    }

	public PersonalCapacitacion getCodigoPersonalCapacitacion() {
		return codigoPersonalCapacitacion;
	}

	public void setCodigoPersonalCapacitacion(
			PersonalCapacitacion codigoPersonalCapacitacion) {
		this.codigoPersonalCapacitacion = codigoPersonalCapacitacion;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.tumi.db.entity.Evaluacion[ codigo=" + codigo + " ]";
    }
    
}
