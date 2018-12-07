import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Level3 extends FullGame {

    Timer timer;

    public Level3() {
        super(3,1000, 60);
        this.store.setPrice(50000, 40000,300000);
        this.store.setProduction(250, 250, 1500);
        this.store.setStd(0.05, 0.1, 0.2);
        this.store.clearInventory();
        this.store.generateInventory("wind");
        this.store.generateInventory("solar");
        this.store.generateInventory("hydro");
        this.mode = "Expert";

        startTimer();
    }

    @Override
    public void advance() {
        super.advance();
        timer.cancel();
        startTimer();
    }

    public void startTimer() {
        timer = new Timer("Timer");
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                if (ui != null) {
                    ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
                }
                advance();
            }
        };
        timer.scheduleAtFixedRate(updateTask, 60000, 60000);
    }
}
