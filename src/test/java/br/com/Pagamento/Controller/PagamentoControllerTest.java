package br.com.Pagamento.Controller;
import br.com.Pagamento.Model.DadosPagamento;
import br.com.Pagamento.Model.Pagamento;
import br.com.Pagamento.Service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @Test
    void calcularPagamento() throws Exception {
        DadosPagamento dadosPagamento = new DadosPagamento();
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(new Pagamento(100.0));
        pagamentos.add(new Pagamento(200.0));
        dadosPagamento.setPagamentos(pagamentos);

        when(pagamentoService.calcularPagamento(any(DadosPagamento.class))).thenReturn(pagamentos);

        mockMvc.perform(post("/calcularPagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"pagamentos\": [{ \"valor\": 100 }, { \"valor\": 200 }] }"))
                .andExpect(status().isOk());

        verify(pagamentoService).calcularPagamento(any(DadosPagamento.class));
    }

    @Test
    void gerarLinkPagamento() throws Exception {
        double valor = 300.0;

        when(pagamentoService.gerarLinkPagamento(valor)).thenReturn("https://paymentlink.com");

        mockMvc.perform(get("/gerarLinkPagamento")
                        .param("valor", String.valueOf(valor)))
                .andExpect(status().isOk());

        verify(pagamentoService).gerarLinkPagamento(valor);
    }
}
