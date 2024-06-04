import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.io.File;

public class FilmRecommendationApp extends JFrame {

    private final String[] superheroNames = {"Captain America", "Captain Marvel", "Hulk", "Loki", "IronMan"};
    private final String[] redImageFileNames = {"captainAmerica.jpg", "captainMarvel.jpg", "hulk.jpg", "loki.jpg", "ironMan.jpg"};
    private final String[] blueImageFileNames = {"Thor.jpg", "Titanic.jpg", "poco.png", "Ninjago.jpg", "Oppenheimer.jpeg"};

    private final Map<String, String> filmLinks = new HashMap<>();

    private final Timer timer = new Timer();
    private final JLabel advertisementLabel = new JLabel();

    public FilmRecommendationApp() {
        filmLinks.put("Captain America", "https://tv5.idlixplus.net/movie/captain-america-the-first-avenger-2011/");
        filmLinks.put("Captain Marvel", "https://tv5.idlixplus.net/movie/captain-marvel-2019/");
        filmLinks.put("Hulk", "https://tv5.idlixplus.net/movie/the-incredible-hulk-2008/");
        filmLinks.put("Loki", "https://tv5.idlixplus.net/season/loki-season-1/");
        filmLinks.put("IronMan", "https://tv5.idlixplus.net/movie/iron-man-2008/");

        filmLinks.put("Thor", "https://tv5.idlixplus.net/movie/thor-2011/");
        filmLinks.put("Titanic", "https://tv5.idlixplus.net/movie/titanic-1997/");
        filmLinks.put("Ninjago", "https://tv5.idlixplus.net/tvseries/lego-ninjago-dragons-rising-2023/");
        filmLinks.put("Oppenheimer", "https://tv5.idlixplus.net/movie/oppenheimer-2023/");

        setTitle("Film Recommendation App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                FlyingRocket flyingRocket = new FlyingRocket(getWidth(), getHeight());
                flyingRocket.paintComponent(g);
            }
        };
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel adPanel = createAdvertisementPanel();
        mainPanel.add(adPanel, gbc);
        gbc.gridy++;

