package controlefinanceiro.Codigo_Completo_Modificado;

import controlefinanceiro.controller.ArquivoTransacoes;
import controlefinanceiro.controller.GerenciadorFinancas;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

public class App_Financeiro extends JFrame {

    private GerenciadorFinancas gerenciador = new GerenciadorFinancas();
    private ArquivoTransacoes arquivoUtil; // inicializado com o usuário logado

    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable tabela;

    private JLabel lblSaldo = new JLabel("Saldo: R$ 0.00");
    private JTextField txtDescricao = new JTextField(12);
    private JTextField txtValor = new JTextField(6);
    private JTextField txtBusca = new JTextField(15);

    private JComboBox<String> cbTipo =
            new JComboBox<>(new String[]{"Receita", "Despesa"});

    private JComboBox<String> cbCategoria =
            new JComboBox<>(new String[]{"Fixas", "Pontuais", "Extras"});

    public App_Financeiro() {
        setTitle("Controle Financeiro Pessoal");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Inicializa o arquivo de transações do usuário logado
        String usuario = SessaoUsuario.getUsuarioLogado();
        arquivoUtil = new ArquivoTransacoes(usuario);

        // Carrega transações do usuário ao abrir o sistema
        try {
            gerenciador.setTransacoes(arquivoUtil.carregar());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar transações do usuário.");
        }

        configurarTopo();
        configurarTabela();
        configurarRodape();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //BOTÃO VOLTAR
    private JLabel criarBotaoVoltar() {
        ImageIcon img = new ImageIcon(
                "src/controlefinanceiro/Codigo_Completo_Modificado/img/bntvolta.png"
        );
        Image imgEscalada = img.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        JLabel btnVoltar = new JLabel(new ImageIcon(imgEscalada));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setToolTipText("Voltar para o login");

        btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });

        return btnVoltar;
    }

    //TOPO
    private void configurarTopo() {
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));

        // Painel seta
        JPanel painelSeta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSeta.setMaximumSize(new Dimension(900, 50));
        painelSeta.add(criarBotaoVoltar());
        painelTopo.add(painelSeta);
        painelTopo.add(Box.createVerticalStrut(10));

        // Painel adicionar transação
        JPanel painelAdd = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelAdd.setBorder(BorderFactory.createTitledBorder("Nova Transação"));

        painelAdd.add(new JLabel("Descrição:"));
        painelAdd.add(txtDescricao);

        painelAdd.add(new JLabel("Valor:"));
        painelAdd.add(txtValor);

        painelAdd.add(new JLabel("Categoria:"));
        painelAdd.add(cbCategoria);

        painelAdd.add(new JLabel("Tipo:"));
        painelAdd.add(cbTipo);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> acaoAdicionar());
        painelAdd.add(btnAdicionar);

        painelTopo.add(painelAdd);

        // Painel busca
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBusca.add(new JLabel("Buscar:"));
        pnlBusca.add(txtBusca);

        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtro = txtBusca.getText();
                sorter.setRowFilter(filtro.isEmpty() ? null : RowFilter.regexFilter("(?i)" + filtro));
            }
        });

        painelTopo.add(pnlBusca);

        add(painelTopo, BorderLayout.NORTH);
    }

    //TABELA
    private void configurarTabela() {
        String[] colunas = {"Tipo", "Descrição", "Valor", "Categoria", "Data"};

        modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modelo);
        sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean s, boolean f, int r, int c) {

                Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
                int modelRow = t.convertRowIndexToModel(r);
                Object tipoObj = t.getModel().getValueAt(modelRow, 0);

                if (tipoObj != null && !s) {
                    comp.setForeground(tipoObj.toString().equalsIgnoreCase("Receita") ?
                            new Color(0, 128, 0) : Color.RED);
                }
                return comp;
            }
        });

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    //RODAPÉ
    private void configurarRodape() {
        JPanel pnlSul = new JPanel(new BorderLayout(10, 10));
        pnlSul.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        lblSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        pnlSul.add(lblSaldo, BorderLayout.WEST);

        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnRem = new JButton("Remover");
        JButton btnSal = new JButton("Salvar");
        JButton btnCar = new JButton("Carregar");
        JButton btnResumo = new JButton("Resumo do Mês");

        btnRem.addActionListener(e -> acaoRemover());
        btnSal.addActionListener(e -> acaoSalvar());
        btnCar.addActionListener(e -> acaoCarregar());
        btnResumo.addActionListener(e -> acaoResumoMes());

        pnlBotoes.add(btnRem);
        pnlBotoes.add(btnCar);
        pnlBotoes.add(btnSal);
        pnlBotoes.add(btnResumo);

        pnlSul.add(pnlBotoes, BorderLayout.EAST);
        add(pnlSul, BorderLayout.SOUTH);
    }

    //AÇÕES 
    private void acaoAdicionar() {
        try {
            if (txtDescricao.getText().trim().isEmpty() || txtValor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha descrição e valor.");
                return;
            }

            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            String desc = txtDescricao.getText();
            String cat = (String) cbCategoria.getSelectedItem();
            LocalDate data = LocalDate.now();

            if (cbTipo.getSelectedItem().equals("Receita")) {
                gerenciador.adicionarReceita(valor, desc, data, cat);
            } else {
                gerenciador.adicionarDespesa(valor, desc, data, cat);
            }

            atualizar();
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void acaoRemover() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int index = tabela.convertRowIndexToModel(row);
            gerenciador.getTransacoes().remove(index);
            atualizar();
        }
    }

    private void acaoSalvar() {
        try {
            arquivoUtil.salvar(gerenciador.getTransacoes());
            JOptionPane.showMessageDialog(this, "Dados salvos!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }
    }

    private void acaoCarregar() {
        try {
            gerenciador.setTransacoes(arquivoUtil.carregar());
            atualizar();
            JOptionPane.showMessageDialog(this, "Dados carregados!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar.");
        }
    }

    private void acaoResumoMes() {
        String mesStr = JOptionPane.showInputDialog("Digite o mês (1 a 12):");
        String anoStr = JOptionPane.showInputDialog("Digite o ano (ex: 2026):");

        if (mesStr == null || anoStr == null) return;

        int mes = Integer.parseInt(mesStr);
        int ano = Integer.parseInt(anoStr);

        var lista = gerenciador.filtrarPorMes(mes, ano);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma transação nesse mês.");
            return;
        }

        double totalReceitas = 0;
        double totalDespesas = 0;

        for (var t : lista) {
            if (t.getTipo().equals("Receita")) totalReceitas += t.getValor();
            else totalDespesas += t.getValor();
        }

        double saldo = totalReceitas - totalDespesas;

        JOptionPane.showMessageDialog(this,
                "Resumo do mês:\n\n" +
                        "Receitas: R$ " + totalReceitas +
                        "\nDespesas: R$ " + totalDespesas +
                        "\nSaldo: R$ " + saldo
        );
    }

    private void atualizar() {
        modelo.setRowCount(0);

        for (var t : gerenciador.getTransacoes()) {
            modelo.addRow(new Object[]{
                    t.getTipo(),
                    t.getDescricao(),
                    String.format("%.2f", t.getValor()),
                    t.getCategoria(),
                    t.getData()
            });
        }

        double saldo = gerenciador.calcularSaldo();
        lblSaldo.setText(String.format("Saldo Atual: R$ %.2f", saldo));
        lblSaldo.setForeground(saldo >= 0 ? new Color(0, 128, 0) : Color.RED);
    }

    private void limparCampos() {
        txtDescricao.setText("");
        txtValor.setText("");
        cbCategoria.setSelectedIndex(0);
    }
}