package br.com.alura.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import br.com.alura.model.AgendamentoEmail;

@Stateless // EJB
@TransactionManagement(TransactionManagementType.BEAN) // tira o controle da transação do CONTAINER
public class AgendamentoEmailDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject 
	private UserTransaction userTransaction;

	public List<AgendamentoEmail> listar() {

		return entityManager.createQuery("Select ae from AgendamentoEmail ae", AgendamentoEmail.class).getResultList();

	}

	public void inserir(AgendamentoEmail agendamentoEmail) {
		entityManager.persist(agendamentoEmail);
	}
	
	public List<AgendamentoEmail> listarPorNaoAgendado(){
		return entityManager.createQuery("Select ae from AgendamentoEmail ae where ae.agendado = false", 
				AgendamentoEmail.class).getResultList();
	}
	
	public void alterar(AgendamentoEmail agendamentoEmail) {
		try {
			userTransaction.begin();
			entityManager.merge(agendamentoEmail);
			userTransaction.commit();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
