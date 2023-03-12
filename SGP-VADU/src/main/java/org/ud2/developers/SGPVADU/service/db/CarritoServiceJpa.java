package org.ud2.developers.SGPVADU.service.db;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ud2.developers.SGPVADU.entity.Carrito;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.repository.CarritoRepository;
import org.ud2.developers.SGPVADU.service.IntServiceCarrito;

@Service
public class CarritoServiceJpa implements IntServiceCarrito {

	private List<Producto> productos = new LinkedList<>();
	
	@Autowired
	private CarritoRepository repoCarrito;

	@Override
	public List<Carrito> obtenerCarrito() {
		return repoCarrito.findAll();
	}

	@Override
	public void agregarCarrito(Carrito carrito) {
		repoCarrito.save(carrito);
	}

	@Override
	public void eliminarPorId(Integer idCarrito) {
		repoCarrito.deleteById(idCarrito);
	}

	@Override
	public Integer contarCarrito() {
		return (int) repoCarrito.count();
	}

}
