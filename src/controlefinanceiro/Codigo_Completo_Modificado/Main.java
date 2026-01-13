package controlefinanceiro.Codigo_Completo_Modificado;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { 
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Login login = new Login(); // abre o login primeiro
            login.setVisible(true);
        });
    }
}
