import java.util.ArrayList;

public class Store {
    private ArrayList<Equipment> inventory;

    public Store() {
        this.inventory = new ArrayList<Equipment>();
    }

    public ArrayList<Equipment> getInventory() { return inventory; }

    public double generatePrice() {
        //need to actually calculate
        return 1;
    }

    public double generateEfficiency() {
        //need to actually calculate
        return 1;
    }

    public void generateInventory() {
        //need to do this for all types of equipment
        this.inventory.add(new Equipment("Windmill", generatePrice(), generateEfficiency(), 0));
    }
}
