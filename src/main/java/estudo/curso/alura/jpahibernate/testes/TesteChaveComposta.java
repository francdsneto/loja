package estudo.curso.alura.jpahibernate.testes;

import estudo.curso.alura.jpahibernate.dao.CategoriaDao;
import estudo.curso.alura.jpahibernate.dao.ClienteDao;
import estudo.curso.alura.jpahibernate.dao.PedidoDao;
import estudo.curso.alura.jpahibernate.dao.ProdutoDao;
import estudo.curso.alura.jpahibernate.model.*;
import estudo.curso.alura.jpahibernate.util.JpaUtil;
import estudo.curso.alura.jpahibernate.vo.RelatorioVendaVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class TesteChaveComposta {

    public static void main(String[] args) {

        popularBancoDeDados();

        EntityManager em = JpaUtil.getEntityManager();

        em.getTransaction().begin();

        em.find(Categoria.class, new CategoriaId("CELULARES","teste"));

        em.close();

    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMATICA");

        Produto celular = new Produto("Xiaomi Redmi","Muito legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5","Playstation 5", new BigDecimal("3800"), videogames);
        Produto notebook = new Produto("Dell","Alienware", new BigDecimal("800"), informatica);

        Cliente cliente = new Cliente("Neto","12312312312");

        EntityManager em = JpaUtil.getEntityManager();

        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(informatica);
        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(notebook);
        clienteDao.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();
    }

}
