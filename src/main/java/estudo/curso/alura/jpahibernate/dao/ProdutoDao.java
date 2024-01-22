package estudo.curso.alura.jpahibernate.dao;

import estudo.curso.alura.jpahibernate.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {

        String jpql = "SELECT p FROM Produto p WHERE 1=1 ";

        if(nome != null && !nome.trim().isEmpty())
        {
            jpql += "AND p.nome LIKE CONCAT('%',:nome,'%') ";
        }
        if(preco != null)
        {
            jpql += "AND p.preco = :preco ";
        }
        if(dataCadastro != null)
        {
            jpql += "AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);

        if(nome != null && !nome.trim().isEmpty())
        {
            query.setParameter("nome",nome);
        }
        if(preco != null)
        {
            query.setParameter("preco",preco);
        }
        if(dataCadastro != null)
        {
            query.setParameter("dataCadastro",dataCadastro);
        }

        return query.getResultList();

    }

    public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);
        Predicate filtros = criteriaBuilder.and();

        if(nome != null && !nome.trim().isEmpty())
        {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.like(from.get("nome"),nome));
        }
        if(preco != null)
        {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("preco"),preco));
        }
        if(dataCadastro != null)
        {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("dataCadastro"),dataCadastro));
        }

        query.where(filtros);

        return em.createQuery(query).getResultList();

    }


}
