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
@Table(name = "cursoNivel")
@NamedQueries({
    @NamedQuery(name = "Cursonivel.findAll", query = "SELECT c FROM Cursonivel c")})
public class Cursonivel implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCursoNivel")
    private List<Cursocargo> cursocargoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCursoNivel")
    private List<Cursoevaluacion> cursoevaluacionList;
    @OneToMany(mappedBy = "codigoCursoNivel")
    private List<Cursocapacitacion> cursocapacitacionList;
    @JoinColumn(name = "codigoCurso", referencedColumnName = "codigo")
    @ManyToOne
    private Curso codigoCurso;
    @JoinColumn(name = "codigoNivel", referencedColumnName = "codigo")
    @ManyToOne
    private Nivel codigoNivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCursoNivel")
    private List<Retroalimentacion> retroalimentacionList;
    @OneToMany(mappedBy = "codigoCursoNivel")
    private List<Tema> temaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoRequisito")
    private List<Cursoprerequisito> cursoprerequisitoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCursoNivel")
    private List<Cursoprerequisito> cursoprerequisitoList1;

    public Cursonivel() {
    }

    public Cursonivel(Integer codigo) {
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

    public List<Cursocargo> getCursocargoList() {
        return cursocargoList;
    }

    public void setCursocargoList(List<Cursocargo> cursocargoList) {
        this.cursocargoList = cursocargoList;
    }

    public List<Cursoevaluacion> getCursoevaluacionList() {
        return cursoevaluacionList;
    }

    public void setCursoevaluacionList(List<Cursoevaluacion> cursoevaluacionList) {
        this.cursoevaluacionList = cursoevaluacionList;
    }

    public List<Cursocapacitacion> getCursocapacitacionList() {
        return cursocapacitacionList;
    }

    public void setCursocapacitacionList(List<Cursocapacitacion> cursocapacitacionList) {
        this.cursocapacitacionList = cursocapacitacionList;
    }

    public Curso getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(Curso codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public Nivel getCodigoNivel() {
        return codigoNivel;
    }

    public void setCodigoNivel(Nivel codigoNivel) {
        this.codigoNivel = codigoNivel;
    }

    public List<Retroalimentacion> getRetroalimentacionList() {
        return retroalimentacionList;
    }

    public void setRetroalimentacionList(List<Retroalimentacion> retroalimentacionList) {
        this.retroalimentacionList = retroalimentacionList;
    }

    public List<Tema> getTemaList() {
        return temaList;
    }

    public void setTemaList(List<Tema> temaList) {
        this.temaList = temaList;
    }

    public List<Cursoprerequisito> getCursoprerequisitoList() {
        return cursoprerequisitoList;
    }

    public void setCursoprerequisitoList(List<Cursoprerequisito> cursoprerequisitoList) {
        this.cursoprerequisitoList = cursoprerequisitoList;
    }

    public List<Cursoprerequisito> getCursoprerequisitoList1() {
        return cursoprerequisitoList1;
    }

    public void setCursoprerequisitoList1(List<Cursoprerequisito> cursoprerequisitoList1) {
        this.cursoprerequisitoList1 = cursoprerequisitoList1;
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
        if (!(object instanceof Cursonivel)) {
            return false;
        }
        Cursonivel other = (Cursonivel) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.tumi.db.entity.Cursonivel[ codigo=" + codigo + " ]";
    }
    
}
