package org.ud2.developers.SGPVADU.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ud2.developers.SGPVADU.entity.DetalleOrden;
import org.ud2.developers.SGPVADU.repository.DetallesOrdenesRepository;
import org.ud2.developers.SGPVADU.service.IntServiceDetallesOrdenes;

@Service
public class DetallesOrdenesServiceJpa implements IntServiceDetallesOrdenes {

	@Autowired
	private DetallesOrdenesRepository repoDetallesOrdenes;

	@Override
	public List<DetalleOrden> obtenerDetalles() {
		return repoDetallesOrdenes.findAll();
	}

	@Override
	public DetalleOrden buscarPorId(Integer idDetalleOrden) {
		Optional<DetalleOrden> optional = repoDetallesOrdenes.findById(idDetalleOrden);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardarDetalle(DetalleOrden detalleOrden) {
		repoDetallesOrdenes.save(detalleOrden);
	}

	@Override
	public void eliminarPorId(Integer idDetalleOrden) {
		repoDetallesOrdenes.deleteById(idDetalleOrden);
	}

	@Override
	public int contarDetalles() {
		return (int) repoDetallesOrdenes.count();
	}

}
