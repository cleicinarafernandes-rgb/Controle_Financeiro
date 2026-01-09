package Controle_Financeiro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Arquivo_Transacoes {
    private String arquivo = "transacoes.csv";

    public void salvar(List<Controle_Financeiro.Transacao> lista) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(arquivo))) {
            for (Transacao t : lista) {
                out.println(t.getTipo() + ";" + t.getValor() + ";" + t.getDescricao() + ";" + t.getData() + ";" + t.getCategoria());
            }
        }
    }

    public List<Controle_Financeiro.Transacao> carregar() throws Exception {
        List<Transacao> lista = new ArrayList<>();
        File f = new File(arquivo);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                if (p[0].equals("Receita"))
                    lista.add(new Receita(Double.parseDouble(p[1]), p[2]));
                else
                    lista.add(new Despesa(Double.parseDouble(p[1]), p[2]));
            }
        }
        return lista;
    }
}
