package org.ud2.developers.SGPVADU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ud2.developers.SGPVADU.entity.Contacto;
import org.ud2.developers.SGPVADU.service.IntServiceContactos;

@Controller
@RequestMapping("/contactos")
public class ContactosController {
	
	@Autowired
	private IntServiceContactos serviceContactos;
	
	@GetMapping("/noLeido")
	public String cambiarNoLeido(@RequestParam("id") int idContacto, RedirectAttributes model) {
		Contacto contacto = serviceContactos.buscarPorId(idContacto);
		contacto.setEstatus(0);
		serviceContactos.guardarContacto(contacto);
		model.addFlashAttribute("msg", "El estado del mensaje se cambió a No Leído.");
		return "redirect:/contactos/indexPaginado";
	}
	
	@GetMapping("/leido")
	public String cambiarLeido(@RequestParam("id") int idContacto, RedirectAttributes model) {
		Contacto contacto = serviceContactos.buscarPorId(idContacto);
		contacto.setEstatus(1);
		serviceContactos.guardarContacto(contacto);
		model.addFlashAttribute("msg", "El estado del mensaje se cambió a Leído.");
		return "redirect:/contactos/indexPaginado";
	}
	
	@PostMapping("/agregar")
	public String agregarEmpleado(Contacto contacto, Model model, RedirectAttributes model2) {
		model2.addFlashAttribute("msg", "El mensaje de contacto se envió correctamente");
        serviceContactos.guardarContacto(contacto);
        return "redirect:/contactos/indexPaginado";
	}
	
	@GetMapping(value = "/indexPaginado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Contacto> contactos = serviceContactos.buscarTodas(page);
		model.addAttribute("contactos", contactos);
		model.addAttribute("total", serviceContactos.contarContactos());
		return "contactos/listaContactos";
	}
	
}
