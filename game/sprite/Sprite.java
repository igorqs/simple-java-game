/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

package game.sprite;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Sprite {
  private float x;
  private float y;
  private Image image;
  private boolean visible;

  public Sprite(float x, float y) {
    this.x = x;
    this.y = y;
    visible = true;
  }

  protected void loadImage(String filename) {
    ImageIcon ii = new ImageIcon(filename);
    image = ii.getImage();
  }

  public Image getImage() {
    return image;
  }

  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }
}

