package org.ud2.developers.SGPVADU.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ud2.developers.SGPVADU.entity.Categoria;

@Service
public class CategoriasServiceImp implements IntServiceCategorias {

	private List<Categoria> categorias;
	
	public CategoriasServiceImp() {
		categorias = new LinkedList<>();
		
		Categoria c1 = new Categoria();
		c1.setId(1);
		c1.setNombre("Crudo");
		c1.setDescripcion("Productos sometidos a un proceso tecnológico sin tratamiento térmico.");
		
		Categoria c2 = new Categoria();
		c2.setId(2);
		c2.setNombre("Fresco");
		c2.setDescripcion("Productos elaborados con carne y grasa molida.");
		
		Categoria c3 = new Categoria();
		c3.setId(3);
		c3.setNombre("Fermentado");
		c3.setDescripcion("Productos elaborados con carne y grasas molidas o picadas.");
		
		Categoria c4 = new Categoria();
		c4.setId(4);
		c4.setNombre("Salado");
		c4.setDescripcion("Productos elaborados con piezas de carne o subproductos y conservados mediante un proceso de salado.");
		
		categorias.add(c1);
		categorias.add(c2);
		categorias.add(c3);
		categorias.add(c4);
	}
	
	@Override
	public List<Categoria> obtenerCategorias() {
		return categorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for(Categoria categoria : categorias) {
			if(categoria.getId().compareTo(idCategoria) == 0) {
				return categoria;
			}
		}
		return null;
	}
	
	@Override
	public void guardarCategoria(Categoria categoria) {
		categorias.add(categoria);
	}

	@Override
	public void eliminarPorId(Integer idCategoria) {
		categorias.remove(buscarPorId(idCategoria));
	}
	
	@Override
	public int contarCategorias() {
		return categorias.size();
	}
	
	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		return null;
	}

}
