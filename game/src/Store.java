import java.util.ArrayList;

public class Store {
    private ArrayList<Equipment> inventory;

    public Store() {
        this.inventory = new ArrayList<Equipment>();
    }

    public ArrayList<Equipment> getInventory() { return inventory; }

    public double generatePrice() {
        //TODO actually calculate
        return 1;
    }

    public double generateEfficiency() {
        //TODO actually calculate
        return 1;
    }

    public void generateInventory() {
        //TODO do this for all types of equipment
        this.inventory.add(new Equipment("Windmill", generatePrice(), generateEfficiency(), 0));
    }
}
