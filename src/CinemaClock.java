import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CinemaClock extends JLabel implements Runnable {

    public CinemaClock() {
        setHorizontalAlignment(JLabel.CENTER);
        setForeground(Color.YELLOW);
        setFont(new Font("Arial", Font.PLAIN, 18));
        updateTime();
        Thread clockThread = new Thread(this);
        clockThread.start();
    }

    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        setText(dateFormat.format(new Date()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                updateTime();
                Thread.sleep(1000); // Update every second
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
