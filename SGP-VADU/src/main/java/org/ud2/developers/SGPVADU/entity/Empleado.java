package org.ud2.developers.SGPVADU.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Empleados")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String telefono;

	@OneToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", telefono=" + telefono + ", usuario=" + usuario + "]";
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
