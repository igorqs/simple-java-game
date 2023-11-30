/**
  * Autor: Igor Quadros Silva
  * Matricula: 201610519
  * Inicio: 06 de marco de 2021
  * Ultima Alteracao: 07 de marco de 2021
  * Nome: Press SPACE to shoot and arrows to move
  */

package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ListIterator;
import javax.swing.JPanel;

import game.sprite.Background;
import game.sprite.Spaceship;
import game.sprite.Missile;
import game.sprite.Asteroid;

public class Board extends JPanel {
  public static final boolean SHOW_HITBOX = false;
  public static final int BOARD_WIDTH = 400;
  public static final int BOARD_HEIGHT = 500;
  public static final int DELAY = 15;
  private static final float DIFFICULT_OFFSET = 0.0008f;
  private Thread thread;
  private List<Background> backgrounds;
  private List<Asteroid> asteroids;
  private Spaceship spaceship;
  private Random random;
  private static float difficult;
  private boolean ingame;
  private GameAudio gameAudio;

  public Board() {
    gameAudio = new GameAudio();
    initBoard();
  }

  private void initBoard() {
    addKeyListener(new TAdapter());
    setBackground(Color.BLACK);
    setFocusable(true);
    setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

    random = new Random();
    difficult = 1;
    ingame = true;

    initBackgrounds();
    initSpaceship();
    initAsteroids();

    if (thread == null) {
      thread = new Thread(new GameCycle());
      thread.start();
    }
  }

  private void initBackgrounds() {
    backgrounds = new ArrayList<>();

    for (Background.Layer layer : Background.Layer.values()) {
      backgrounds.add(new Background(layer));
      backgrounds.add(new Background(layer, -BOARD_HEIGHT));
    }
  }

  private void initSpaceship() {
    spaceship = new Spaceship();
  }

  private void initAsteroids() {
    asteroids = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (ingame) {
      doDrawing(g);
    } else {
      drawGameOver(g);
    }

    Toolkit.getDefaultToolkit().sync();
  }

  private void drawGameOver(Graphics g) {
    String message = "Game Over";
    Font small = new Font("Helvetica", Font.BOLD, 14);
    FontMetrics fontMetrics = getFontMetrics(small);

    g.setColor(Color.WHITE);
    g.setFont(small);
    g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2, BOARD_HEIGHT / 2);

    message = "Press Enter to play again";
    g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
        BOARD_HEIGHT / 2 + 14);
  }

  private void doDrawing(Graphics g) {
    drawBackgrounds(g);
    drawSpaceship(g);
    drawMissiles(g);
    drawAsteroids(g);
  }

  private void drawBackgrounds(Graphics g) {
    for (Background background : backgrounds) {
      g.drawImage(background.getImage(), (int)background.getX(), (int)background.getY(), this);
    }
  }

  private void drawSpaceship(Graphics g) {
      g.drawImage(spaceship.getImage(), (int)spaceship.getX(), (int)spaceship.getY(), this);
      if (SHOW_HITBOX) {
        g.setColor(Color.YELLOW);
        Rectangle hitbox = spaceship.getHitbox();
        g.drawRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
      }
  }

  private void drawMissiles(Graphics g) {
    for (Missile missile : spaceship.getMissiles()) {
      g.setColor(Color.YELLOW);
      g.fillRect((int)missile.getX(), (int)missile.getY(), Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT);
    }
  }

  private void drawAsteroids(Graphics g) {
    for (Asteroid asteroid : asteroids) {
      g.drawImage(asteroid.getImage(), (int)asteroid.getX(), (int)asteroid.getY(), this);
      if (SHOW_HITBOX) {
        g.setColor(Color.YELLOW);
        Rectangle hitbox = asteroid.getHitbox();
        g.drawRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
      }
    }
  }

  private void update() {
    updateBackgrounds();
    updateSpaceship();
    updateMissiles();
    updateAsteroids();

    checkCollisions();

    difficult += DIFFICULT_OFFSET;
  }

  private void updateBackgrounds() {
    for (Background background : backgrounds) {
      background.move();
    }
  }

  private void updateSpaceship() {
    spaceship.move();
  }

  private void updateMissiles() {
    ListIterator<Missile> it = spaceship.getMissiles().listIterator();
    while (it.hasNext()) {
      Missile missile = it.next();
      if (missile.isVisible()) {
        missile.move();
      } else {
        it.remove();
      }
    }
  }

  private void updateAsteroids() {
    boolean any = false;
    ListIterator<Asteroid> it = asteroids.listIterator();
    while (it.hasNext()) {
      Asteroid asteroid = it.next();
      if (asteroid.isVisible()) {
        asteroid.move();
        if (asteroid.getY() < 0) {
          any = true;
        }
      } else {
        it.remove();
      }
    }

    if (any == false) {
      int index = random.nextInt(Asteroid.Size.values().length);
      Asteroid.Size size = Asteroid.Size.values()[index];

      int x = random.nextInt(BOARD_WIDTH - Spaceship.SPACESHIP_WIDTH - size.getDimension()) 
        + Spaceship.SPACESHIP_WIDTH / 2;

      asteroids.add(new Asteroid(size, x));
    }
  }

  public void checkCollisions() {

    Rectangle hitbox = spaceship.getHitbox();

    for (Asteroid asteroid : asteroids) {

      Rectangle asteroidBounds = asteroid.getHitbox();

      if (hitbox.intersects(asteroidBounds)) {

        spaceship.setVisible(false);
        asteroid.setVisible(false);
        ingame = false;
      }
    }

    for (Asteroid asteroid : asteroids) {
      if (asteroid.getY() > BOARD_HEIGHT) {
        ingame = false;
      }
    }

    List<Missile> missiles = spaceship.getMissiles();

    for (Missile missile : missiles) {
      Rectangle missileHitbox = missile.getHitbox();

      for (Asteroid asteroid : asteroids) {
        Rectangle asteroidRectangle = asteroid.getHitbox();

        if (missileHitbox.intersects(asteroidRectangle)) {
          missile.setVisible(false);
          asteroid.setVisible(false);
          gameAudio.explosionSound();
        }
      }
    }
  }

  private void doGameCycle() {
    update();
    repaint();
  }

  public static float getDifficult() {
    return difficult;
  }

  private class GameCycle implements Runnable {
    @Override
    public void run() {
      long beforeTime, timeDiff, sleep;

      beforeTime = System.currentTimeMillis();

      while (true) {

        doGameCycle();

        timeDiff = System.currentTimeMillis() - beforeTime;
        sleep = DELAY - timeDiff;

        if (sleep < 0) {
          System.err.println("FPS drop");
          sleep = 2;
        }

        try {
          Thread.sleep(sleep);
        } catch (InterruptedException e) {
          String message = String.format("Thread interrupted: %s", e.getMessage());

          System.err.println(message);
        }

        beforeTime = System.currentTimeMillis();
      }
    }
  }

  private class TAdapter extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
      spaceship.keyReleased(e);

      int key = e.getKeyCode();

      if (key == KeyEvent.VK_ENTER && ingame == false) {
        initBoard();
      }

      if (key == KeyEvent.VK_SPACE && ingame) {
        gameAudio.fireSound();
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {
      spaceship.keyPressed(e);
    }
  }
}
