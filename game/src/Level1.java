public class Level1 extends FullGame {
    public Level1() {
        super(100000, 20);
        this.store.setPrice(50000, 20000,200000);
        this.store.setProduction(100, 10, 1000);
        this.store.setStd(0.05, 0.01, 0.1);
        this.store.clearInventory();
        this.store.generateInventory("wind");
        this.store.generateInventory("solar");
        this.store.generateInventory("hydro");
    }
}
