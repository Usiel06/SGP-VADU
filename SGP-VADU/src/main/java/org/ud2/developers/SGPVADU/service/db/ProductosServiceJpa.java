package org.ud2.developers.SGPVADU.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.repository.ProductosRepository;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Service
public class ProductosServiceJpa implements IntServiceProductos {

	@Autowired
	private ProductosRepository repoProductos;

	@Override
	public List<Producto> obtenerEnVenta() {
		return repoProductos.findByEstatus(1);
	}

	@Override
	public List<Producto> obtenerProductos() {
		return repoProductos.findAll();
	}

	@Override
	public void agregarProducto(Producto producto) {
		repoProductos.save(producto);
	}

	@Override
	public Producto buscarPorId(Integer idProducto) {
		Optional<Producto> optional = repoProductos.findById(idProducto);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminarPorId(Integer idProducto) {
		repoProductos.deleteById(idProducto);
	}

	@Override
	public Integer contarProductos() {
		return repoProductos.cantidadProductos();
	}

	@Override
	public Page<Producto> buscarTodas(Pageable page) {
		return repoProductos.findAll(page);
	}
}
