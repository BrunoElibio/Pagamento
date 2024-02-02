package br.com.Pagamento.Controller;

import br.com.Pagamento.Model.DadosPagamento;
import br.com.Pagamento.Model.Pagamento;
import br.com.Pagamento.Providers.PagamentoProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    @Mock
    private PagamentoProvider pagamentoProvider;

    @InjectMocks
    private PagamentoController pagamentoController;

    private MockMvc mockMvc;

    @Test
    public void testCalcularPagamento() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
        // Englobando construtores
        DadosPagamento dadosPagamento = new DadosPagamento();
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(new Pagamento(42.0));
        pagamentos.add(new Pagamento(8.0));
        dadosPagamento.setPagamentos(pagamentos);
        dadosPagamento.setDesconto("20");
        dadosPagamento.setEntrega(8);

        // Parseando como JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String dadosPagamentoJson = objectMapper.writeValueAsString(dadosPagamento);

        // Teste de retorno da calcularPagamento - Teste direto do desafio
        mockMvc.perform(MockMvcRequestBuilders.post("/calcularPagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosPagamentoJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].valor").value(31.92))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].valor").value(6.08));
    }

    @Test
    public void testGerarLinkPagamento() throws Exception {
        // Teste de conexão ao gerarLinkPagamento
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
        double valor =10;
        mockMvc.perform(MockMvcRequestBuilders.get("/gerarLinkPagamento")
                        .param("valor", String.valueOf(valor)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
