package org.ud2.developers.SGPVADU.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ud2.developers.SGPVADU.entity.Empleado;
import org.ud2.developers.SGPVADU.entity.Perfil;
import org.ud2.developers.SGPVADU.entity.Usuario;
import org.ud2.developers.SGPVADU.service.IntServiceEmpleados;
import org.ud2.developers.SGPVADU.service.IntServiceUsuarios;

@Controller
@RequestMapping("/empleados")
public class EmpleadosController {

	@Autowired
	private IntServiceEmpleados serviceEmpleados;

	@Autowired
	public IntServiceUsuarios serviceUsuarios;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/buscar")
	public String modificarEmpleado(@RequestParam("id") int idEmpleado, Model model) {
		Empleado empleado = serviceEmpleados.buscarPorId(idEmpleado);
		model.addAttribute("empleado", empleado);
		return "empleados/formEmpleado";
	}

	@GetMapping("/eliminar")
	public String eliminarEmpleado(@RequestParam("id") int idEmpleado, RedirectAttributes model) {
		Empleado empleado = serviceEmpleados.buscarPorId(idEmpleado);
		serviceEmpleados.eliminar(idEmpleado);
		serviceUsuarios.eliminar(empleado.getUsuario().getId());
		model.addFlashAttribute("msg", "Empleado Eliminado");
		return "redirect:/empleados/indexPaginado";
	}

	@PostMapping("/agregar")
	public String agregarEmpleado(Empleado empleado, BindingResult result, Model model, RedirectAttributes model2) {
		Usuario usuario = new Usuario();
		Empleado e = serviceEmpleados.findByNombre(empleado.getNombre());
		if (empleado.getId() == null)
			model2.addFlashAttribute("msg", "Empleado Agregado");
		else
			usuario.setId(e.getUsuario().getId());
			model2.addFlashAttribute("msg", "Empleado Modificado");
		usuario.setNombre(empleado.getNombre());
		usuario.setUsername(empleado.getUsername());
		usuario.setEmail(empleado.getEmail());
		usuario.setPassword(passwordEncoder.encode(empleado.getPassword()));
		usuario.setEstatus(1);
		Perfil perfil = new Perfil();
		perfil.setId(2);
		usuario.agregar(perfil);
		serviceUsuarios.agregar(usuario);
		empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
		empleado.setUsuario(usuario);
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "empleados/formEmpleado";
		}
		serviceEmpleados.agregar(empleado);
		return "redirect:/empleados/indexPaginado";
	}

	@GetMapping("/nuevo")
	public String mostrarFormEmpleado(Empleado empleado, Model model) {
		return "empleados/formEmpleado";
	}

	@GetMapping(value = "/indexPaginado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Empleado> empleados = serviceEmpleados.buscarTodas(page);
		model.addAttribute("empleados", empleados);
		model.addAttribute("total", serviceEmpleados.numeroEmpleados());
		return "empleados/listaEmpleados";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			}

			@Override
			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("dd-MM-yyyy").format((LocalDate) getValue());
			}
		});
	}
}
