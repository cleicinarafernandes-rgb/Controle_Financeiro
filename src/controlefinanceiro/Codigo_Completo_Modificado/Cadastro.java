package controlefinanceiro.Codigo_Completo_Modificado;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Cadastro extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private final String ARQUIVO = "usuarios.txt";

    public Cadastro() {
        setTitle("Cadastro de Usuário");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Painel superior (título)
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(30, 30, 30));
        JLabel titulo = new JLabel("Cadastro de Usuário");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelTopo.add(titulo);
        add(painelTopo, BorderLayout.NORTH);

        // Painel central (campos)
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridBagLayout());
        painelCentral.setBackground(new Color(40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentral.add(lblUsuario, gbc);

        campoUsuario = new JTextField(20);
        gbc.gridx = 1;
        painelCentral.add(campoUsuario, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCentral.add(lblSenha, gbc);

        campoSenha = new JPasswordField(20);
        gbc.gridx = 1;
        painelCentral.add(campoSenha, gbc);

        add(painelCentral, BorderLayout.CENTER);

        // Painel inferior (botões)
        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(new Color(30, 30, 30));

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(Color.BLACK);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.addActionListener(e -> cadastrarUsuario());

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(Color.DARK_GRAY);
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        painelInferior.add(btnVoltar);
        painelInferior.add(Box.createRigidArea(new Dimension(20,0)));
        painelInferior.add(btnCadastrar);

        add(painelInferior, BorderLayout.SOUTH);
    }

    private void cadastrarUsuario() {
        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try {
            File file = new File(ARQUIVO);
            if (!file.exists()) file.createNewFile();

            // verifica se o usuário já existe
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] dados = sc.nextLine().split(";");
                if (dados[0].equals(usuario)) {
                    JOptionPane.showMessageDialog(this, "Usuário já existe!");
                    sc.close();
                    return;
                }
            }
            sc.close();

            // adiciona novo usuário
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            pw.println(usuario + ";" + senha);
            pw.close();

            // cria pasta de transações
            File pasta = new File("usuarios");
            if (!pasta.exists()) pasta.mkdir();
            File transacoes = new File(pasta, "transacoes_" + usuario + ".txt");
            if (!transacoes.exists()) transacoes.createNewFile();

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!\nFaça login para acessar o sistema.");
            new Login().setVisible(true); // volta para login
            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário!");
        }
    }
}