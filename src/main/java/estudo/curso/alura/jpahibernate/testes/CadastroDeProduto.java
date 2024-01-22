package estudo.curso.alura.jpahibernate.testes;

import estudo.curso.alura.jpahibernate.dao.CategoriaDao;
import estudo.curso.alura.jpahibernate.dao.ProdutoDao;
import estudo.curso.alura.jpahibernate.model.Categoria;
import estudo.curso.alura.jpahibernate.model.Produto;
import estudo.curso.alura.jpahibernate.util.JpaUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {

        cadastrarProduto();

        Long id = 1l;

        EntityManager em = JpaUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);

        Produto produto = produtoDao.buscarPorId(id);
        System.out.println(produto.getPreco());

        //List<Produto> produtos = produtoDao.buscarTodos();

        //List<Produto> produtos = produtoDao.buscarPorNome("Xiaomi");

        //List<Produto> produtos = produtoDao.buscarPorNomeDaCategoria("CELULARES");

        //produtos.forEach(p -> System.out.println(p.getNome()));

        BigDecimal precoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi");

        System.out.println("Preco do produto = " +precoProduto);

    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");

        Produto celular = new Produto("Xiaomi Redmi","Muito legal", new BigDecimal("800"), celulares);

        EntityManager em = JpaUtil.getEntityManager();

        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);

        em.getTransaction().commit();
        em.close();
    }

    private void exemploAulaEstadosDosObjetosGerenciadosPeloEntityManager() {
        Categoria celulares = new Categoria("CELULARES");

        Produto celular = new Produto("Xiaomi Redmi","Muito legal", new BigDecimal("800"), celulares);

        EntityManager em = JpaUtil.getEntityManager();

//        ProdutoDao produtoDao = new ProdutoDao(em);
//        CategoriaDao categoriaDao = new CategoriaDao(em);
//
//        em.getTransaction().begin();
//
//        categoriaDao.cadastrar(celulares);
//        produtoDao.cadastrar(celular);
//
//        em.getTransaction().commit();
//        em.close();

        /**
         * Testando estados das entidades
         * Transient, Managed, Detached
         */
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        /**
         * Aqui é feito um INSERT
         */
        em.persist(celulares);
        /**
         * Como o valor do nome foi alterado, é feito um UPDATE na tabela após o INSERT
         * para alcançar o valor alterado em nome nesta linha ao se chamar transaction commit ou em.flush
         */
        celulares.setNome("XYZ");

        /**
         * Sincroniza todos os dados alterados nos objetos da transação com o bd
         */
        //em.getTransaction().commit();

        /**
         * Sincroniza as alterações com o banco de dados
         */
        em.flush();
        /**
         * Altera o estado de todos os objetos gerenciados pelo EntityManager transformando
         * todos os objetos managed em detached
         */
        em.clear();

        /**
         * Como a conexão foi fechada, o estado do objeto passou a ser DETACHED, então
         * apesar dele não ser mais transient, por ter um id no banco, as alterações nele
         * não são mais gerenciadas pelo EntityManager
         */
        //em.close();
        //celulares.setNome("ABC");

        /**
         * O método merge coloca um objeto de volta como managed, porém há uma pegadinha.
         * O objeto celulares passado por parametro continua detached, o que se torna managed
         * é o objeto retornado pelo método merge.
         */
        celulares = em.merge(celulares);
        celulares.setNome("ABCD");
        em.flush();

        em.remove(celulares);

        em.flush();
    }

}