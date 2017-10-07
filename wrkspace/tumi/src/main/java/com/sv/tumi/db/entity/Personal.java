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
@Table(name = "personal")
@NamedQueries({
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p")})
public class Personal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Column(name = "dni")
    private Integer dni;
    @Column(name = "fechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "correo")
    private String correo;
    @Column(name = "capacitador")
    private Short capacitador;
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
    @JoinColumn(name = "codigoCargo", referencedColumnName = "codigo")
    @ManyToOne
    private Cargo codigoCargo;
    @OneToMany(mappedBy = "codigoCapacitador")
    private List<Especialidadcapacitador> especialidadcapacitadorList;
    @OneToMany(mappedBy = "codigoPersonal")
    private List<PersonalCapacitacion> personalCapacitacion;
    @JoinColumn(name = "codigoNivelDesempeño", referencedColumnName = "codigo")
    @ManyToOne
    private NivelDesempeño codigoNivelDesempeño;
    @JoinColumn(name = "codigoNivelPersonal", referencedColumnName = "codigo")
    @ManyToOne
    private NivelPersonal codigoNivelPersonal;
    
    public Personal() {
    }

    public Personal(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Short getCapacitador() {
        return capacitador;
    }

    public void setCapacitador(Short capacitador) {
        this.capacitador = capacitador;
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

    public Cargo getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(Cargo codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public List<Especialidadcapacitador> getEspecialidadcapacitadorList() {
        return especialidadcapacitadorList;
    }

    public void setEspecialidadcapacitadorList(List<Especialidadcapacitador> especialidadcapacitadorList) {
        this.especialidadcapacitadorList = especialidadcapacitadorList;
    }

	public List<PersonalCapacitacion> getPersonalCapacitacion() {
		return personalCapacitacion;
	}

	public void setPersonalCapacitacion(
			List<PersonalCapacitacion> personalCapacitacion) {
		this.personalCapacitacion = personalCapacitacion;
	}
	
	public NivelDesempeño getCodigoNivelDesempeño() {
		return codigoNivelDesempeño;
	}
	
	public void setCodigoNivelDesempeño(NivelDesempeño codigoNivelDesempeño) {
		this.codigoNivelDesempeño = codigoNivelDesempeño;
	}
	
	public void setCodigoNivelPersonal(NivelPersonal codigoNivelPersonal) {
		this.codigoNivelPersonal = codigoNivelPersonal;
	}

	public NivelPersonal getCodigoNivelPersonal() {
		return codigoNivelPersonal;
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
        if (!(object instanceof Personal)) {
            return false;
        }
        Personal other = (Personal) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sv.tumi.db.entity.Personal[ codigo=" + codigo + " ]";
    }
    
}
