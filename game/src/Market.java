import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class Market {
    private double currentPrice;
    private double nextPrice;

    public Market(double initialPrice) {
        this.currentPrice = initialPrice;
        this.nextPrice = initialPrice;
    }

    public double getCurrentPrice() { return currentPrice; }
    public double getNextPrice() { return nextPrice; }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void calculateNextPrice() {
        Random r = new Random();
        if (this.currentPrice == this.nextPrice) {
            double returns = r.nextGaussian()*0.15; // mean 0, std 0.15
            this.nextPrice = this.currentPrice + (this.currentPrice * returns);
        }
    }

    public void updatePrice() {
        setCurrentPrice(nextPrice);
    }

}
