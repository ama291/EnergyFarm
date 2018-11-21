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
}
