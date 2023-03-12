package org.ud2.developers.SGPVADU.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ud2.developers.SGPVADU.entity.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
}