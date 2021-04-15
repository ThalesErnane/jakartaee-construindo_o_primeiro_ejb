package br.com.alura.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.alura.dao.AgendamentoEmailDAO;
import br.com.alura.model.AgendamentoEmail;

@Stateless
public class AgendamentoEmailServico {

	private static final Logger LOGGER = Logger.getLogger(AgendamentoEmailServico.class.getName());

	@Inject
	private AgendamentoEmailDAO dao;

	public List<AgendamentoEmail> listar() {
		return dao.listar();
	}

	public void inserir(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setAgendado(false);
		dao.inserir(agendamentoEmail);
	}

	public List<AgendamentoEmail> listarPorNaoAgendado() {
		return dao.listarPorNaoAgendado();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void alterar(AgendamentoEmail agendamentoEmail) {
		if(agendamentoEmail.getEmail().equals("talesernani@gmail.com")) { // cai na exception mas faz o commit dos outros
			throw new RuntimeException("Não foi possível alterar");	
		} else {
			agendamentoEmail.setAgendado(true);
			dao.alterar(agendamentoEmail);
		} 
	}

	public void enviar(AgendamentoEmail agendamentoEmail) {
		try {
			Thread.sleep(5000);
			LOGGER.info("o e-mail do(a) usuário" + agendamentoEmail.getEmail() + "foi enviado");
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		}
	}
}
