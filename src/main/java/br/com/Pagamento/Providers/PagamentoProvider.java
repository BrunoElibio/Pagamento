package br.com.Pagamento.Providers;

import org.springframework.stereotype.Component;
public interface PagamentoProvider {
    String gerarLinkPagamento(double valor);
}