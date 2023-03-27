package org.ud2.developers.SGPVADU.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.service.IntServiceCategorias;
import org.ud2.developers.SGPVADU.service.IntServiceDetallesOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;
import org.ud2.developers.SGPVADU.util.Utileria;

@Controller
@RequestMapping("/productos")
public class ProductosController {

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceCategorias serviceCategorias;

	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;
	
    @Autowired
    private Utileria util;

	@GetMapping("/detalle")
	public String consultarDetalleProducto(@RequestParam("id") int idProducto, Model model) {
		Producto producto = serviceProductos.buscarPorId(idProducto);
		model.addAttribute("producto", producto);
		model.addAttribute("items", serviceDetallesOrdenes.contarDetalles());
		return "productos/detalle";
	}

	@GetMapping("/buscar")
	public String modificarProducto(@RequestParam("id") int idProducto, Model model) {
		Producto producto = serviceProductos.buscarPorId(idProducto);
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		return "productos/formProducto";
	}

	@GetMapping("/eliminar")
	public String eliminarProducto(Producto producto, RedirectAttributes model) {
		System.out.println(producto);
		serviceProductos.eliminarPorId(producto.getId());
		model.addFlashAttribute("msg", "Producto Eliminado");
		return "redirect:/productos/indexPaginado";
	}

	@PostMapping("/agregar")
	public String agregarProducto(Producto producto, BindingResult result, Model model, RedirectAttributes model2,
			@RequestParam("archivoImagen") MultipartFile file) {		
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
			return "productos/formProducto";
		}
		if (!file.isEmpty()) {
			String fileName = util.uploadImage(file);
			if (fileName != null) {
				producto.setImagen(fileName);
			}
		}
		if (producto.getId() == null) {
			model2.addFlashAttribute("msg", "Producto Agregado");
		} else {
			Producto p = serviceProductos.buscarPorId(producto.getId());
			producto.setImagen(p.getImagen());
			model2.addFlashAttribute("msg", "Producto Modificado");
		}
		serviceProductos.guardarProducto(producto);
		return "redirect:/productos/indexPaginado";
	}

	@GetMapping("/nuevo")
	public String mostrarFormProducto(Producto producto, Model model) {
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		return "productos/formProducto";
	}

	@GetMapping(value = "/indexPaginado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Producto> productos = serviceProductos.buscarTodas(page);
		model.addAttribute("productos", productos);
		model.addAttribute("total", serviceProductos.contarProductos());
		return "productos/listaProductos";
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
