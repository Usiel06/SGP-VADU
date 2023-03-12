package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.ud2.developers.SGPVADU.entity.Carrito;

public interface IntServiceCarrito {
	public List<Carrito> obtenerCarrito();
	public void agregarCarrito(Carrito carrito);
	public void eliminarPorId(Integer idCarrito);
	public Integer contarCarrito();
}
