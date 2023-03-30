package org.ud2.developers.SGPVADU.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ud2.developers.SGPVADU.entity.Producto;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {
	public List<Producto> findByEstatus(Integer estatus);
	
	@Query(value = "select count(*) from Productos", nativeQuery = true)
	public Integer cantidadProductos();
}
