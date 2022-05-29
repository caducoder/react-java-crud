package br.com.cadastramento.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.cadastramento.model.Estados;

@Stateless
public class EstadoDAO {
	
	@PersistenceContext
	private EntityManager em;

	public List<Estados> listar() {
		String jpql = "SELECT e FROM Estados e";
		return em.createQuery(jpql, Estados.class).getResultList();
	}

}
