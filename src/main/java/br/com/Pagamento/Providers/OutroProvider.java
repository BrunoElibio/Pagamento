package br.com.Pagamento.Providers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutroProvider implements PagamentoProvider {

    private static final Logger logger = LoggerFactory.getLogger(PicpayProvider.class);

    @Override
    public String gerarLinkPagamento(double valor) {

        // Caso futuramente seja implementado outro método de pagamento
        logger.info("Gerando link de pagamento para outro provedor.");
        return "";
    }
}
