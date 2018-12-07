import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Setup UI
        JFrame menu = new JFrame("Energy Farm Menu");
        menu.setSize(1000,750);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setResizable(false);
        Container contentPane = menu.getContentPane();
        JLabel actualInstructions = new JLabel("<html> <p style=\"text-align: center;\"> *** Energy Farm ***<br><br><br> Energy Farm is a strategy game where you own a property designed to generate and sell energy in an environmentally friendly way, with the goal of maximizing profits. You start with $100,000 in initial capital and have the ability to buy equipment to produce energy. You can then sell the energy you produce on the market for the fair market price determined by the game’s current economy. Throughout time, your goal is to make profit-maximizing decisions in order to end up with the greatest amount of profit possible. <br><br><br>*** INSTRUCTIONS *** <br><br><br> Press I to view your current inventory. <br> Walk onto the store or the market, and press Enter to bring up the interface.<br> Press S to use the settings. <br> Press Space to advance time. <br> Select a button below to begin. <br><br> " +
                "<b>Novice:</b> The length of the game is short. Each piece of equipment is around the same price but has a different energy production level, making it easy to determine which is the better value. When selling energy on the market, the price at the next time period is predetermined, and the interface provides the player a hint of the price trend." +
                "<b> Intermediate:</b> The length of the game is long and the difference in equipment efficiency given varying prices is not so obvious. This level introduces a timer, where the player has a limited amount of time between each decision. The player now has to balance analysis and decision making with allowing enough time to physically perform the desired actions. Once the timer expires, time will progress one year. (the time per move is adjustable for players with (dis)abilities)." +
                "<b> Expert:</b> The player now can borrow capital to enter into a leveraged position, and can repay the amount borrowed at each time period according to a specified interest rate. Machines also have an added maintenance cost. These two mechanics introduce the idea of expenses, and the player loses the game if bankrupt. Additionally, there is a news alert each time period that can change the specifications of each piece of equipment.<br><br></p></html>");
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
        File f = new File("./resources/hiscores.txt");
        ArrayList<String> scores = new ArrayList<>();
        try {
            BufferedReader fr = new BufferedReader(new FileReader(f));
            String l;
            while ((l = fr.readLine()) != null) {
                scores.add(l);
            }
            fr.close();
        }
        catch(Exception e) {
            scores.add("No hiscores.");
        }
        JLabel separator = new JLabel("\n");
        JLabel scoresLabel = new JLabel("*** Hiscores ***");
        scoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(separator);
        separator = new JLabel("\n");
        contentPane.add(separator);
        contentPane.add(scoresLabel);
        separator = new JLabel("\n");
        contentPane.add(separator);
        String novicehiscore = "None: 0";
        String intermediatehiscore = "None: 0";
        String experthiscore = "None: 0";
        for (String s : scores) {
            String[] parts = s.split(":");
            if (parts[0].equals("Novice")) {
                if (Double.parseDouble(parts[2]) > Double.parseDouble(novicehiscore.split(":")[1])) {
                    novicehiscore = parts[1] + ": " + parts[2];
                }
            }
            else if (parts[0].equals("Intermediate")) {
                if (Double.parseDouble(parts[2]) > Double.parseDouble(intermediatehiscore.split(":")[1])) {
                    intermediatehiscore = parts[1] + ": " + parts[2];
                }
            }
            else if (parts[0].equals("Expert")) {
                if (Double.parseDouble(parts[2]) > Double.parseDouble(experthiscore.split(":")[1])) {
                    experthiscore = parts[1] + ": " + parts[2];
                }
            }
        }
        JLabel noviceLabel = new JLabel("*** Novice ***");
        noviceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel noviceScore = new JLabel(novicehiscore);
        noviceScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(noviceLabel);
        contentPane.add(noviceScore);
        separator = new JLabel("\n");
        contentPane.add(separator);
        JLabel intermediateLabel = new JLabel("*** Intermediate ***");
        intermediateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel intermediateScore = new JLabel(intermediatehiscore);
        intermediateScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(intermediateLabel);
        contentPane.add(intermediateScore);
        separator = new JLabel("\n");
        contentPane.add(separator);
        JLabel expertLabel = new JLabel("*** Expert ***");
        expertLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel expertScore = new JLabel(experthiscore);
        expertScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(expertLabel);
        contentPane.add(expertScore);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
