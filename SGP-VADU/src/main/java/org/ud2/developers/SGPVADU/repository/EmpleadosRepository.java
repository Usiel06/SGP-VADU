package org.ud2.developers.SGPVADU.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ud2.developers.SGPVADU.entity.Empleado;

public interface EmpleadosRepository extends JpaRepository<Empleado, Integer> {
	public Empleado findByNombre(String nombre);
}