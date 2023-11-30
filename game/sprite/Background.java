/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

package game.sprite;

import game.Board;

public class Background extends Sprite {
  public enum Layer {
    CLOSE(0),
    NEAR(1),
    FAR(2)
    ;

    private static final String[] FILENAMES = {
      "resources/images/parallax100.png",
      "resources/images/parallax80.png",
      "resources/images/parallax60.png",
    };

    private static final float[] SPEEDS = {
      0.1f,
      0.2f,
      0.4f
    };

    private final int index;

    Layer(int index) {
      this.index = index;
    }

    public int getIndex() {
      return this.index;
    }

    public float getSpeed() {
      return SPEEDS[this.index];
    }

    public String getFilename() {
      return FILENAMES[this.index];
    }
  }

  private static final int INITIAL_X = 0;
  private static final int INITIAL_Y = 0;
  private static final int X_OFFSET = -35;

  private Layer layer;

  public Background(Layer layer, int y) {
    super(INITIAL_X + X_OFFSET * layer.getIndex(), y);

    this.layer = layer;

    initBackground();
  }

  public Background(Layer layer) {
    super(INITIAL_X + X_OFFSET * layer.getIndex(), INITIAL_Y);

    this.layer = layer;

    initBackground();
  }

  private void initBackground() {
    loadImage(layer.getFilename());
  }

  public void move() {
    setY(getY() + layer.getSpeed());

    if (getY() >= Board.BOARD_HEIGHT) {
      setY(-Board.BOARD_HEIGHT);
    }
  }
}

