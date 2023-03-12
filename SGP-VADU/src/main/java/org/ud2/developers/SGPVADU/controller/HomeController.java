package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ud2.developers.SGPVADU.entity.Carrito;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.service.IntServiceCarrito;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
public class HomeController {
    
	@Autowired
	private IntServiceProductos serviceProductos;
	
	@Autowired
	private IntServiceCarrito serviceCarrito;
	
	@GetMapping("/agregar")
	public String agregarCarrito(@RequestParam("id") Integer idProducto, Carrito carrito) {
		Producto producto = serviceProductos.buscarPorId(idProducto);
		carrito.setProducto(producto);
		serviceCarrito.agregarCarrito(carrito);
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String mostrarIndex(Model model) {
		model.addAttribute("productos", serviceProductos.obtenerEnVenta());
		return "index";
	}
}
