package org.ud2.developers.SGPVADU.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.ud2.developers.SGPVADU.service.UploadFileService;
import org.ud2.developers.SGPVADU.util.Utileria;

@Controller
@RequestMapping("/productos")
public class ProductosController {

	@Value("${SGP-VADU.ruta.imagenes}")
	private String ruta;

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceCategorias serviceCategorias;

	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;

	@Autowired
	private UploadFileService upload;

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
		Producto p = new Producto();
		p = serviceProductos.buscarPorId(producto.getId());
		//eliminar cuando no sea la imagen por defecto
		if (!p.getImagen().equals("default.jpg")) {
			upload.deleteImage(p.getImagen());
		}
		serviceProductos.eliminarPorId(producto.getId());
		model.addFlashAttribute("msg", "Producto Eliminado");
		return "redirect:/productos/indexPaginado";
	}

	@PostMapping("/agregar")
	public String agregarProducto(Producto producto, BindingResult result, Model model, RedirectAttributes model2,
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		Producto p = new Producto();
		p = serviceProductos.buscarPorId(producto.getId());
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
			return "productos/formProducto";
		}
		/*
		 * if (!multiPart.isEmpty()) { String nombreImagen =
		 * Utileria.guardarArchivo(multiPart, ruta); if (nombreImagen != null) {
		 * producto.setImagen(nombreImagen); } } if(!multiPart.isEmpty()) { Path
		 * directorioImagenes = Paths.get("src//main//resources//static/images/");
		 * String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		 * 
		 * try { byte[] bytesImg = multiPart.getBytes(); Path rutaCompleta =
		 * Paths.get(rutaAbsoluta + "//" + multiPart.getOriginalFilename());
		 * Files.write(rutaCompleta, bytesImg);
		 * producto.setImagen(multiPart.getOriginalFilename()); } catch (IOException e)
		 * { e.printStackTrace(); } }
		 */
		/*if (producto.getId() == null) {
			String nombreImagen = "";
			try {
				nombreImagen = upload.saveImage(multiPart);
			} catch (IOException e) {
				e.printStackTrace();
			}
			producto.setImagen(nombreImagen);
			model2.addFlashAttribute("msg", "Producto Agregado");
		} else {
			if (multiPart.isEmpty()) {
				producto.setImagen(p.getImagen());
			} else {
				if (!p.getImagen().equals("img0.png")) {
					upload.deleteImage(p.getImagen());
				}
				String nombreImagen = "";
				try {
					nombreImagen = upload.saveImage(multiPart);
				} catch (IOException e) {
					e.printStackTrace();
				}
				producto.setImagen(nombreImagen);
			}*/
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) {
				producto.setImagen(nombreImagen);
			}
		}
		if (producto.getId() == null) model2.addFlashAttribute("msg", "Producto Agregado");
		else model2.addFlashAttribute("msg", "Producto Modificado");
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
