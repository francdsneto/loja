package estudo.curso.alura.jpahibernate.model;

import com.sun.xml.bind.v2.model.core.ID;

import javax.persistence.*;

@Entity
@Table(name="categorias")
public class Categoria {


    @EmbeddedId
    private CategoriaId id;

    public Categoria() {
    }

    public Categoria(String nome) {
        this.id = new CategoriaId(nome, "teste");
    }

    public String getNome() {
        return this.id.getNome();
    }

    public void setNome(String nome) {
        this.id.setNome(nome);
    }

}
