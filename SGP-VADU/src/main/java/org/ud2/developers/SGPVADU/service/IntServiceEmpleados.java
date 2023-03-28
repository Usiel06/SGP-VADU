package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ud2.developers.SGPVADU.entity.Empleado;

public interface IntServiceEmpleados {
	public List<Empleado> obtenerEmpleados();
	public void agregar(Empleado empleado);
	public Empleado buscarPorId(Integer idEmpleado);
	public Empleado findByNombre(String nombre);
	public void eliminar(Integer idEmpleado);
	public int numeroEmpleados();
	Page<Empleado> buscarTodas(Pageable page);
}
