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
    int gameSpan;
    Market market;
    Store store;
    Player player;
    Sprite character = new Sprite("Character", "player.png");
    Sprite storeSprite = new Sprite("Store", "store.jpg");
    Sprite marketSprite = new Sprite("Market", "market.png");
    JFrame ui;
    boolean uiopen = false;
    String currency = "$";

    public FullGame(double capital, int gameSpan) {
        super("EnergyFarm", 1000, 750);
        time = 1;
        this.gameSpan = gameSpan;
        market = new Market(50);
        market.calculateCurrentPrice();
        store = new Store(50000, 20000,200000);
        store.generateInventory("wind");
        store.generateInventory("solar");
        store.generateInventory("hydro");
        player = new Player(capital);
        this.addChild(storeSprite);
        this.addChild(marketSprite);
        this.addChild(character);
        character.setPosition(new Point(475, 500));
        storeSprite.setPosition(new Point(0, 540));
        marketSprite.setPosition(new Point(785, 540));
        player.setEnergyStored(1000);
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
//        player.getInventory().add(store.getInventory().get(1));
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
            if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                advance();
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
            Container contentPane = new Container();
            JScrollPane scrollPane = new JScrollPane(contentPane);
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            ui.add(scrollPane);
            // Add Player Inventories
            JLabel inventories = new JLabel("*** Your Inventories ***");
            HashMap counts = player.getEquipmentCounts();
            JLabel equipmentCounts = new JLabel("Wind: " + counts.get("wind") + ", Solar: " + counts.get("solar") + ", Hydro: " + counts.get("hydro"));
            JLabel myCapital = new JLabel("Capital: $" + String.format("%.2f", player.getCapital()));
            contentPane.add(inventories);
            contentPane.add(myCapital);
            contentPane.add(equipmentCounts);
            contentPane.add(new JLabel("\n"));

            // Add Items for Sale
            for (Equipment i : store.getInventory()) {
                JLabel title = new JLabel("*** Item for Sale ***");
                JLabel name = new JLabel("Type: " + i.getName());
                JLabel price = new JLabel("Price: $" + String.format("%.2f", i.getPrice()));
                JLabel installfee = new JLabel("Install Fee: $" + i.getInstallFee());
                JLabel productionlevel = new JLabel("Average Annual Production: " + String.format("%.2f", i.getProductionLevel()) + " kJ");
                JLabel productionstd = new JLabel("Annual Production STDDEV: " + String.format("%.2f", i.getProductionStd() * 100) + "%");
                JTextField quantity = new JTextField("\t");
                quantity.setMaximumSize(quantity.getPreferredSize());
                JButton buy = new JButton("Buy");
                buy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int quant = 0;
                        try {
                            quant = Integer.parseInt(quantity.getText());
                        }
                        catch (Exception x) {}
                        if (quant < 1) {
                            JOptionPane.showMessageDialog(null, "Invalid quantity.");
                        }
                        else if (player.getCapital() < i.getPrice()*quant) {
                            JOptionPane.showMessageDialog(null, "Sorry: you cannot afford this!");
                        } else {
                            for (int x=0; x<quant; x++) {
                                player.getInventory().add(i);
                            }
                            player.setCapital(player.getCapital() - i.getPrice()*quant);
                            uiopen = false;
                            ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
                            storeUI();
                        }
                    }
                });
                contentPane.add(title);
                contentPane.add(name);
                contentPane.add(price);
                contentPane.add(installfee);
                contentPane.add(productionlevel);
                contentPane.add(productionstd);
                contentPane.add(quantity);
                quantity.setText("1");
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
            Container contentPane = new Container();
            JScrollPane scrollPane = new JScrollPane(contentPane);
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            ui.add(scrollPane);
            JLabel separator = new JLabel("\n");
            // Add Player Resources
            JLabel summary = new JLabel("\t\t*** SUMMARY ***");
            JLabel capital = new JLabel("\t\tCurrent Capital: $" + String.format("%.2f", player.getCapital()));
            contentPane.add(summary);
            contentPane.add(capital);
            // Add Equipments
            int equipmentNumber = 0;
            for (Equipment i : player.getInventory()) {
                equipmentNumber++;
                separator = new JLabel("\n");
                JLabel itemNumber = new JLabel("\t\t*** EQUIPMENT #" + equipmentNumber + " *** \t\t");
                JLabel name = new JLabel("\t\tType: " + i.getName());
                JLabel productionlevel = new JLabel("\t\tProduction Level: " + String.format("%.2f", i.getProductionLevel()) + " kJ");
                contentPane.add(separator);
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
            Container contentPane = new Container();
            JScrollPane scrollPane = new JScrollPane(contentPane);
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            ui.add(scrollPane);
            JLabel mark = new JLabel("*** Market ***");
            JLabel energy = new JLabel("Energy Stored: " + String.format("%.2f", player.getEnergyStored()) + " kJ");
            JLabel price = new JLabel("Current Market Price: $" + String.format("%.2f", market.getCurrentPrice()) + " per kJ");
            JTextField quantity = new JTextField("\t");
            quantity.setMaximumSize(quantity.getPreferredSize());
            JButton sell = new JButton("Sell");
            sell.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int quant = 0;
                    try {
                        quant = Integer.parseInt(quantity.getText());
                    }
                    catch (Exception x) {}
                    if (quant > player.getEnergyStored() || quant < 1) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity.");
                    }
                    else {
                        player.setEnergyStored(player.getEnergyStored() - quant);
                        player.setCapital(player.getCapital() + quant*market.getCurrentPrice());
                        ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
                        marketUI();
                    }
                }
            });
            contentPane.add(mark);
            contentPane.add(energy);
            contentPane.add(price);
            contentPane.add(quantity);
            quantity.setText("1");
            contentPane.add(sell);
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

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Graphics2D g2d = (Graphics2D) g;
        if (player != null) {
            if (time < gameSpan) {
                g2d.drawString("*** Game Progress ***", 10,15);
            } else {
                g2d.drawString("*** Game Over ***", 10,15);
            }
            g2d.drawString("Capital: " + currency + String.format("%.2f", player.getCapital()), 10,35);
            g2d.drawString("Energy Stored: " + String.format("%.2f", player.getEnergyStored()) + " kJ", 10,50);
            g2d.drawString("Year: " + time, 10,65);
        }
    }

    public void advance() {
        if (time < gameSpan) {
            for (Equipment e : player.getInventory()) {
                //TODO randomize this
                player.setEnergyStored(player.getEnergyStored() + e.getProductionLevel());
                e.updateProductionLevel();
            }
            store.clearInventory();
            store.generateInventory("wind");
            store.generateInventory("solar");
            store.generateInventory("hydro");
            market.calculateCurrentPrice();
            setTime(getTime() + 1);
        }
    }
}
