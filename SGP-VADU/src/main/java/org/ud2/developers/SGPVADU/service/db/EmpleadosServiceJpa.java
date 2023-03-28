package org.ud2.developers.SGPVADU.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ud2.developers.SGPVADU.entity.Empleado;
import org.ud2.developers.SGPVADU.repository.EmpleadosRepository;
import org.ud2.developers.SGPVADU.service.IntServiceEmpleados;

@Service
public class EmpleadosServiceJpa implements IntServiceEmpleados {
	
	@Autowired
	private EmpleadosRepository repoEmpleados;
	
	@Override
	public List<Empleado> obtenerEmpleados() {
		return repoEmpleados.findAll();
	}

	@Override
	public void agregar(Empleado empleado) {
		repoEmpleados.save(empleado);
	}

	@Override
	public Empleado buscarPorId(Integer idEmpleado) {
		Optional<Empleado> optional = repoEmpleados.findById(idEmpleado);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public Empleado findByNombre(String nombre) {
		return repoEmpleados.findByNombre(nombre);
	}

	@Override
	public void eliminar(Integer idEmpleado) {
		repoEmpleados.deleteById(idEmpleado);
	}

	@Override
	public int numeroEmpleados() {
		return (int) repoEmpleados.count();
	}

	@Override
	public Page<Empleado> buscarTodas(Pageable page) {
		return repoEmpleados.findAll(page);
	}

}
