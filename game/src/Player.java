import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<String, Integer> getEquipmentCounts() {
        int wind = 0;
        int solar = 0;
        int hydro = 0;
        for (Equipment i : this.getInventory()) {
            if (i.getName() == "wind") {
                wind++;
            } else if (i.getName() == "solar") {
                solar++;
            } else if (i.getName() == "hydro") {
                hydro++;
            }
        }
        HashMap<String, Integer> count = new HashMap<String, Integer>();
        count.put("wind", wind);
        count.put("solar", solar);
        count.put("hydro", hydro);
        return count;
    }


}
