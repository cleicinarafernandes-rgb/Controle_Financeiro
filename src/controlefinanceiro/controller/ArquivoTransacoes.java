package controlefinanceiro.controller;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import controlefinanceiro.Codigo_Completo_Modificado.*;

public class ArquivoTransacoes {

    private String caminho; // variável para armazenar o caminho do arquivo do usuário

    // Construtor recebe o usuário e cria o arquivo específico dele
    public ArquivoTransacoes(String usuario) {
        // cria pasta "usuarios" se não existir
        File pasta = new File("usuarios");
        if (!pasta.exists()) pasta.mkdir();

        // arquivo específico do usuário
        this.caminho = "usuarios/transacoes_" + usuario + ".txt";
    }

    // Método para salvar as transações do usuário
    public void salvar(List<Transacao> transacoes) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminho))) {
            for (Transacao t : transacoes) {
                writer.println(
                        t.getTipo() + ";" +
                        t.getValor() + ";" +
                        t.getDescricao() + ";" +
                        t.getData() + ";" +
                        t.getCategoria()
                );
            }
        }
    }

    // Método para carregar as transações do usuário
    public List<Transacao> carregar() throws Exception {
        List<Transacao> lista = new ArrayList<>();
        File file = new File(caminho);
        if (!file.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                String tipo = partes[0];
                double valor = Double.parseDouble(partes[1]);
                String descricao = partes[2];
                LocalDate data = LocalDate.parse(partes[3]);
                String categoria = partes[4];

                if (tipo.equals("Receita")) {
                    lista.add(new Receita(valor, descricao, data, categoria));
                } else {
                    lista.add(new Despesa(valor, descricao, data, categoria));
                }
            }
        }

        return lista;
    }
}
