package org.ud2.developers.SGPVADU.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.ud2.developers.SGPVADU.entity.Categoria;
import org.ud2.developers.SGPVADU.service.IntServiceCategorias;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
public class HomeController {
    
	@Autowired
	private IntServiceProductos serviceProductos;
	
	@Autowired
	private IntServiceCategorias serviceCategorias;
	
	/*@GetMapping("/agregar")
	public String agregarCarrito(@RequestParam("id") Integer idProducto, Carrito carrito) {
		Producto producto = serviceProductos.buscarPorId(idProducto);
		carrito.setProducto(producto);
		serviceCarrito.agregarCarrito(carrito);
		return "redirect:/";
	}*/
	
	@GetMapping("/carrito")
	public String carrito() {
		return "carrito";
	}
	
	@GetMapping("/")
	public String mostrarIndex(Model model) {
		List<Categoria> categorias = new LinkedList<>();
		model.addAttribute("productos", serviceProductos.obtenerEnVenta());
		for(Categoria categoria : serviceCategorias.obtenerCategorias()) {
			if(categoria.getId().compareTo(1) == 1) {
				categorias.add(categoria);
			}
		}
		model.addAttribute("categoria", serviceCategorias.buscarPorId(1));
		model.addAttribute("categorias", categorias);
		return "home";
	}
}
