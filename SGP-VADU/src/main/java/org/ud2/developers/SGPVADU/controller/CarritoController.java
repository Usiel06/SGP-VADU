package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private IntServiceProductos serviceProductos;

    @GetMapping("/")
    public String mostrarCarrito(Model model) {
        return "carrito";
    }
}