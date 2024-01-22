package estudo.curso.alura.jpahibernate.vo;

import java.time.LocalDate;

public record RelatorioVendaVo(String nomeProduto, long quantidadeVendida, LocalDate dataUltimaVenda) {
}
