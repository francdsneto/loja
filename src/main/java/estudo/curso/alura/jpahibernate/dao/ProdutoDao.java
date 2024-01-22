package estudo.curso.alura.jpahibernate.dao;

import estudo.curso.alura.jpahibernate.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDao {

    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        return em.createQuery("SELECT p FROM Produto AS p", Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        /**
         * Named parameter usa-se :nomeparametro
         * Positional Parameter usa-se ?1, e em setParameter passa-se o numero do parametro
         * setParameter(1,nome);
         */
        String jpql = "SELECT p FROM Produto AS p WHERE p.nome LIKE CONCAT('%',:nome,'%')";
        return em.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeDaCategoria(String nome) {
        /**
         * Named parameter usa-se :nomeparametro
         * Positional Parameter usa-se ?1, e em setParameter passa-se o numero do parametro
         * setParameter(1,nome);
         */
        return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
        /**
         * Named parameter usa-se :nomeparametro
         * Positional Parameter usa-se ?1, e em setParameter passa-se o numero do parametro
         * setParameter(1,nome);
         */
        String jpql = "SELECT p.preco FROM Produto AS p WHERE p.nome LIKE CONCAT('%',:nome,'%')";
        return em.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }
}
