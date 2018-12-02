import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;

public class Store {
    private ArrayList<Equipment> inventory;
    private double windPrice;
    private double solarPrice;
    private double hydroPrice;
    private JFrame ui;
    boolean uiopen = false;

    public Store(double windPrice, double solarPrice, double hydroPrice) {
        this.inventory = new ArrayList<Equipment>();
        this.windPrice = windPrice;
        this.solarPrice = solarPrice;
        this.hydroPrice = hydroPrice;
    }

    public ArrayList<Equipment> getInventory() { return inventory; }
    public void clearInventory() { this.inventory = new ArrayList<Equipment>(); }

    public double generatePrice(String equipmentType) {
        //TODO actually calculate
        Random r = new Random();
        double returns = r.nextGaussian() * 0.05; // mean 0, std 0.05
        double nextPrice = 0;
        if (equipmentType == "wind") {
            this.windPrice = this.windPrice + (this.windPrice * returns);
            nextPrice = this.windPrice;
        } else if (equipmentType == "solar") {
            this.solarPrice = this.solarPrice  + (this.solarPrice * returns);
            nextPrice = this.solarPrice;
        } else if (equipmentType == "hydro") {
            this.hydroPrice = this.hydroPrice + (this.hydroPrice * returns);
            nextPrice = this.hydroPrice;
        }
        return nextPrice;
    }

    public double generateEfficiency(String equipmentType) {
        if (equipmentType == "wind") {
            return 100;
        } else if (equipmentType == "solar") {
            return 10;
        } else if (equipmentType == "hydro") {
            return 1000;
        }
        return 1;
    }

    public double generateInstallFee(String equipmentType) {
        // TODO: add installation fee feature
        return 0;
    }

    public void generateInventory(String equipmentType) {
        this.inventory.add(new Equipment(equipmentType,
                generatePrice(equipmentType),
                generateEfficiency(equipmentType),
                generateInstallFee(equipmentType)));
    }

}
