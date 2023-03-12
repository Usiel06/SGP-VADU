package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
public class HomeController {
    
	@Autowired
	private IntServiceProductos serviceProductos;
	
    @GetMapping("/carrito")
    public String mostrarCarrito(Model model) {
        return "carrito";
    }
    
	@GetMapping("/")
	public String mostrarIndex(Model model) {
		model.addAttribute("productos", serviceProductos.obtenerEnVenta());
		return "index";
	}
}
