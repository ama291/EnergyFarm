import java.util.ArrayList;

public class Player {
    private double capital;
    private ArrayList<Equipment> inventory;
    private double energyStored;

    public Player(double capital) {
        this.capital  = capital;
        this.inventory = new ArrayList<Equipment>();
        this.energyStored = 0;
    }

    public double getEnergyStored() {
        return energyStored;
    }
    public double getCapital() {
        return capital;
    }
    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void setEnergyStored(double energyStored) {
        this.energyStored = energyStored;
    }
    public void setCapital(double capital) {
        this.capital = capital;
    }

    public void sellEnergy(int amount, Market market) {
        double price = market.getCurrentPrice();
        setEnergyStored(getEnergyStored() - amount);
        setCapital(getCapital() + price * amount);
    }
}
