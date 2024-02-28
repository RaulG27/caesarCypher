import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;


public class Caesar {

    private static int key;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Caesar Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load image from URL
                try {
                    URL imageURL = new URL("https://cdn.mos.cms.futurecdn.net/BiNbcY5fXy9Lra47jqHKGK-1200-80.jpg");
                    Image image = ImageIO.read(imageURL);
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        frame.add(panel);

        //placing components
        placeComponents(panel);

        frame.setVisible(true);
    
    }
    

    private static void setURLAsBackground(String imageUrl, JLabel label) {
        try {
            URL url = new URL(imageUrl);
            Image backgroundImage = ImageIO.read(url);
            label.setIcon(new ImageIcon(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        gbc.insets = new Insets(5, 5, 5, 5);


        // Title
        JLabel titleLabel = new JLabel("<html><em><b>Caesar Cipher</b></em></html>");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        
        panel.add(titleLabel, gbc);

        // Labels and Text Fields
        JLabel keyLabel = new JLabel("<html><b>Enter the key: </b></html>");
        keyLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        keyLabel.setForeground(Color.DARK_GRAY); // Set font color

        panel.add(keyLabel, gbc);

        JPasswordField keyText = new JPasswordField(20);
        panel.add(keyText, gbc);

        JLabel plaintextLabel = new JLabel("<html><b>Enter plaintext: </html></b>");
        plaintextLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        plaintextLabel.setForeground(Color.WHITE); // Set font color

        panel.add(plaintextLabel, gbc);

        JPasswordField plaintextText = new JPasswordField(20);
        panel.add(plaintextText, gbc);

        // Buttons
        JButton encryptButton = new JButton("Encrypt");
        panel.add(encryptButton, gbc);

        JButton decryptButton = new JButton("Decrypt");
        panel.add(decryptButton, gbc);

        // Text and Labels for Cipher Text
        JLabel cipherTextLabel = new JLabel("<html><em><b>Encrypted alphanumeric value: </html></em></b>");
        cipherTextLabel.setForeground(Color.BLACK); // Set font color
        cipherTextLabel.setBackground(Color.WHITE);
        cipherTextLabel.setOpaque(true);

        panel.add(cipherTextLabel, gbc);
        
        // Cipher TextLabel
        JTextField ciphertextArea = new JTextField();
        ciphertextArea.setBounds(10, 110, 360, 40);
        ciphertextArea.setColumns(20);  // Set the preferred number of columns
        ciphertextArea.setForeground(Color.BLACK); // Set font color
        ciphertextArea.setBackground(Color.WHITE);
        ciphertextArea.setOpaque(true);

        panel.add(ciphertextArea, gbc);
        
     

        // Dropdown for Decryption Results
        JComboBox<String> decryptionResultsDropdown = new JComboBox<>();
        panel.add(decryptionResultsDropdown, gbc);

        // Action Listeners
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    key = Integer.parseInt(keyText.getText());
                    char[] plaintextChars = plaintextText.getPassword();
                    String plaintext = new String(plaintextChars);
                    String ciphertext = encryptText(plaintext, key);
                    ciphertextArea.setText(ciphertext);

                    // Clear previous items in the dropdown
                    decryptionResultsDropdown.removeAllItems();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Key must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertext = ciphertextArea.getText();
                decryptAndDisplay(decryptionResultsDropdown, ciphertext);
            }
        });
    }

    private static void decryptAndDisplay(JComboBox<String> dropdown, String ciphertext) {
        String decryptText = "Decrypted Text: "  + decryptText(ciphertext, key);
        String decryptedKey = "Decrypted with Key " + key;

        // Add both results to the dropdown
        dropdown.addItem(decryptedKey);
        dropdown.addItem(decryptText);
    }

    private static String encryptText(String plaintext, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);

            if (Character.isLetterOrDigit(currentChar)) {
                char encryptedChar = encryptChar(currentChar, key);
                result.append(encryptedChar);
            } else if (Character.isWhitespace(currentChar)) {
                result.append(currentChar);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    private static String decryptText(String ciphertext, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);

            if (Character.isLetterOrDigit(currentChar)) {
                char decryptedChar = decryptChar(currentChar, key);
                result.append(decryptedChar);
            } else if (Character.isWhitespace(currentChar)) {
                result.append(currentChar);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    private static char encryptChar(char c, int key) {
        int alphabetSize = 26;

        if (Character.isUpperCase(c)) {
            return (char) (((c - 'A' + key) % alphabetSize + alphabetSize) % alphabetSize + 'A');
        } else if (Character.isLowerCase(c)) {
            return (char) (((c - 'a' + key) % alphabetSize + alphabetSize) % alphabetSize + 'a');
        } else if (Character.isDigit(c)) {
            return (char) (((c - '0' + key) % 10 + 10) % 10 + '0');
        }

        // Non-alphanumeric characters remain unchanged
        return c;
    }

    private static char decryptChar(char c, int key) {
        // Decryption is essentially the same as encryption, but with a negative key
        return encryptChar(c, -key);

}
}