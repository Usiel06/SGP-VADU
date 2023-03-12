package org.ud2.developers.SGPVADU.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.repository.CarritoRepository;
import org.ud2.developers.SGPVADU.service.IntServiceCarrito;

@Service
public class CarritoServiceJpa implements IntServiceCarrito {

	@Autowired
	private CarritoRepository repoCarrito;

	@Override
	public List<Producto> obtenerCarrito() {
		return repoCarrito.findAll();
	}

	@Override
	public void agregarCarrito(Producto producto) {
		repoCarrito.save(producto);
	}

	@Override
	public void eliminarPorId(Integer idProducto) {
		repoCarrito.deleteById(idProducto);
	}

	@Override
	public Integer contarCarrito() {
		return (int) repoCarrito.count();
	}

}
