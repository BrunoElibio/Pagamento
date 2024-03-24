package br.com.Pagamento.Service;

import br.com.Pagamento.Model.DadosPagamento;
import br.com.Pagamento.Model.Pagamento;
import br.com.Pagamento.Providers.PagamentoProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

class PagamentoServiceTest {

    @Mock
    private PagamentoProvider pagamentoProvider;

    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calcularPagamento_porCento() {
        DadosPagamento dadosPagamento = new DadosPagamento();
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(new Pagamento(100.0));
        pagamentos.add(new Pagamento(200.0));
        dadosPagamento.setPagamentos(pagamentos);
        dadosPagamento.setDesconto("10%");

        List<Pagamento> resultado = pagamentoService.calcularPagamento(dadosPagamento);

        assertEquals(2, resultado.size());
        assertEquals(90.0, resultado.get(0).getValor());
        assertEquals(180.0, resultado.get(1).getValor());
    }

    @Test
    void calcularPagamento_inteiro() {
        DadosPagamento dadosPagamento = new DadosPagamento();
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(new Pagamento(10.0));
        pagamentos.add(new Pagamento(20.0));
        dadosPagamento.setPagamentos(pagamentos);
        dadosPagamento.setDesconto("5");

        List<Pagamento> resultado = pagamentoService.calcularPagamento(dadosPagamento);

        assertEquals(2, resultado.size());
        assertEquals(8.33, resultado.get(0).getValor());
        assertEquals(16.67, resultado.get(1).getValor());
    }

    @Test
    void gerarLinkPagamento() {
        double valor = 300.0;

        when(pagamentoProvider.gerarLinkPagamento(anyDouble())).thenReturn("https://paymentlink.com");

        String resultado = pagamentoService.gerarLinkPagamento(valor);

        assertEquals("https://paymentlink.com", resultado);
    }

    @Test
    void gerarLinkPagamentoPicPay() {
        double valor = 300.0;

        when(pagamentoProvider.gerarLinkPagamento(anyDouble())).thenReturn("https://picpay.me/brunoelibio/300.0");

        String resultado = pagamentoService.gerarLinkPagamento(valor);

        assertEquals("https://picpay.me/brunoelibio/300.0", resultado);
    }
}
