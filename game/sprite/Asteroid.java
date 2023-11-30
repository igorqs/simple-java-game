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

public class Asteroid extends Sprite {
  public enum Size {
    LARGE(0),
    MEDIUM(1),
    SMALL(2)
    ;

    private static final String[] FILENAMES = {
      "resources/images/asteroid_large.png",
      "resources/images/asteroid_medium.png",
      "resources/images/asteroid_small.png",
    };

    private static final int[] DIMENSIONS = {
      128, 64, 32
    };

    private static final int[] HITBOX_X_OFFSETS = {
      24, 7, 2
    };

    private static final int[] HITBOX_Y_OFFSETS = {
      14, 7, 2
    };

    private static final int[] HITBOX_WIDTH = {
      75, 50, 28
    };

    private static final int[] HITBOX_HEIGHT = {
      100, 50, 28
    };

    private final int index;

    Size(int index) {
      this.index = index;
    }

    public int getDimension() {
      return DIMENSIONS[this.index];
    }

    public String getFilename() {
      return FILENAMES[this.index];
    }

    public int getHitboxXOffset() {
      return HITBOX_X_OFFSETS[this.index];
    }

    public int getHitboxYOffset() {
      return HITBOX_Y_OFFSETS[this.index];
    }

    public int getHitboxWidth() {
      return HITBOX_WIDTH[this.index];
    }

    public int getHitboxHeight() {
      return HITBOX_HEIGHT[this.index];
    }
  }

  private Size size;

  public Asteroid(Size size, int x) {
    super(x, -size.getDimension());

    this.size = size;

    initAsteroid();
  }

  private void initAsteroid() {
    loadImage(size.getFilename());
  }

  public void move() {
    setY(getY() + Board.getDifficult());
  }

  public Rectangle getHitbox() {
    return new Rectangle(
        (int)getX() + size.getHitboxXOffset(),
        (int)getY() + size.getHitboxYOffset(),
        size.getHitboxWidth(),
        size.getHitboxHeight()
        );
  }
}

