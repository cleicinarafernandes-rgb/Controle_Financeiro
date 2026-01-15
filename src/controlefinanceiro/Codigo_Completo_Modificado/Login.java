package controlefinanceiro.Codigo_Completo_Modificado;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Login extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private final String ARQUIVO = "usuarios.txt";

    public Login() {
        setTitle("Login - Controle Financeiro");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Painel esquerdo
        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.setBackground(new Color(20, 20, 20));

        JLabel tituloSistema = new JLabel("Controle Financeiro Pessoal", SwingConstants.CENTER);
        tituloSistema.setFont(new Font("Segoe UI", Font.BOLD, 26));
        tituloSistema.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Organize sua vida financeira", SwingConstants.CENTER);
        subtitulo.setForeground(Color.LIGHT_GRAY);

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setBackground(new Color(20, 20, 20));
        textos.add(tituloSistema);
        textos.add(subtitulo);

        painelEsquerdo.add(textos, BorderLayout.CENTER);

        // Painel direito (login)
        JPanel painelDireito = new JPanel();
        painelDireito.setBackground(new Color(30, 30, 30));
        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));
        painelDireito.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        JLabel icone = new JLabel("👤", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        icone.setForeground(Color.WHITE);
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tituloLogin = new JLabel("LOGIN");
        tituloLogin.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tituloLogin.setForeground(Color.WHITE);
        tituloLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoUsuario.setBorder(BorderFactory.createTitledBorder("Usuário"));

        campoSenha = new JPasswordField();
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        JButton btnEntrar = new JButton("ENTRAR");
        estilizarBotaoGrande(btnEntrar);

        JButton btnCadastroLink = new JButton("Não tenho cadastro");
        estilizarBotaoLink(btnCadastroLink);

        painelDireito.add(icone);
        painelDireito.add(Box.createRigidArea(new Dimension(0, 15)));
        painelDireito.add(tituloLogin);
        painelDireito.add(Box.createRigidArea(new Dimension(0, 30)));
        painelDireito.add(campoUsuario);
        painelDireito.add(Box.createRigidArea(new Dimension(0, 15)));
        painelDireito.add(campoSenha);
        painelDireito.add(Box.createRigidArea(new Dimension(0, 25)));
        painelDireito.add(btnEntrar);
        painelDireito.add(Box.createRigidArea(new Dimension(0, 10)));
        painelDireito.add(btnCadastroLink);

        add(painelEsquerdo);
        add(painelDireito);

        // Ações
        btnEntrar.addActionListener(e -> fazerLogin());
        btnCadastroLink.addActionListener(e -> {
            new Cadastro().setVisible(true);
            dispose();
        });
    }

    private void estilizarBotaoGrande(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
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

    // LOGIN (APENAS AUTENTICA E ABRE O SISTEMA)
    private void fazerLogin() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        try (Scanner sc = new Scanner(new File(ARQUIVO))) {
            while (sc.hasNextLine()) {
                String[] dados = sc.nextLine().split(";");

                if (dados[0].equals(usuario) && dados[1].equals(senha)) {

                    // salva usuário na sessão
                    SessaoUsuario.login(usuario);

                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");

                    // abre SOMENTE o sistema principal
                    new App_Financeiro().setVisible(true);

                    dispose();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
