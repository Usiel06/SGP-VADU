package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.ud2.developers.SGPVADU.entity.Producto;

public interface IntServiceCarrito {
	public List<Producto> obtenerCarrito();
	public void agregarCarrito(Producto producto);
	public void eliminarPorId(Integer idProducto);
	public Integer contarCarrito();
}
