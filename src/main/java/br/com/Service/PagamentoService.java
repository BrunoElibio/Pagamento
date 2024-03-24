package br.com.Pagamento.Service;

import br.com.Pagamento.Model.DadosPagamento;
import br.com.Pagamento.Model.Pagamento;
import br.com.Pagamento.Providers.PagamentoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoService {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    private final PagamentoProvider pagamentoProvider;

    @Autowired
    public PagamentoService(PagamentoProvider pagamentoProvider) {
        this.pagamentoProvider = pagamentoProvider;
    }

    public List<Pagamento> calcularPagamento(DadosPagamento dadosPagamento) {
        double totalPago = calcularTotalPago(dadosPagamento.getPagamentos());
        double novoTotal = calcularNovoTotal(totalPago, dadosPagamento.getDesconto());
        logger.info(dadosPagamento.getDesconto());
        List<Pagamento> retornoPagamentosList = new ArrayList<>();

        for (Pagamento pagamento : dadosPagamento.getPagamentos()) {
            Pagamento retornoPagamentos = new Pagamento();
            double percentual = (pagamento.getValor() / totalPago);
            retornoPagamentos.setValor(Math.round(((novoTotal * percentual) + (dadosPagamento.getEntrega() * percentual)) * 100) / 100.0);
            retornoPagamentosList.add(retornoPagamentos);
            logger.info("Percentual do pagamento " + retornoPagamentos.getValor() + ": " + percentual + "%");
        }

        return retornoPagamentosList;
    }

    private double calcularTotalPago(List<Pagamento> pagamentos) {
        double totalPago = 0;
        for (Pagamento pagamento : pagamentos) {
            totalPago += pagamento.getValor();
        }
        return totalPago;
    }

    private double calcularNovoTotal(double totalPago, String desconto) {
        double novoTotal;
        if (!desconto.contains("%")) {
            novoTotal = totalPago - Double.parseDouble(desconto);
        } else {
            novoTotal = totalPago - totalPago * (Double.parseDouble(desconto.replace("%", "")) / 100.0);
        }
        return novoTotal;
    }

    public String gerarLinkPagamento(double valor) {
        logger.info("Gerando link de pagamento.");
        return pagamentoProvider.gerarLinkPagamento(valor);
    }
}
