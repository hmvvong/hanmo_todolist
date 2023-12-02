import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class RoundedButton extends JButton {
    private static final int ARC_WIDTH = 15;
    private static final int ARC_HEIGHT = 15;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, ARC_WIDTH, ARC_HEIGHT));

        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textX = (width - fm.stringWidth(getText())) / 2;
        int textY = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }
}

public class ToDoListApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JFrame verificationFrame;

    public ToDoListApp() {
        super("ToDoList App");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel landingPage = createLandingPage();
        cardPanel.add(landingPage, "landingPage");

        JPanel loginPage = createLoginPage();
        cardPanel.add(loginPage, "loginPage");

        JPanel signUpPage = createSignUpPage();
        cardPanel.add(signUpPage, "signUpPage");

        createVerificationFrame();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        cardPanel.setBackground(Color.GRAY);

        cardLayout.show(cardPanel, "landingPage");
        add(cardPanel);

        landingPage.setBackground(Color.GRAY);
        loginPage.setBackground(Color.GRAY);
        signUpPage.setBackground(Color.GRAY);

        setVisible(true);
    }

    private JPanel createLandingPage() {
        JPanel landingPage = new JPanel(new GridBagLayout());

        JLabel titleLabel = new JLabel("ToDoList");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton loginButton = new RoundedButton("Log in");
        JButton signUpButton = new RoundedButton("Sign up");

        customizeButton(loginButton);
        customizeButton(signUpButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 60, 0);
        landingPage.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        landingPage.add(loginButton, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        landingPage.add(signUpButton, gbc);

        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "loginPage"));

        signUpButton.addActionListener(e -> cardLayout.show(cardPanel, "signUpPage"));

        return landingPage;
    }

    private void customizeButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private JPanel createLoginPage() {
        JPanel loginPage = new JPanel(new FlowLayout());

        Icon closeIcon = new ImageIcon(getClass().getResource("closeicon.png"));
        JButton closeButton = new JButton(closeIcon);
        customizeButton(closeButton);

        closeButton.setPreferredSize(new Dimension(20, 20));

        GridBagConstraints closeGbc = new GridBagConstraints();
        closeGbc.gridx = 0;
        closeGbc.gridy = 0;
        closeGbc.anchor = GridBagConstraints.NORTHWEST;
        closeGbc.insets = new Insets(10, 10, 0, 0);
        loginPage.add(closeButton, closeGbc);

        JLabel titleLabel = new JLabel("Log in");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel emailLabel = new JLabel("YOUR EMAIL");
        JLabel passwordLabel = new JLabel("YOUR PASSWORD");

        JTextField emailField = new JTextField(20);
        emailField.setForeground(Color.LIGHT_GRAY);
        emailField.setText("EMAIL");
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("EMAIL")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("EMAIL");
                    emailField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setText("PASSWORD");
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(passwordField.getPassword(), "PASSWORD".toCharArray())) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Arrays.equals(passwordField.getPassword(), "".toCharArray())) {
                    passwordField.setText("PASSWORD");
                    passwordField.setForeground(Color.LIGHT_GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        JButton loginButton = new RoundedButton("Log in");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        loginPage.add(titleLabel, gbc);

        gbc.gridy = 2;
        loginPage.add(emailLabel, gbc);

        gbc.gridy = 3;
        loginPage.add(emailField, gbc);

        gbc.gridy = 4;
        loginPage.add(passwordLabel, gbc);

        gbc.gridy = 5;
        loginPage.add(passwordField, gbc);

        gbc.gridy = 6;
        loginPage.add(loginButton, gbc);

        closeButton.addActionListener(e -> cardLayout.show(cardPanel, "landingPage"));

        return loginPage;
    }

    private void createVerificationFrame() {
        verificationFrame = new JFrame("Verified!");
        verificationFrame.setSize(300, 150);
        verificationFrame.setLocationRelativeTo(null);
        verificationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel verificationPanel = new JPanel(new GridBagLayout());

        JLabel titleLabel = new JLabel("Verified!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel textLabel = new JLabel("Email verification successful");

        JButton loginButton = new JButton("Log in");
        customizeButton(loginButton);

        loginButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "loginPage");
            verificationFrame.dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        verificationPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        verificationPanel.add(textLabel, gbc);

        gbc.gridy = 2;
        verificationPanel.add(loginButton, gbc);

        verificationFrame.add(verificationPanel);
    }

    private void showVerificationFrame() {
        verificationFrame.setVisible(true);
    }

    private JPanel createSignUpPage() {
        JPanel signUpPage = new JPanel(new GridBagLayout());

        Icon closeIcon = new ImageIcon(getClass().getResource("closeicon.png"));
        JButton closeButton = new JButton(closeIcon);
        customizeButton(closeButton);

        closeButton.setPreferredSize(new Dimension(20, 20));

        GridBagConstraints closeGbc = new GridBagConstraints();
        closeGbc.gridx = 0;
        closeGbc.gridy = 0;
        closeGbc.anchor = GridBagConstraints.NORTHWEST;
        closeGbc.insets = new Insets(10, 10, 0, 0);
        signUpPage.add(closeButton, closeGbc);

        JLabel titleLabel = new JLabel("Sign up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel emailLabel = new JLabel("YOUR EMAIL");
        JLabel usernameLabel = new JLabel("YOUR USERNAME");
        JLabel passwordLabel = new JLabel("YOUR PASSWORD");

        JTextField emailField = new JTextField(20);
        emailField.setForeground(Color.LIGHT_GRAY);
        emailField.setText("EMAIL");
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("EMAIL")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("EMAIL");
                    emailField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        JTextField usernameField = new JTextField(20);
        usernameField.setForeground(Color.LIGHT_GRAY);
        usernameField.setText("USERNAME");
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("USERNAME")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("USERNAME");
                    usernameField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setText("PASSWORD");
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(passwordField.getPassword(), "PASSWORD".toCharArray())) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Arrays.equals(passwordField.getPassword(), "".toCharArray())) {
                    passwordField.setText("PASSWORD");
                    passwordField.setForeground(Color.LIGHT_GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        JButton signUpButton = new RoundedButton("Sign up");

        signUpButton.addActionListener(e -> {
            showVerificationFrame();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        signUpPage.add(titleLabel, gbc);

        gbc.gridy = 2;
        signUpPage.add(emailLabel, gbc);

        gbc.gridy = 3;
        signUpPage.add(emailField, gbc);

        gbc.gridy = 4;
        signUpPage.add(usernameLabel, gbc);

        gbc.gridy = 5;
        signUpPage.add(usernameField, gbc);

        gbc.gridy = 6;
        signUpPage.add(passwordLabel, gbc);

        gbc.gridy = 7;
        signUpPage.add(passwordField, gbc);

        gbc.gridy = 8;
        signUpPage.add(signUpButton, gbc);

        gbc.gridy = 8;
        signUpPage.add(signUpButton, gbc);

        closeButton.addActionListener(e -> cardLayout.show(cardPanel, "landingPage"));

        return signUpPage;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Button.background", Color.BLACK);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
            UIManager.put("Button.focusPainted", false);
            UIManager.put("Button.borderPainted", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new ToDoListApp());
    }
}
