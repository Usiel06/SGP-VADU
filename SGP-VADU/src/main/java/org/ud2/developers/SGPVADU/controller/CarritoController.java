package org.ud2.developers.SGPVADU.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ud2.developers.SGPVADU.entity.DetalleOrden;
import org.ud2.developers.SGPVADU.entity.Orden;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.entity.Usuario;
import org.ud2.developers.SGPVADU.service.IntServiceDetallesOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;
import org.ud2.developers.SGPVADU.service.IntServiceUsuarios;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

	// private final Logger log = LoggerFactory.getLogger(CarritoController.class);

	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;

	@Autowired
	private IntServiceOrdenes serviceOrdenes;

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceUsuarios serviceUsuarios;

	// Para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

	// Datos de la orden
	Orden orden = new Orden();

	@GetMapping("/orden")
	public String mostrarOrden(Model model, org.springframework.security.core.Authentication auth) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(serviceOrdenes.generarNumeroOrden());

		// Usuario
		// Usuario usuario =
		// serviceUsuarios.buscarPorId(Integer.parseInt(session.getAttribute("idUsuario").toString()));
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		orden.setUsuario(usuario);
		serviceOrdenes.guardarOrden(orden);

		// Guardar detalles
		for (DetalleOrden dt : detalles) {
			dt.setOrden(orden);
			serviceDetallesOrdenes.guardarDetalle(dt);
		}

		// Limpiar lista y orden
		orden = new Orden();
		detalles.clear();

		return "redirect:/carrito/";
	}

	@GetMapping("/eliminar")
	public String eliminarCarrito(@RequestParam("id") Integer idDetalle, Model model) {
		// lista nueva de prodcutos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() != idDetalle) {
				ordenesNueva.add(detalleOrden);
			}
		}

		// poner la nueva lista con los productos restantes
		detalles = ordenesNueva;

		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		orden.setTotal(sumaTotal);
		return "redirect:/carrito/";
	}

	@PostMapping("/agregar")
	public String agregarCarrito(@RequestParam Integer idProducto, @RequestParam Integer cantidad,
			RedirectAttributes model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = serviceProductos.buscarPorId(idProducto);
		double sumaTotal = 0;
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecioKg());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecioKg() * cantidad);
		detalleOrden.setProducto(producto);
		boolean ingresado = detalles.stream()
				.anyMatch(p -> p.getProducto().getId() == idProducto);
		if (!ingresado) {
			detalles.add(detalleOrden);
		} /*else {
			for (DetalleOrden dorden : detalles) {
				if (dorden.getProducto().getId().compareTo(idProducto) == 0) {
					detalleOrden.setId(dorden.getId());
					detalleOrden.setCantidad(cantidad + dorden.getCantidad());
					detalleOrden.setTotal(producto.getPrecioKg() * (cantidad + dorden.getCantidad()));
					detalles.add(detalleOrden);
				}
			}
		}*/
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		return "redirect:/carrito/";
	}

	@GetMapping("/")
	public String carrito(Model model, org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		DecimalFormat df = new DecimalFormat("0.0");
		model.addAttribute("carrito", detalles);
		model.addAttribute("items", detalles.size());
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		model.addAttribute("iva", df.format(orden.getTotal() * .16));
		model.addAttribute("totalIva", df.format(orden.getTotal() * .16 + orden.getTotal()));
		return "carrito";
	}
}