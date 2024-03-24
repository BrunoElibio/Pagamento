package br.com.Pagamento.Controller;

import br.com.Pagamento.Model.DadosPagamento;
import br.com.Pagamento.Model.Pagamento;
import br.com.Pagamento.Service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/calcularPagamento")
    public ResponseEntity<List<Pagamento>> calcularPagamento(@RequestBody DadosPagamento dadosPagamento) {
        if (dadosPagamento == null || dadosPagamento.getPagamentos() == null || dadosPagamento.getPagamentos().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Pagamento> resultado = pagamentoService.calcularPagamento(dadosPagamento);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/gerarLinkPagamento")
    public ResponseEntity<String> gerarLinkPagamento(@RequestParam("valor") String valorString) {
        double valor;
        try {
            valor = Double.parseDouble(valorString);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("O valor deve ser numérico");
        }
        return ResponseEntity.ok(pagamentoService.gerarLinkPagamento(valor));
    }
}
