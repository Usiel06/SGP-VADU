package org.ud2.developers.SGPVADU.controller;

import java.text.DecimalFormat;

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
import org.ud2.developers.SGPVADU.service.IntServiceDetallesOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;

	@Autowired
	private IntServiceProductos serviceProductos;

	Orden orden = new Orden();

	@GetMapping("/orden")
	public String mostrarOrden(Model model) {
		DecimalFormat df = new DecimalFormat("#.00");
		model.addAttribute("iva", df.format(orden.getTotal() * .16));
		return "redirect:/carrito/";
	}
	
	@GetMapping("/eliminar")
	public String eliminarCarrito(@RequestParam("id") Integer idDetalle) {
		double sumaTotal = orden.getTotal() - serviceDetallesOrdenes.buscarPorId(idDetalle).getTotal();
		orden.setTotal(sumaTotal);
		serviceDetallesOrdenes.eliminarPorId(idDetalle);
		return "redirect:/carrito/";
	}

	@PostMapping("/agregar")
	public String agregarCarrito(@RequestParam Integer idProducto, @RequestParam Integer cantidad, RedirectAttributes model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = serviceProductos.buscarPorId(idProducto);
		double sumaTotal = 0;;
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecioKg());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecioKg() * cantidad);
		detalleOrden.setProducto(producto);
		boolean ingresado = serviceDetallesOrdenes.obtenerDetalles().stream()
				.anyMatch(p -> p.getProducto().getId() == idProducto);
		if (!ingresado) {
			serviceDetallesOrdenes.guardarDetalle(detalleOrden);
		} else {
			for (DetalleOrden dorden : serviceDetallesOrdenes.obtenerDetalles()) {
				if (dorden.getProducto().getId().compareTo(idProducto) == 0) {
					detalleOrden.setId(dorden.getId());
					detalleOrden.setCantidad(cantidad + dorden.getCantidad());
					detalleOrden.setTotal(producto.getPrecioKg() * (cantidad + dorden.getCantidad()));
					serviceDetallesOrdenes.guardarDetalle(detalleOrden);
				}
			}
		}
		sumaTotal = serviceDetallesOrdenes.obtenerDetalles().stream().mapToDouble(dt -> dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		return "redirect:/carrito/";
	}

	@GetMapping("/")
	public String carrito(Model model) {
		DecimalFormat df = new DecimalFormat("#.00");
		model.addAttribute("carrito", serviceDetallesOrdenes.obtenerDetalles());
		model.addAttribute("items", serviceDetallesOrdenes.contarDetalles());
		model.addAttribute("orden", orden);
		model.addAttribute("iva", df.format(orden.getTotal() * .16));
		model.addAttribute("totalIva", df.format(orden.getTotal() * .16 + orden.getTotal()));
		return "carrito";
	}
}