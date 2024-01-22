package estudo.curso.alura.jpahibernate.dao;

import estudo.curso.alura.jpahibernate.model.Pedido;
import estudo.curso.alura.jpahibernate.vo.RelatorioVendaVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> buscarTodos() {
        return em.createQuery("SELECT p FROM Pedido AS p", Pedido.class).getResultList();
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido AS p";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    /**
     * O retorno deste método poderia ser retornado um List<Object[]> e no parametro
     * do createQuery retornar um Object[].class
     *
     * public List<Object[]> relatorioDeVendas() {
     *
     *         String jpql = "SELECT produto.nome, " +
     *                 "SUM(item.quantidade)," +
     *                 "MAX(pedido.data) " +
     *                 "FROM Pedido AS pedido " +
     *                 "JOIN pedido.itens AS item " +
     *                 "JOIN item.produto AS produto " +
     *                 "GROUP BY produto.nome " +
     *                 "ORDER BY item.quantidade DESC";
     *
     *         return em.createQuery(jpql, Object[].class).getResultList();
     * }
     *
     */
    public List<RelatorioVendaVo> relatorioDeVendas() {

        String jpql = "SELECT new estudo.curso.alura.jpahibernate.vo.RelatorioVendaVo(produto.nome, " +
                "SUM(item.quantidade)," +
                "MAX(pedido.data)) " +
                "FROM Pedido AS pedido " +
                "JOIN pedido.itens AS item " +
                "JOIN item.produto AS produto " +
                "GROUP BY produto.nome " +
                "ORDER BY item.quantidade DESC";

        return em.createQuery(jpql, RelatorioVendaVo.class).getResultList();
    }

}
