package org.ud2.developers.SGPVADU.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ud2.developers.SGPVADU.entity.Contacto;

public interface ContactosRepository extends JpaRepository<Contacto, Integer> {
	@Query(value = "select count(*) from Contactos", nativeQuery = true)
	public Integer cantidadContactos();
}
