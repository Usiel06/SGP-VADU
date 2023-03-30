package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.ud2.developers.SGPVADU.entity.DetalleOrden;

public interface IntServiceDetallesOrdenes {
	public List<DetalleOrden> obtenerDetalles();

	public void agregarDetalle(DetalleOrden detalleOrden);

	public DetalleOrden buscarPorId(Integer idDetalleOrden);
}
