public class Level3 extends FullGame {

    public Level3() {
        super(3,1000, 60);
        this.store.setPrice(50000, 40000,300000);
        this.store.setProduction(500, 500, 3000);
        this.store.setStd(0.05, 0.1, 0.2);
        this.store.clearInventory();
        this.store.generateInventory("wind");
        this.store.generateInventory("solar");
        this.store.generateInventory("hydro");
        this.mode = "Expert";
    }

}