        JLabel welcomeLabel = new JLabel("Selamat Datang!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(Color.YELLOW);
        setComponentFontSize(welcomeLabel, 24);
        mainPanel.add(welcomeLabel, gbc);
        gbc.gridy++;

        JLabel additionalLabel = new JLabel("Nikmati rekomendasi film terbaik kami!");
        additionalLabel.setHorizontalAlignment(JLabel.CENTER);
        additionalLabel.setForeground(Color.YELLOW);
        setComponentFontSize(additionalLabel, 16);
        mainPanel.add(additionalLabel, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(25, 0, 40, 0);
        mainPanel.add(additionalLabel, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(150, 0, 0, 0);

        JPanel recommendationPanel = createFilmPanel(1000, 150, Color.RED, Color.YELLOW, superheroNames, redImageFileNames);
        recommendationPanel.setBackground(Color.BLACK);

        JPanel todayFilmPanel = createFilmPanel(1000, 150, Color.BLUE, Color.YELLOW, new String[]{"Thor", "Titanic", "Poco", "Ninjago", "Oppenheimer"}, blueImageFileNames);
        todayFilmPanel.setBackground(Color.BLACK);

        JLabel recommendationTitleLabel = new JLabel("Rekomendasi Film");
        recommendationTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        recommendationTitleLabel.setForeground(Color.YELLOW);
        setComponentFontSize(recommendationTitleLabel, 18);
        mainPanel.add(recommendationTitleLabel, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(20, 0, 20, 0);

        mainPanel.add(recommendationPanel, gbc);
        gbc.gridy++;

        JLabel todayFilmTitleLabel = new JLabel("Film Hari Ini");
        todayFilmTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        todayFilmTitleLabel.setForeground(Color.YELLOW);
        setComponentFontSize(todayFilmTitleLabel, 18);
        mainPanel.add(todayFilmTitleLabel, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(0, 0, 40, 0);

        mainPanel.add(todayFilmPanel, gbc);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JPanel clockPanel = createClockPanel();
        mainPanel.add(clockPanel, gbc);

        FlyingRocket flyingRocket = new FlyingRocket(1200, 800);
        setLayout(new BorderLayout());
        add(flyingRocket, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createAdvertisementPanel() {
        JPanel adPanel = new JPanel();
        adPanel.setBackground(Color.BLACK);
        advertisementLabel.setHorizontalAlignment(JLabel.CENTER);
        advertisementLabel.setForeground(Color.WHITE);
        setComponentFontSize(advertisementLabel, 16);
        adPanel.add(advertisementLabel);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateAdvertisement();
            }
        }, 0, getRandomInterval());

        return adPanel;
    }

    private void updateAdvertisement() {
        Random random = new Random();
        int index = random.nextInt(superheroNames.length);
        String filmTitle = superheroNames[index];
        SwingUtilities.invokeLater(() -> advertisementLabel.setText(filmTitle));
    }

    private int getRandomInterval() {
        Random random = new Random();
        return (random.nextInt(3) + 3) * 1000;
    }

    private void setComponentFontSize(JComponent component, int size) {
        Font currentFont = component.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(currentFont.getAttributes());
        attributes.put(TextAttribute.SIZE, size);
        component.setFont(currentFont.deriveFont(attributes));
    }

    private JPanel createFilmPanel(int width, int height, Color borderColor, Color textColor, String[] superheroNames, String[] imageFileNames) {
        JPanel filmPanel = new JPanel();
        filmPanel.setLayout(new GridLayout(1, 5, 10, 10));
        filmPanel.setBackground(Color.BLACK);
        filmPanel.setPreferredSize(new Dimension(width, height));

        for (int i = 0; i < superheroNames.length; i++) {
            JPanel filmBox = new JPanel();
            filmBox.setLayout(new BorderLayout());

            JButton filmButton = createFilmButton(superheroNames[i], imageFileNames[i]);
            filmButton.setHorizontalAlignment(JButton.CENTER);
            setComponentFontSize(filmButton, 12);
            filmButton.setBorder(BorderFactory.createLineBorder(borderColor, 2));
            filmButton.setBackground(Color.BLACK);
            filmButton.setForeground(textColor);

            filmBox.add(filmButton, BorderLayout.CENTER);
            filmPanel.add(filmBox);
        }

        return filmPanel;
    }

    private JButton createFilmButton(String filmName, String imageFileName) {
        JButton filmButton = new JButton(filmName);

        filmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String link = filmLinks.get(filmName);
                if (link != null) {
                    openLink(link);
                    generateReceipt(filmName, link);
                    scheduleNotification();  // Mengganti showNotification langsung dengan scheduleNotification
                } else {
                    openLink("https://tv5.idlixplus.net/");
                }
            }
        });

        ImageIcon icon = new ImageIcon("img/" + imageFileName);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        filmButton.setIcon(new ImageIcon(scaledImage));

        return filmButton;
    }

    private void openLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createClockPanel() {
        JPanel clockPanel = new JPanel();
        CinemaClock cinemaClock = new CinemaClock();
        clockPanel.add(cinemaClock);
        clockPanel.setBackground(Color.BLACK);
        return clockPanel;
    }

    private void generateReceipt(String filmName, String link) {
        Receipt receipt = new Receipt(filmName, link);
        receipt.printReceipt();
    }

    private void showNotification() {
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());

        JLabel notificationLabel = new JLabel("Selamat menonton dan silakan cek Receipt-mu di perangkat kamu ya!!");
        notificationLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton okeButton = new JButton("Oke");
        okeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Window) SwingUtilities.getRoot(notificationPanel)).dispose();
            }
        });

        notificationPanel.add(notificationLabel, BorderLayout.CENTER);
        notificationPanel.add(okeButton, BorderLayout.SOUTH);

        JOptionPane.showOptionDialog(
                this,
                notificationPanel,
                "Notifikasi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{},
                null
        );
    }

    private void scheduleNotification() {
        Timer notificationTimer = new Timer();
        notificationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                showNotification();
            }
        }, 5000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FilmRecommendationApp());
    }
}
