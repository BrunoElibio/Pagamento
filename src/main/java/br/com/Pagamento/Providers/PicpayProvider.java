package br.com.Pagamento.Providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PicpayProvider implements PagamentoProvider {
    @Override
    public String gerarLinkPagamento(double valor) {
        return "https://picpay.me/brunoelibio/" + valor;
    }
}