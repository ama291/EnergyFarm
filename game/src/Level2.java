public class Level2 extends FullGame {

    public Level2() {
        super(2, 1000000, 40);
        this.store.setPrice(50000, 40000,200000);
        this.store.setProduction(250, 250, 1500);
        this.store.setStd(0.05, 0.1, 0.2);
        this.store.clearInventory();
        this.store.generateInventory("wind");
        this.store.generateInventory("solar");
        this.store.generateInventory("hydro");
        this.mode = "Intermediate";
    }

}
