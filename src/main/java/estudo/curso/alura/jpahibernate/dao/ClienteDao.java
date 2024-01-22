package estudo.curso.alura.jpahibernate.dao;

import estudo.curso.alura.jpahibernate.model.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDao {

    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Cliente cliente) {
        this.em.persist(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        return em.createQuery("SELECT p FROM Cliente AS p", Cliente.class).getResultList();
    }

}
