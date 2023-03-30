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
import org.ud2.developers.SGPVADU.entity.Cliente;
import org.ud2.developers.SGPVADU.entity.Perfil;
import org.ud2.developers.SGPVADU.entity.Usuario;
import org.ud2.developers.SGPVADU.service.IntServiceCategorias;
import org.ud2.developers.SGPVADU.service.IntServiceClientes;
import org.ud2.developers.SGPVADU.service.IntServiceDetallesOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceOrdenes;
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
	private IntServiceClientes serviceClientes;

	@Autowired
	private IntServiceOrdenes serviceOrdenes;
	
	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CarritoController carritoCtrl;
	
	@GetMapping("/contactanos")
	public String contactanos(Model model) {
		model.addAttribute("items", carritoCtrl.contarItems());
		return "contacto";
	}
	
	@GetMapping("/acerca")
	public String acerca(Model model) {
		model.addAttribute("items", carritoCtrl.contarItems());
		return "acerca";
	}

	@GetMapping("/historialOrdenes")
	public String mostrarHistorialOrdenes(Model model, org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		model.addAttribute("detalles", serviceDetallesOrdenes.obtenerDetalles());
		model.addAttribute("ordenes", serviceOrdenes.buscarPorUsuario(usuario));
		model.addAttribute("cliente", cliente);
		model.addAttribute("items", carritoCtrl.contarItems());
		return "historialOrdenes";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	@PostMapping("/guardar")
	public String guardarUsuario(Usuario usuario) {
		Cliente cliente = new Cliente();
		cliente.setNombre(usuario.getNombre());
		cliente.setApellidoPaterno(usuario.getApellidoPaterno());
		cliente.setApellidoMaterno(usuario.getApellidoMaterno());
		cliente.setUsername(usuario.getUsername());
		cliente.setEmail(usuario.getEmail());
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		cliente.setPassword(usuario.getPassword());
		cliente.setFechaRegistro(usuario.getFechaRegistro());
		usuario.setEstatus(1);
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		cliente.setUsuario(usuario);
		serviceUsuarios.agregar(usuario);
		serviceClientes.agregar(cliente);
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
		model.addAttribute("items", carritoCtrl.contarItems());
		return "home";
	}
}
