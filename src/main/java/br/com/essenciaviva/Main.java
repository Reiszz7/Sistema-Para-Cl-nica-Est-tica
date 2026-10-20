package br.com.essenciaviva;
import br.com.essenciaviva.ui.LoginFrame;
import javax.swing.SwingUtilities;
public class Main { public static void main(String[] args) { SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true)); } }
