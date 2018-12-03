import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Level2 extends FullGame {

    public Level2() {
        super(2, 1000000, 40);
        this.store.setPrice(50000, 40000,200000);
        this.store.setProduction(500, 500, 3000);
        this.store.setStd(0.05, 0.1, 0.2);
        this.store.clearInventory();
        this.store.generateInventory("wind");
        this.store.generateInventory("solar");
        this.store.generateInventory("hydro");
        this.mode = "Intermediate";

        Timer timer = new Timer("Timer");
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
                advance();
            }
        };
        timer.scheduleAtFixedRate(updateTask, 60000, 60000);
    }

}
