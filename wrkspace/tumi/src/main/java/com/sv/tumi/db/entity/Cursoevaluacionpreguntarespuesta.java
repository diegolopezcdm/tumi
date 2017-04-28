/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Hector Santos
 */
@Entity
@Table(name = "cursoEvaluacionPreguntaRespuesta")
@NamedQueries({
    @NamedQuery(name = "Cursoevaluacionpreguntarespuesta.findAll", query = "SELECT c FROM Cursoevaluacionpreguntarespuesta c")})
public class Cursoevaluacionpreguntarespuesta implements Serializable {
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
    @JoinColumn(name = "codigoCursoEvaluacionPregunta", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Cursoevaluacionpregunta codigoCursoEvaluacionPregunta;
    @JoinColumn(name = "codigoRespuesta", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Respuesta codigoRespuesta;

    public Cursoevaluacionpreguntarespuesta() {
    }

    public Cursoevaluacionpreguntarespuesta(Integer codigo) {
        this.codigo = codigo;
    }

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

    public Cursoevaluacionpregunta getCodigoCursoEvaluacionPregunta() {
        return codigoCursoEvaluacionPregunta;
    }

    public void setCodigoCursoEvaluacionPregunta(Cursoevaluacionpregunta codigoCursoEvaluacionPregunta) {
        this.codigoCursoEvaluacionPregunta = codigoCursoEvaluacionPregunta;
    }

    public Respuesta getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Respuesta codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
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
        if (!(object instanceof Cursoevaluacionpreguntarespuesta)) {
            return false;
        }
        Cursoevaluacionpreguntarespuesta other = (Cursoevaluacionpreguntarespuesta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.tumi.db.entity.Cursoevaluacionpreguntarespuesta[ codigo=" + codigo + " ]";
    }
    
}
