package controlefinanceiro.Codigo_Completo_Modificado;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class TelaGrafico extends JFrame {

    public TelaGrafico(ResumoFinanceiro resumo) {

        setTitle("Resumo Financeiro - " + SessaoUsuario.getUsuarioLogado());
        setSize(750, 450);
        setLocationRelativeTo(null); // centraliza
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // melhor para não fechar todo app

        // Dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(resumo.totalEntradas(), "Entradas", "Resumo");
        dataset.addValue(resumo.totalSaidas(), "Saídas", "Resumo");

        // Criar gráfico
        JFreeChart chart = ChartFactory.createBarChart(
                "Resumo Financeiro",
                "Tipo",
                "Valor (R$)",
                dataset
        );

        // Estilo
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 20));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(245, 245, 245));
        plot.setRangeGridlinePaint(Color.GRAY);

        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.BOLD, 14));
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.BOLD, 14));

        // Cores das barras
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(76, 175, 80));  // verde
        renderer.setSeriesPaint(1, new Color(244, 67, 54));  // vermelho

        renderer.setItemMargin(0.30);        // espaço entre barras
        renderer.setMaximumBarWidth(0.08);   // barras mais finas

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.WHITE);
        panel.setMouseWheelEnabled(true);

        setContentPane(panel);
    }
}