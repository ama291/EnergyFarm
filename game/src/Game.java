public class Game {

    public int time;
    public Market market;
    public Store store;

    public Game() {
        this.time = 0;
        this.market = new Market();
        this.store = new Store();
        store.generateInventory();
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.store.getInventory().get(0).getName());
    }
}
