package br.com.cadastramento.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.cadastramento.model.Cliente;

@Stateless
public class ClienteDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void cadastrar(Cliente cliente) {
		em.persist(cliente);
	}
	
	public List<Cliente> listar() {
		String jpql = "SELECT c FROM Cliente c";
		return em.createQuery(jpql, Cliente.class).getResultList();
	}
	
	public void remover(Long id) {
		Cliente cliente = em.find(Cliente.class, id);
		em.remove(cliente);
	}

	public Cliente buscarPorId(Long id) {
		return em.find(Cliente.class, id);
	}

	public Cliente atualizar(Long id, String sigla, Cliente cliente) {
		Cliente fregues = em.find(Cliente.class, id);
		//System.out.println(cliente.getEstado());
		if(fregues == null)
			return null;
		
		fregues.setNome(cliente.getNome());
		fregues.setEstado(sigla);
		fregues.setIdade(cliente.getIdade());
		
		em.merge(fregues);
		return fregues;
		
	}
}
