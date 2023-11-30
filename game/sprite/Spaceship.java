/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

package game.sprite;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import game.Board;

public class Spaceship extends Sprite {
  private static final String FILENAME = "resources/images/spaceship.png";
  public static final int HITBOX_X_OFFSET = 18;
  public static final int HITBOX_Y_OFFSET = 5;
  public static final int HITBOX_WIDTH = 44;
  public static final int HITBOX_HEIGHT = 70;
  public static final int SPACESHIP_WIDTH = 80;
  public static final int SPACESHIP_HEIGHT = 80;
  private static final int X_OFFSET = 3;
  private static final int Y_OFFSET = 3;
  private int dx;
  private int dy;
  private List<Missile> missiles;
  private boolean canFire;

  public Spaceship() {
    super(Board.BOARD_WIDTH / 2 - SPACESHIP_WIDTH / 2, Board.BOARD_HEIGHT - SPACESHIP_HEIGHT / 2);

    initSpaceship();
  }

  private void initSpaceship() {
    missiles = new ArrayList<>();
    canFire = true;

    loadImage(FILENAME);
  }

  public void move() {
    setX(getX() + dx);
    setY(getY() + dy);

    if (getX() < 0) {
      setX(0);
    } else if (getX() > Board.BOARD_WIDTH - SPACESHIP_WIDTH) {
      setX(Board.BOARD_WIDTH - SPACESHIP_WIDTH);
    }

    if (getY() < 0) {
      setY(0);
    } else if (getY() > Board.BOARD_HEIGHT - SPACESHIP_HEIGHT) {
      setY(Board.BOARD_HEIGHT - SPACESHIP_HEIGHT);
    }
  }

  public List<Missile> getMissiles() {
    return missiles;
  }

  public void fire() {
    if (canFire) {
      //TODO: Only add inside game loop
      missiles.add(new Missile(getX() + SPACESHIP_WIDTH / 2 - Missile.MISSILE_WIDTH / 2, getY()));
    }
    canFire = false;
  }

  public void releaseGun() {
    canFire = true;
  }

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_SPACE) {
      fire();
    }

    if (key == KeyEvent.VK_LEFT) {
      dx = -X_OFFSET;
    }

    if (key == KeyEvent.VK_RIGHT) {
      dx = X_OFFSET;
    }

    if (key == KeyEvent.VK_UP) {
      dy = -Y_OFFSET;
    }

    if (key == KeyEvent.VK_DOWN) {
      dy = Y_OFFSET;
    }
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_SPACE) {
      releaseGun();
    }

    if (key == KeyEvent.VK_LEFT && dx == -X_OFFSET) {
      dx = 0;
    }

    if (key == KeyEvent.VK_RIGHT && dx == X_OFFSET) {
      dx = 0;
    }

    if (key == KeyEvent.VK_UP && dy == -Y_OFFSET) {
      dy = 0;
    }

    if (key == KeyEvent.VK_DOWN && dy == Y_OFFSET) {
      dy = 0;
    }
  }

  public Rectangle getHitbox() {
    return new Rectangle(
        (int)getX() + HITBOX_X_OFFSET,
        (int)getY() + HITBOX_Y_OFFSET,
        HITBOX_WIDTH,
        HITBOX_HEIGHT
        );
  }
}

