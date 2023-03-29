package org.ud2.developers.SGPVADU.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ud2.developers.SGPVADU.entity.Producto;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {
	List<Producto> findByEstatus(Integer estatus);
}
