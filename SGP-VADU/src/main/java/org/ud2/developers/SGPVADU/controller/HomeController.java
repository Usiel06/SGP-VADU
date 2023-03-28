package org.ud2.developers.SGPVADU.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.ud2.developers.SGPVADU.entity.Categoria;
import org.ud2.developers.SGPVADU.entity.Perfil;
import org.ud2.developers.SGPVADU.entity.Usuario;
import org.ud2.developers.SGPVADU.service.IntServiceCategorias;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;
import org.ud2.developers.SGPVADU.service.IntServiceUsuarios;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@Autowired
	private IntServiceCategorias serviceCategorias;

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceUsuarios serviceUsuarios;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/contacto")
	public String contactanos() {
		return "contacto";
	}
	
	@GetMapping("/acerca")
	public String acerca() {
		return "acerca";
	}

	/*
	 * @GetMapping("/user") public String
	 * mostrarUsuario(org.springframework.security.core.Authentication auth) {
	 * List<Orden> ordenes =
	 * repoOrdenes.findByUsuario(serviceUsuarios.buscarPorId(1));
	 * System.out.println(ordenes); String userName = auth.getName();
	 * System.out.println(auth.getAuthorities() + "nzfdjbgbbshg");
	 * System.out.println(userName); return "redirect:/"; }
	 */

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	@PostMapping("/guardar")
	public String guardarUsuario(Usuario usuario) {
		usuario.setEstatus(1);
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		// usuario.setPassword("{noop}" + usuario.getPassword());
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		serviceUsuarios.agregar(usuario);
		return "redirect:/";
	}

	@GetMapping("/signup")
	public String mostrarFormRegistro() {
		return "formRegistro";
	}

	@GetMapping("/login")
	public String mostrarFormLogin() {
		return "formLogin";
	}

	@GetMapping("/")
	public String mostrarIndex(Model model) {
		List<Categoria> categorias = new LinkedList<>();
		model.addAttribute("productos", serviceProductos.obtenerEnVenta());
		for (Categoria categoria : serviceCategorias.obtenerCategorias()) {
			if (categoria.getId().compareTo(1) == 1) {
				categorias.add(categoria);
			}
		}
		model.addAttribute("categoria", serviceCategorias.buscarPorId(1));
		model.addAttribute("categorias", categorias);
		return "home";
	}
}
