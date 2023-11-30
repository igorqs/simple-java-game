/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

import java.awt.EventQueue;
import javax.swing.JFrame;

import game.Board;

public class Principal extends JFrame {
  private static final String APP_TITLE = "Press SPACE to shoot and arrows to move";

  public Principal() {
    initUI();
  }

  private void initUI() {
    add(new Board());

    setResizable(false);
    pack();

    setTitle(APP_TITLE);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      Principal app = new Principal();
      app.setVisible(true);
    });
  }
}

