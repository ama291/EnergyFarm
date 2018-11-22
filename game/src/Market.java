public class Market {
    private double currentPrice;

    public double getCurrentPrice() { return currentPrice; }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void calculateCurrentPrice() {
        //TODO to actually calculate
        setCurrentPrice(1);
    }
}
