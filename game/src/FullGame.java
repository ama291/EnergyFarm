import engine.display.Game;
import engine.display.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FullGame extends Game {

    int time;
    Market market;
    Store store;
    Player player;
    Sprite character = new Sprite("Character", "player.png");
    Sprite storeSprite = new Sprite("Store", "store.jpg");
    Sprite marketSprite = new Sprite("Market", "market.png");
    JFrame ui;
    boolean uiopen = false;

    public FullGame(double capital) {
        super("EnergyFarm", 1000, 750);
        time = 0;
        market = new Market(50);
        market.calculateCurrentPrice();
        store = new Store(50, 50 ,50);
        store.generateInventory("wind");
        store.generateInventory("solar");
        player = new Player(capital);
        this.addChild(storeSprite);
        this.addChild(marketSprite);
        this.addChild(character);
        character.setPosition(new Point(475, 500));
        storeSprite.setPosition(new Point(0, 540));
        marketSprite.setPosition(new Point(785, 540));
    }

    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);
        if(character != null) character.update(pressedKeys);

        if (!uiopen) {
            if (pressedKeys.contains(KeyEvent.VK_UP)) {
                character.setPosition(new Point(character.getPosition().x, character.getPosition().y - 5));
            }
            if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
                character.setPosition(new Point(character.getPosition().x - 5, character.getPosition().y));
            }
            if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                character.setPosition(new Point(character.getPosition().x + 5, character.getPosition().y));
            }
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                character.setPosition(new Point(character.getPosition().x, character.getPosition().y + 5));
            }
            if (pressedKeys.contains(KeyEvent.VK_ENTER)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (character.collidesWith(storeSprite)) {
                    storeUI();
                }
                if (character.collidesWith(marketSprite)) {
                    marketUI();
                }
            }
            if (pressedKeys.contains(KeyEvent.VK_I)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                inventoryUI();
            }
        }

    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public void storeUI() {
        if (!uiopen) {
            this.ui = new JFrame("Store");
            ui.setSize(500,500);
            ui.setResizable(false);
            Container contentPane = ui.getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            // Add Player Inventories
            JLabel inventories = new JLabel("*** Your Inventories ***");
            HashMap counts = player.getEquipmentCounts();
            JLabel equipmentCounts = new JLabel("Wind: " + counts.get("wind") + ", Solar: " + counts.get("solar") + ", Hydro: " + counts.get("hydro"));
            contentPane.add(inventories);
            contentPane.add(equipmentCounts);
            contentPane.add(new JLabel("\n"));

            // Add Items for Sale
            for (Equipment i : store.getInventory()) {
                JLabel title = new JLabel("*** Item for Sale ***");
                JLabel name = new JLabel("Name: " + i.getName());
                JLabel price = new JLabel("Price: $" + String.format("%.2f", i.getPrice()));
                JLabel installfee = new JLabel("Install Fee: $" + i.getInstallFee());
                JLabel productionlevel = new JLabel("Production Level: " + i.getProductionLevel() + " units");
                JButton buy = new JButton("Buy");
                buy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        player.getInventory().add(i);
                        uiopen = false;
                        ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
                        storeUI();
                    }
                });
                contentPane.add(title);
                contentPane.add(name);
                contentPane.add(price);
                contentPane.add(installfee);
                contentPane.add(productionlevel);
                contentPane.add(buy);
            }
            ui.setLocationRelativeTo(null);
            ui.setVisible(true);
            ui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    uiopen = false;
                    super.windowClosing(e);
                }
            });
            uiopen = true;
        }
    }

    public void inventoryUI() {
        if (!uiopen) {
            this.ui = new JFrame("Inventory");
            ui.setSize(500,500);
            ui.setResizable(false);
            // Create Content Pane
            Container contentPane = ui.getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            JLabel seperator = new JLabel("\n");
            // Add Player Resources
            JLabel summary = new JLabel("\t\t*** SUMMARY ***");
            JLabel capital = new JLabel("\t\tCurrent Capital: $" + player.getCapital());
            contentPane.add(seperator);
            contentPane.add(summary);
            contentPane.add(capital);
            // Add Equipments
            int equipmentNumber = 0;
            for (Equipment i : player.getInventory()) {
                equipmentNumber++;
                seperator = new JLabel("\n");
                JLabel itemNumber = new JLabel("\t\t *** EQUIPMENT #" + equipmentNumber + " *** \t\t");
                JLabel name = new JLabel("\t\tName: " + i.getName());
                JLabel productionlevel = new JLabel("\t\tProduction Level: " + i.getProductionLevel() + " Joule(s)");
                contentPane.add(seperator);
                contentPane.add(itemNumber);
                contentPane.add(name);
                contentPane.add(productionlevel);
            }
            ui.setLocationRelativeTo(null);
            ui.setVisible(true);
            ui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    uiopen = false;
                    super.windowClosing(e);
                }
            });
            uiopen = true;
        }
    }

    public void marketUI() {
        if (!uiopen) {
            this.ui = new JFrame("Market");
            ui.setSize(500,500);
            ui.setResizable(false);
            Container contentPane = ui.getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            ui.setLocationRelativeTo(null);
            ui.setVisible(true);
            ui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    uiopen = false;
                    super.windowClosing(e);
                }
            });
            uiopen = true;
        }
    }

    public void advance() {
        for (Equipment e : player.getInventory()) {
            //TODO randomize this
            player.setEnergyStored(player.getEnergyStored() + e.getProductionLevel());
        }
        setTime(getTime() + 1);
    }
}
