package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ud2.developers.SGPVADU.entity.Carrito;
import org.ud2.developers.SGPVADU.service.IntServiceCarrito;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	private IntServiceCarrito serviceCarrito;

	@Autowired
	private IntServiceProductos serviceProductos;

	@GetMapping("/eliminar")
	public String eliminarCarrito(@RequestParam("id") Integer idCarrito) {
		System.out.println(idCarrito + "fsbgdjgjkgdjgnj");
		serviceCarrito.eliminarPorId(idCarrito);
		return "redirect:/carrito/index";
	}

	@GetMapping("/index")
	public String mostrarCarrito(Model model) {
		model.addAttribute("carritos", serviceCarrito.obtenerCarrito());
		Double total = 0.0;
		for (Carrito carrito : serviceCarrito.obtenerCarrito()) {
			total += carrito.getProducto().getPrecioKg();
		}
		model.addAttribute("total", total);
		model.addAttribute("cantidad", serviceCarrito.contarCarrito());
		return "carrito";
	}
}