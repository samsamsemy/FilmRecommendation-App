import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FlyingRocket extends JPanel {

    private static final int ROCKET_COUNT = 50;
    private final Timer timer = new Timer();
    private final Rocket[] rockets;

    public FlyingRocket(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        rockets = new Rocket[ROCKET_COUNT];
        for (int i = 0; i < ROCKET_COUNT; i++) {
            rockets[i] = new Rocket(width, height);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateRocketPositions();
                repaint();
            }
        }, 0, 100);
    }

    private void updateRocketPositions() {
        for (Rocket rocket : rockets) {
            rocket.move();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Rocket rocket : rockets) {
            rocket.draw(g);
        }
    }

    private class Rocket {
        private int x;
        private int y;
        private int speed;
        private Color color;
        private int maxX;
        private int maxY;

        public Rocket(int maxX, int maxY) {
            this.maxX = maxX;
            this.maxY = maxY;
            Random random = new Random();
            x = random.nextInt(maxX);
            y = random.nextInt(maxY);
            speed = random.nextInt(5) + 1;
            color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        public void move() {
            Random random = new Random();
            x += speed;
            if (x > maxX) {
                x = 0;
                y = random.nextInt(maxY);
            }
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, 10, 5);
        }
    }
}
