package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ud2.developers.SGPVADU.entity.Producto;

public interface IntServiceProductos {
	public List<Producto> obtenerEnVenta();
	public List<Producto> obtenerProductos();
	public Producto buscarPorId(Integer idProducto);
	public void guardarProducto(Producto producto);
	public void eliminarPorId(Integer idProducto);
	public Integer contarProductos();
	public Page<Producto> buscarTodas(Pageable page);
}
