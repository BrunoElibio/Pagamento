package br.com.Pagamento.Model;

import java.util.List;
public class DadosPagamento {
    private List<Pagamento> pagamentos;
    private String desconto;
    private double entrega;

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public double getEntrega(){
        return entrega;
    }
    public void setEntrega(double entrega) {
        this.entrega = entrega;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }
}