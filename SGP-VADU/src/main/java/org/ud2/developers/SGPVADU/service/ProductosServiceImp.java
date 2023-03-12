package org.ud2.developers.SGPVADU.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ud2.developers.SGPVADU.entity.Producto;

@Service
public class ProductosServiceImp implements IntServiceProductos {

	private List<Producto> productos;
	
	public ProductosServiceImp() {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		productos = new LinkedList<>();
		
		Producto p1 = new Producto();
		p1.setId(1);
		p1.setNombre("Bistec de res");
		p1.setDescripcion("Producto cárnico fresco de res.");
		p1.setEstatus(1);
		p1.setFechaIngreso(LocalDate.parse("25/02/2023", formato));
		p1.setPrecioKg(150.0);
		p1.setCantidadIngreso(5.0);
		p1.setTempAlmacen(2);
		p1.setVidaUtil(14);
		p1.setImagen("img1.png");
		
		Producto p2 = new Producto();
		p2.setId(2);
		p2.setNombre("Aguja de res");
		p2.setDescripcion("Producto cárnico crudo de res.");
		p2.setEstatus(1);
		p2.setFechaIngreso(LocalDate.parse("25/02/2023", formato));
		p2.setPrecioKg(200.0);
		p2.setCantidadIngreso(3.0);
		p2.setTempAlmacen(5);
		p2.setVidaUtil(7);
		p2.setImagen("img2.png");
		
		Producto p3 = new Producto();
		p3.setId(3);
		p3.setNombre("Filetes de lomo de cerdo");
		p3.setDescripcion("Producto cárnico crudo de cerdo fileteado.");
		p3.setEstatus(1);
		p3.setFechaIngreso(LocalDate.parse("25/02/2023", formato));
		p3.setPrecioKg(450.0);
		p3.setCantidadIngreso(15.0);
		p3.setTempAlmacen(0);
		p3.setVidaUtil(7);
		p3.setImagen("img3.png");
		
		Producto p4 = new Producto();
		p4.setId(4);
		p4.setNombre("Longaniza de cerdo");
		p4.setDescripcion("Producto cárnico fermentado de cerdo.");
		p4.setEstatus(1);
		p4.setFechaIngreso(LocalDate.parse("25/02/2023", formato));
		p4.setPrecioKg(50.0);
		p4.setCantidadIngreso(10.0);
		p4.setTempAlmacen(10);
		p4.setVidaUtil(14);
		p4.setImagen("img4.png");
		
		productos.add(p1);
		productos.add(p2);
		productos.add(p3);
		productos.add(p4);
	}
	
	@Override
	public List<Producto> obtenerEnVenta() {
		List<Producto> productosEnVenta = new LinkedList<>();
		for(Producto producto : productos) {
			if(producto.getEstatus() == 1) {
				productosEnVenta.add(producto);
			}
		}
		return productosEnVenta;
	}

	@Override
	public List<Producto> obtenerProductos() {
		return productos;
	}

	@Override
	public Producto buscarPorId(Integer idProducto) {
		for(Producto producto : productos) {
			if(producto.getId().compareTo(idProducto) == 0) {
				return producto;
			}
		}
		return null;
	}

	@Override
	public void guardar(Producto producto) {
		productos.add(producto);
	}

	@Override
	public void eliminar(Integer idProducto) {
		productos.remove(buscarPorId(idProducto));
	}

	@Override
	public Integer contarProductos() {
		return productos.size();
	}

	@Override
	public Page<Producto> buscarTodas(Pageable page) {
		return null;
	}
}
