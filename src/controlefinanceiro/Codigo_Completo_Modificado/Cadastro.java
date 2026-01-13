package controlefinanceiro.Codigo_Completo_Modificado;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Cadastro extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmar;
    private final String ARQUIVO = "usuarios.txt";

    public Cadastro() {
        setTitle("Cadastro - Controle Financeiro");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setBackground(new Color(30, 30, 30));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel titulo = new JLabel("CRIAR CONTA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoUsuario.setBorder(BorderFactory.createTitledBorder("Usuário"));

        campoSenha = new JPasswordField();
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        campoConfirmar = new JPasswordField();
        campoConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoConfirmar.setBorder(BorderFactory.createTitledBorder("Confirmar senha"));

        JButton btnCadastrar = new JButton("CADASTRAR");
        estilizarBotao(btnCadastrar);

        JButton btnVoltar = new JButton("Voltar para Login");
        estilizarBotaoLink(btnVoltar);

        painel.add(titulo);
        painel.add(Box.createRigidArea(new Dimension(0, 25)));
        painel.add(campoUsuario);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        painel.add(campoSenha);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        painel.add(campoConfirmar);
        painel.add(Box.createRigidArea(new Dimension(0, 25)));
        painel.add(btnCadastrar);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(btnVoltar);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        btnVoltar.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });
    }

    private void estilizarBotao(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void estilizarBotaoLink(JButton btn) {
        btn.setBackground(new Color(30, 30, 30));
        btn.setForeground(Color.LIGHT_GRAY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void cadastrarUsuario() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());
        String confirmar = new String(campoConfirmar.getPassword());

        if (usuario.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        if (!senha.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            return;
        }

        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {
            fw.write(usuario + ";" + senha + "\n");
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            new Login().setVisible(true);
            dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
