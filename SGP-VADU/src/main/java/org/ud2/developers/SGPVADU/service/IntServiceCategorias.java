package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ud2.developers.SGPVADU.entity.Categoria;

public interface IntServiceCategorias {
    public List<Categoria> obtenerCategorias();
    public Categoria buscarPorId(Integer idCategoria);
	public void guardar(Categoria categoria);
	public void eliminar(Integer idCategoria);
	public int contarCategorias();
	public Page<Categoria> buscarTodas(Pageable page);
}
