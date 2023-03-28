package org.ud2.developers.SGPVADU.service;

import java.util.List;
import java.util.Optional;

import org.ud2.developers.SGPVADU.entity.Orden;
import org.ud2.developers.SGPVADU.entity.Usuario;

public interface IntServiceOrdenes {
	public List<Orden> obtenerOrdenes();
	public void guardarOrden(Orden orden);
	public String generarNumeroOrden();
	public List<Orden> buscarPorUsuario(Usuario usuario);
	public Optional<Orden> buscarPorId(Integer idOrden);
}
