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
import org.ud2.developers.SGPVADU.entity.Cliente;
import org.ud2.developers.SGPVADU.entity.DetalleOrden;
import org.ud2.developers.SGPVADU.entity.Orden;
import org.ud2.developers.SGPVADU.entity.Producto;
import org.ud2.developers.SGPVADU.entity.Usuario;
import org.ud2.developers.SGPVADU.service.IntServiceClientes;
import org.ud2.developers.SGPVADU.service.IntServiceDetallesOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceOrdenes;
import org.ud2.developers.SGPVADU.service.IntServiceProductos;
import org.ud2.developers.SGPVADU.service.IntServiceUsuarios;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;

	@Autowired
	private IntServiceOrdenes serviceOrdenes;

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceUsuarios serviceUsuarios;
	
	@Autowired
	private IntServiceClientes serviceClientes;

	// Para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

	// Datos de la orden
	Orden orden = new Orden();

	@PostMapping("/confirmar")
	public String confirmarOrden(Cliente cliente, RedirectAttributes model) {
		Cliente c = serviceClientes.buscarPorId(cliente.getId());
		c.setTelefono(cliente.getTelefono());
		c.setCalle(cliente.getCalle());
		c.setMunicipio(cliente.getMunicipio());
		c.setColonia(cliente.getColonia());
		c.setCiudad(cliente.getCiudad());
		c.setEstado(cliente.getEstado());
		c.setCp(cliente.getCp());
		serviceClientes.agregar(c);
		model.addFlashAttribute("msg", "La infromación de dirección se guardó correctamente, ya está listo para ordenar.");
		return "redirect:/carrito/";
	}
	
	@GetMapping()
	public Integer contarItems() {
		return detalles.size();
	}
	
	@GetMapping("/orden")
	public String mostrarOrden(RedirectAttributes model, org.springframework.security.core.Authentication auth) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(serviceOrdenes.generarNumeroOrden());

		// Usuario
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		orden.setUsuario(usuario);
		serviceOrdenes.guardarOrden(orden);

		// Guardar detalles
		for (DetalleOrden dt : detalles) {
			dt.setOrden(orden);
			Producto producto = dt.getProducto();
			producto.setCantidadIngreso(producto.getCantidadIngreso()-dt.getCantidad());
			if(producto.getCantidadIngreso() == 0) {
				producto.setEstatus(0);
			}
			serviceProductos.guardarProducto(producto);
			serviceDetallesOrdenes.guardarDetalle(dt);
		}

		// Limpiar lista y orden
		orden = new Orden();
		detalles.clear();
		
		model.addFlashAttribute("msg", "La orden se realizó correctamente.");
		return "redirect:/carrito/";
	}

	@GetMapping("/eliminar")
	public String eliminarCarrito(DetalleOrden detalleOrd, RedirectAttributes model) {
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		double sumaTotal = 0;
		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() != detalleOrd.getId()) {
				ordenesNueva.add(detalleOrden);
			}
		}
		if (detalleOrd.getId() != null) {
			detalles = ordenesNueva;
			sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
			model.addFlashAttribute("msg", "El item se eliminó correctamente del carrito.");
		} else {
			if (!ordenesNueva.isEmpty()) {
				detalles.clear();
				sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
				model.addFlashAttribute("msg", "Se eliminaron correctamente todos los items del carrito.");
			}
		}
		orden.setTotal(sumaTotal);
		return "redirect:/carrito/";
	}

	@PostMapping("/agregar")
	public String agregarCarrito(@RequestParam Integer idProducto, @RequestParam Integer cantidad,
			RedirectAttributes model) {
		DecimalFormat df = new DecimalFormat("0.0");
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = serviceProductos.buscarPorId(idProducto);
		double sumaTotal = 0;
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecioKg());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecioKg() * cantidad);
		detalleOrden.setProducto(producto);
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		if (!ingresado) {
			detalles.add(detalleOrden);
			model.addFlashAttribute("msg", "El item se agregó correctamente al carrito.");
		} else {
			model.addFlashAttribute("msg2", "No puede agregar más de una vez el mismo producto al carrito.");
		}
			/*
			 * else { for (DetalleOrden dorden : detalles) { if
			 * (dorden.getProducto().getId().compareTo(idProducto) == 0) {
			 * detalleOrden.setId(dorden.getId()); detalleOrden.setCantidad(cantidad +
			 * dorden.getCantidad()); detalleOrden.setTotal(producto.getPrecioKg() *
			 * (cantidad + dorden.getCantidad())); detalles.add(detalleOrden); } } }
			 */
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		orden.setTotal(Double.parseDouble(df.format(sumaTotal)));
		return "redirect:/carrito/";
	}

	@GetMapping("/")
	public String carrito(Model model, org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		DecimalFormat df = new DecimalFormat("0.0");
		model.addAttribute("carrito", detalles);
		model.addAttribute("items", detalles.size());
		model.addAttribute("orden", orden);
		model.addAttribute("cliente", cliente);
		model.addAttribute("iva", df.format(orden.getTotal() * .16));
		model.addAttribute("totalIva", df.format(orden.getTotal() * .16 + orden.getTotal()));
		return "carrito";
	}
}