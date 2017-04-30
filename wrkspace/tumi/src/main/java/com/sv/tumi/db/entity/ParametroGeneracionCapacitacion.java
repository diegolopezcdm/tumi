/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sv.tumi.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hector Santos
 */
@Entity
@Table(name = "parametroGeneracionCapacitacion")
@NamedQueries({
    @NamedQuery(name = "ParametroGeneracionCapacitacion.findAll", query = "SELECT c FROM ParametroGeneracionCapacitacion c")})
public class ParametroGeneracionCapacitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "codigoNivelEsperado")
    private Integer codigoNivelEsperado;
    @Column(name = "codigoNivelReal")
    private Integer codigoNivelReal;
    @Column(name = "codigoNivelSeleccionado")
    private Integer codigoNivelSeleccionado;

    public ParametroGeneracionCapacitacion() {
    }

    public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoNivelEsperado() {
		return codigoNivelEsperado;
	}

	public void setCodigoNivelEsperado(Integer codigoNivelEsperado) {
		this.codigoNivelEsperado = codigoNivelEsperado;
	}

	public Integer getCodigoNivelReal() {
		return codigoNivelReal;
	}

	public void setCodigoNivelReal(Integer codigoNivelReal) {
		this.codigoNivelReal = codigoNivelReal;
	}

	public Integer getCodigoNivelSeleccionado() {
		return codigoNivelSeleccionado;
	}

	public void setCodigoNivelSeleccionado(Integer codigoNivelSeleccionado) {
		this.codigoNivelSeleccionado = codigoNivelSeleccionado;
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
        if (!(object instanceof ParametroGeneracionCapacitacion)) {
            return false;
        }
        ParametroGeneracionCapacitacion other = (ParametroGeneracionCapacitacion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.tumi.db.entity.Curso[ codigo=" + codigo + " ]";
    }
    
}
