import engine.display.Game;
import engine.display.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FullGame extends Game {

    int time;
    int gameSpan;
    int level;
    Market market;
    Store store;
    Player player;
    Bank bank;
    Sprite character = new Sprite("Character", "player.png");
    Sprite storeSprite = new Sprite("Store", "store.jpg");
    Sprite marketSprite = new Sprite("Market", "market.png");
    Sprite bankSprite = new Sprite("Bank", "bank.png");
    JFrame ui;
    boolean gameOver = false;
    boolean uiopen = false;
    String currency = "$";
    String mode;

    public FullGame(int gameLevel, double capital, int gameSpan) {
        super("EnergyFarm", 1000, 750);
        time = 1;
        level = gameLevel;
        this.gameSpan = gameSpan;
        market = new Market(50);
        market.calculateNextPrice();
        market.updatePrice();
        store = new Store();
        store.generateInventory("wind");
        store.generateInventory("solar");
        store.generateInventory("hydro");
        player = new Player(capital);
        bank = new Bank(0.1, 5, gameSpan + 5 + 1);
        this.addChild(storeSprite);
        this.addChild(marketSprite);
        this.addChild(bankSprite);
        renderFarm();
        this.addChild(character);
        character.setPosition(new Point(460, 400));
        storeSprite.setPosition(new Point(0, 540));
        marketSprite.setPosition(new Point(785, 540));
        bankSprite.setPosition(new Point(375, 568));
    }

    public void renderFarm() {
        int objects = player.getInventory().size();
        if (objects != 0) {
            Point start = new Point(0, 120);
            int w = 1000;
            int h = 400;
            //remove previous images
            for (Equipment i : player.getInventory()) {
                this.removeChild(i.getName());
            }
            //make farm look normal
            if (objects < 10) {
                objects = 10;
            }
            else if (objects % 2 != 0) {
                objects += 1;
            }
            int pixelsperobj = w*h/objects;
            double scale = Math.sqrt(pixelsperobj);
            //find optimal scale
            int columns = (int)Math.ceil(w / scale);
            int x = 0;
            int y = 0;
            scale = w / columns;
            int rows = (int)(h / scale);
            if (objects > columns*rows) {
                columns += 1;
                scale = w / columns;
                rows = (int)(h / scale);
            }
            for (Equipment i : player.getInventory()) {
                if (i.getName() == "solar") {
                    Sprite image = new Sprite("solar", "solarpanel.png");
                    image.setScaleX(scale / image.getUnscaledWidth());
                    image.setScaleY(scale / image.getUnscaledHeight());
                    image.setPosition(new Point(start.x + (int)(x*scale), start.y+(int)(y*scale)));
                    this.addChild(image);
                }
                else if (i.getName() == "wind") {
                    Sprite image = new Sprite("wind", "turbine.png");
                    image.setScaleX(scale / image.getUnscaledWidth());
                    image.setScaleY(scale / image.getUnscaledHeight());
                    image.setPosition(new Point(start.x + (int)(x*scale), start.y+(int)(y*scale)));
                    this.addChild(image);
                }
                else if (i.getName() == "hydro") {
                    Sprite image = new Sprite("hydro", "hydro.png");
                    image.setScaleX(scale / image.getUnscaledWidth());
                    image.setScaleY(scale / image.getUnscaledHeight());
                    image.setPosition(new Point(start.x + (int)(x*scale), start.y+(int)(y*scale)));
                    this.addChild(image);
                }
                x += 1;
                if (x > columns-1) {
                    y += 1;
                    x = 0;
                }
            }
        }
    }

    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);
        gameOver = (time > gameSpan) || (player.getCapital() < 0);

        if(character != null) character.update(pressedKeys);

        if (!uiopen && !gameOver) {
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
                if (character.collidesWith(bankSprite)) {
                    bankUI();
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
            if (character.getPosition().y < 500) {
                character.setPosition(new Point(character.getPosition().x, 500));
            }
        }

    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public void writeHiscore() {
        try {
            File f = new File("./resources/hiscores.txt");
            FileWriter w = new FileWriter(f, true);
            JFrame frame  = new JFrame("Name Input");
            frame.setSize(500,200);
            Container contentPane = frame.getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            JLabel question = new JLabel("What is your name?");
            JTextField name = new JTextField("Player");
            name.setMaximumSize(new Dimension(300, 30));
            JButton submit = new JButton("Submit");
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        w.write(mode + ":" + name.getText() + ":" + String.format("%.2f", player.getCapital()) + "\n");
                        w.close();
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    }
                    catch (Exception e) { return; }
                }
            });
            contentPane.add(question);
            contentPane.add(name);
            contentPane.add(submit);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        catch (Exception e) { return; }
    }

    public void bankUI() {
        // Set up UI
        this.ui = new JFrame("Bank");
        ui.setSize(500,500);
        ui.setResizable(false);
        Container contentPane = new Container();
        JScrollPane scrollPane = new JScrollPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        ui.add(scrollPane);
        // Add Content
        if (level != 3) {
            JLabel sorry = new JLabel("*** Bank only available in Expert difficulty ***");
            contentPane.add(new JLabel("\n"));
            contentPane.add(sorry);
        } else if (gameSpan - time < bank.getMaturity()) {
            JLabel sorry = new JLabel("*** Too close to maturity to borrow capital! ***");
            contentPane.add(new JLabel("\n"));
            contentPane.add(sorry);
        }
        else {
            JLabel capital = new JLabel("\t\t*** Your Capital:" + currency + player.getCapital() + " ***");
            JLabel title = new JLabel("\t\t*** Bank Loan Terms ***");
            JLabel terms = new JLabel("\t\tInterest Rate: " + 100*bank.getRate() + "%, Maturity: " + bank.getMaturity() + " years. Each bond is " + currency + "1000.");
            JTextField quantity = new JTextField("1");
            quantity.setMaximumSize(new Dimension(300, 30));
            JButton borrow = new JButton("Borrow");
            borrow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int quant = 0;
                    try {
                        quant = Integer.parseInt(quantity.getText());
                    }
                    catch (Exception x) {}
                    if (quant < 1) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity.");
                    } else {
                        player.setCapital(player.getCapital() + 1000*quant);
                        bank.addPayment(time + bank.getMaturity(), bank.computePayment(1000*quant));
                        uiopen = false;
                        ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
                        bankUI();
                    }
                }
            });
            contentPane.add(new JLabel("\n"));
            contentPane.add(capital);
            contentPane.add(title);
            contentPane.add(terms);
            contentPane.add(quantity);
            contentPane.add(borrow);
        }
        // Publish UI
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
                            renderFarm();
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
            if (level == 1) {
                market.calculateNextPrice();
                double nextPrice = market.getNextPrice();
                JLabel hint;
                if (nextPrice > market.getCurrentPrice()) {
                    hint = new JLabel("Hint: Price is trending up next year!");
                } else {
                    hint = new JLabel("Hint: Price is trending down next year!");
                }
                contentPane.add(hint);
            }
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
            if (!gameOver) {
                g2d.drawString("*** Game Progress ***", 10,15);
            } else {
                g2d.drawString("*** Game Over ***", 10,15);
            }
            g2d.drawString("Capital: " + currency + String.format("%.2f", player.getCapital()), 10,35);
            g2d.drawString("Total Debt: " + currency + String.format("%.2f", bank.totalDebt()), 10, 50);
            g2d.drawString("Debt Due This Year: " + currency + String.format("%.2f", bank.getPayment(time)), 10, 65);
            g2d.drawString("Energy Stored: " + String.format("%.2f", player.getEnergyStored()) + " kJ", 10,80);
            g2d.drawString("Year: " + time, 10,95);
        }
    }

    public void advance() {
        if (!gameOver) {
            for (Equipment e : player.getInventory()) {
                //TODO randomize this
                player.setEnergyStored(player.getEnergyStored() + e.getProductionLevel());
                e.updateProductionLevel();
            }
            if (bank.getPayment(time) != 0) {
                player.setCapital(player.getCapital() - bank.getPayment(time));
                bank.settlePayment(time);
            }
            store.clearInventory();
            store.generateInventory("wind");
            store.generateInventory("solar");
            store.generateInventory("hydro");
            market.calculateNextPrice();
            market.updatePrice();
            setTime(getTime() + 1);
        }
        gameOver = (time > gameSpan) || (player.getCapital() < 0);
        if (gameOver) {
            double remainingDebt = bank.clearDebt();
            if (remainingDebt != 0) {
                player.setCapital(player.getCapital() - remainingDebt);
            }
            double remainingEnergy = player.getEnergyStored();
            double liquidatedEnergy = remainingEnergy * 35; // Salvage at $35
            player.setCapital(player.getCapital() + liquidatedEnergy);
            player.setEnergyStored(0);
            writeHiscore();
        }
    }
}
