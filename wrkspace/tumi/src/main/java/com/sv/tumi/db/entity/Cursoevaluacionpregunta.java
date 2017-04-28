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
@Table(name = "cursoEvaluacionPregunta")
@NamedQueries({
    @NamedQuery(name = "Cursoevaluacionpregunta.findAll", query = "SELECT c FROM Cursoevaluacionpregunta c")})
public class Cursoevaluacionpregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PuntajeObtenido")
    private Double puntajeObtenido;
    @Column(name = "orden")
    private Integer orden;
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
    @JoinColumn(name = "codigoCursoEvaluacion", referencedColumnName = "codigo")
    @ManyToOne
    private Cursoevaluacion codigoCursoEvaluacion;
    @JoinColumn(name = "codigoPregunta", referencedColumnName = "codigo")
    @ManyToOne
    private Pregunta codigoPregunta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCursoEvaluacionPregunta")
    private List<Cursoevaluacionpreguntarespuesta> cursoevaluacionpreguntarespuestaList;
    @OneToMany(mappedBy = "codigoCursoEvaluacionPregunta")
    private List<Tiempodesarrollo> tiempodesarrolloList;
    @OneToMany(mappedBy = "codigoCursoEvaluacionPregunta")
    private List<Patrondesarrollo> patrondesarrolloList;

    public Cursoevaluacionpregunta() {
    }

    public Cursoevaluacionpregunta(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(Double puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public Cursoevaluacion getCodigoCursoEvaluacion() {
        return codigoCursoEvaluacion;
    }

    public void setCodigoCursoEvaluacion(Cursoevaluacion codigoCursoEvaluacion) {
        this.codigoCursoEvaluacion = codigoCursoEvaluacion;
    }

    public Pregunta getCodigoPregunta() {
        return codigoPregunta;
    }

    public void setCodigoPregunta(Pregunta codigoPregunta) {
        this.codigoPregunta = codigoPregunta;
    }

    public List<Cursoevaluacionpreguntarespuesta> getCursoevaluacionpreguntarespuestaList() {
        return cursoevaluacionpreguntarespuestaList;
    }

    public void setCursoevaluacionpreguntarespuestaList(List<Cursoevaluacionpreguntarespuesta> cursoevaluacionpreguntarespuestaList) {
        this.cursoevaluacionpreguntarespuestaList = cursoevaluacionpreguntarespuestaList;
    }

    public List<Tiempodesarrollo> getTiempodesarrolloList() {
        return tiempodesarrolloList;
    }

    public void setTiempodesarrolloList(List<Tiempodesarrollo> tiempodesarrolloList) {
        this.tiempodesarrolloList = tiempodesarrolloList;
    }

    public List<Patrondesarrollo> getPatrondesarrolloList() {
        return patrondesarrolloList;
    }

    public void setPatrondesarrolloList(List<Patrondesarrollo> patrondesarrolloList) {
        this.patrondesarrolloList = patrondesarrolloList;
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
        if (!(object instanceof Cursoevaluacionpregunta)) {
            return false;
        }
        Cursoevaluacionpregunta other = (Cursoevaluacionpregunta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.tumi.db.entity.Cursoevaluacionpregunta[ codigo=" + codigo + " ]";
    }
    
}
