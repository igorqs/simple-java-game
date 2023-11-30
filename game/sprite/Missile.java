/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

package game.sprite;

import java.awt.Rectangle;

import game.Board;

public class Missile extends Sprite {
  private static final int Y_OFFSET = -8;
  public static final int MISSILE_WIDTH = 4;
  public static final int MISSILE_HEIGHT = 15;

  public Missile(float x, float y) {
    super(x, y);
  }

  public void move() {
    setY(getY() + Y_OFFSET);

    if (getY() < 0) {
      setVisible(false);
    }
  }

  public Rectangle getHitbox() {
    return new Rectangle( (int)getX(), (int)getY(), MISSILE_WIDTH, MISSILE_HEIGHT);
  }
}

