package controlefinanceiro.Codigo_Completo_Modificado;

import java.util.List;

public class ResumoFinanceiro {

    private List<Transacao> transacoes;

    public ResumoFinanceiro(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public double totalEntradas() {
        double total = 0;
        for (Transacao t : transacoes) {
            if (t instanceof Receita) {
                total += t.getValor();
            }
        }
        return total;
    }

    public double totalSaidas() {
        double total = 0;
        for (Transacao t : transacoes) {
            if (t instanceof Despesa) {
                total += t.getValor();
            }
        }
        return total;
    }
}
