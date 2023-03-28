package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.ud2.developers.SGPVADU.entity.DetalleOrden;

public interface IntServiceDetallesOrdenes {
	public List<DetalleOrden> obtenerDetalles();
    public DetalleOrden buscarPorId(Integer idDetalleOrden);
	public void guardarDetalle(DetalleOrden detalleOrden);
	public void eliminarPorId(Integer idDetalleOrden);
	public int contarDetalles();
}
