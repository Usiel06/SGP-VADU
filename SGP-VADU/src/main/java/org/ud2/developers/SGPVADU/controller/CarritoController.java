package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ud2.developers.SGPVADU.service.IntServiceCarrito;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
	
	@Autowired
	private IntServiceCarrito serviceCarrito;
	
	@Autowired
	private IntServiceProductos serviceProductos;
	
	@GetMapping("/index")
    public String mostrarCarrito(Model model) {
		model.addAttribute("productos", serviceCarrito.obtenerCarrito());
		model.addAttribute("total", serviceCarrito.contarCarrito());
        return "carrito";
    }
}