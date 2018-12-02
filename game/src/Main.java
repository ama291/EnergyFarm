import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Setup UI
        JFrame menu = new JFrame("EnergyFarm Menu");
        menu.setSize(1000,750);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setResizable(false);
        Container contentPane = menu.getContentPane();
        JLabel actualInstructions = new JLabel("<html> <p style=\"padding-left: 260; text-align: center;\"> <br><br><br><br><br> *** INSTRUCTIONS *** <br><br><br> Press I to view your current inventory. <br> Walk onto the store or the market, and press Enter to bring up the interface.<br> Press Space to advance time. <br> Select a button below to begin. <br><br><br><br></p></html>");
        actualInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Create Levels
        JButton l1Button  = new JButton("Novice");
        l1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        l1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                Level1 game = new Level1();
                game.start();
            }
        });

        JButton l2Button  = new JButton("Intermediate");
        l2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                Level2 game = new Level2();
                game.start();
            }
        });

        JButton l3Button  = new JButton("Expert");
        l3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        l3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                Level3 game = new Level3();
                game.start();
            }
        });

        // Add ui to content pane
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(actualInstructions);
        contentPane.add(l1Button);
        contentPane.add(l2Button);
        contentPane.add(l3Button);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
