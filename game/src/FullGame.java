import engine.display.Game;
import engine.display.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FullGame extends Game {

    int time;
    Market market;
    Store store;
    Player player;
    Sprite character = new Sprite("Character", "player.png");
    Sprite storeSprite = new Sprite("Store", "store.png");
    Sprite marketSprite = new Sprite("Market", "market.png");


    public FullGame(double capital) {
        super("EnergyFarm", 1000, 750);
        time = 0;
        market = new Market();
        market.calculateCurrentPrice();
        store = new Store();
        store.generateInventory();
        player = new Player(capital);
        this.addChild(storeSprite);
        this.addChild(marketSprite);
        this.addChild(character);
        character.setPosition(new Point(475, 300));
        storeSprite.setPosition(new Point(200, 500));
        marketSprite.setPosition(new Point(700, 200));
    }

    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);
        if(character != null) character.update(pressedKeys);

        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            character.setPosition(new Point(character.getPosition().x, character.getPosition().y - 5));
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            character.setPosition(new Point(character.getPosition().x - 5, character.getPosition().y));
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            character.setPosition(new Point(character.getPosition().x + 5, character.getPosition().y));
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            character.setPosition(new Point(character.getPosition().x, character.getPosition().y + 5));
        }
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
        FullGame game = new FullGame(100000);
        game.start();
        System.out.println(game.store.getInventory().get(0).getName());
    }
}
