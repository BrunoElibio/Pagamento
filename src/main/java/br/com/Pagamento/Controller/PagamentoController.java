package br.com.Pagamento.Controller;

import br.com.Pagamento.Model.DadosPagamento;
import br.com.Pagamento.Model.Pagamento;
import br.com.Pagamento.Providers.PagamentoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PagamentoController {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoController.class);

    private final PagamentoProvider pagamentoProvider;

    @PostMapping("/calcularPagamento")
    public List<Pagamento> calcularPagamento(@RequestBody DadosPagamento dadosPagamento) {
        double totalPago = 0;
        double novoTotal = 0;

        List<Pagamento> retornoPagamentosList = new ArrayList<>();

        for (Pagamento pagamento : dadosPagamento.getPagamentos()) {
            totalPago += pagamento.getValor();
        }

        if (!dadosPagamento.getDesconto().contains("%")) {
            novoTotal = totalPago - Double.parseDouble(dadosPagamento.getDesconto());
        } else {
            novoTotal = totalPago - totalPago * (Double.parseDouble(dadosPagamento.getDesconto().replace("%", "")) / 100.0);
        }

        for (Pagamento pagamento : dadosPagamento.getPagamentos()) {
            Pagamento retornoPagamentos = new Pagamento();
            double percentual = (pagamento.getValor() / totalPago);
            retornoPagamentos.setValor(Math.round(((novoTotal * percentual) + (dadosPagamento.getEntrega() * percentual)) * 100)/ 100.0);
            retornoPagamentosList.add(retornoPagamentos);
            logger.info("Percentual do pagamento " + retornoPagamentos.getValor() + ": " + percentual + "%");
        }

        return retornoPagamentosList;
    }

    @Autowired
    public PagamentoController(PagamentoProvider pagamentoProvider) {
        this.pagamentoProvider = pagamentoProvider;
    }

    @GetMapping("/gerarLinkPagamento")
    public String gerarLinkPagamento(@RequestParam("valor") double valor) {
        logger.info("Gerando link de pagamento.");
        return pagamentoProvider.gerarLinkPagamento(valor);
    }
}
