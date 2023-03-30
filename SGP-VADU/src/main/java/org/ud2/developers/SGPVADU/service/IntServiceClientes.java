package org.ud2.developers.SGPVADU.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ud2.developers.SGPVADU.entity.Cliente;
import org.ud2.developers.SGPVADU.entity.Usuario;

public interface IntServiceClientes {
	public List<Cliente> obtenerClientes();
	public void agregar(Cliente cliente);
	public Cliente buscarPorId(Integer idCliente);
	public Cliente buscarPorUsuario(Usuario usuario);
	public void eliminar(Integer idCliente);
	public Integer numeroClientes();
	Page<Cliente> buscarTodas(Pageable page);
}
