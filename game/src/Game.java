public class Game {

    public int time;
    public Market market;
    public Store store;
    public Player player;

    public Game(double capital) {
        this.time = 0;
        this.market = new Market();
        market.calculateCurrentPrice();
        this.store = new Store();
        store.generateInventory();
        this.player = new Player(capital);
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public void advance() {
        for (Equipment e : player.getInventory()) {
            //TODO randomize this
            player.setEnergyStored(player.getEnergyStored() + e.getProductionLevel());
        }
        setTime(getTime() + 1);
    }

    public static void main(String[] args) {
        Game game = new Game(100000);
        System.out.println(game.store.getInventory().get(0).getName());
    }
}
