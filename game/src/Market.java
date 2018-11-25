import java.util.Random;

public class Market {
    private double currentPrice;

    public double getCurrentPrice() { return currentPrice; }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Market(double initialPrice) {
        this.currentPrice = initialPrice;
    }

    public void calculateCurrentPrice() {
        Random r = new Random();
        double returns = r.nextGaussian()*2; // mean 0, std 2
        double nextPrice = this.currentPrice + (this.currentPrice * returns);
        setCurrentPrice(nextPrice);
    }
}
