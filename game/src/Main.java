import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame menu = new JFrame("EnergyFarm Menu");
        menu.setSize(1000,750);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setResizable(false);
        Container contentPane = menu.getContentPane();
        JLabel instructions = new JLabel("Instructions:");
        JLabel actualInstructions = new JLabel("<html>Press I to view your current inventory. Walk onto the store or the market, and press Enter to bring up the interface.</html>");
        actualInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton startButton  = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                FullGame game = new FullGame(100000);
                game.start();
            }
        });
        contentPane.add(instructions);
        contentPane.add(actualInstructions);
        contentPane.add(startButton);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
