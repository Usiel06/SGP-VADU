package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ud2.developers.SGPVADU.entity.Perfil;
import org.ud2.developers.SGPVADU.entity.Usuario;
import org.ud2.developers.SGPVADU.service.IntServiceUsuarios;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	public IntServiceUsuarios serviceUsuarios;

	@GetMapping("/eliminar")
	public String eliminarProducto(Usuario usuario, RedirectAttributes model) {
		serviceUsuarios.eliminar(usuario.getId());
		model.addFlashAttribute("msg", "Usuario Eliminado");
		return "redirect:/usuarios/indexPaginado";
	}

	@GetMapping(value = "/indexPaginado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Perfil p = new Perfil();
		for (Usuario usuario : serviceUsuarios.obtenerUsuarios()) {
			p = usuario.getPerfiles().get(0);
		}
		model.addAttribute("perfil", p);
		Page<Usuario> usuarios = serviceUsuarios.buscarTodas(page);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("total", serviceUsuarios.numeroUsuarios());
		return "usuarios/listaUsuarios";
	}
}
