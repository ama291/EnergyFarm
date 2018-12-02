import java.util.Random;

public class Equipment {
    private double price;
    private double productionLevel;
    private double installFee;
    private String name;

    public Equipment(String name, double price, double productionLevel, double installFee) {
        this.name = name;
        this.price = price;
        this.productionLevel = productionLevel;
        this.installFee = installFee;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getProductionLevel() {
        Random r = new Random();
        double returns = r.nextGaussian() * 0.1; // Mean 0, Standard Deviation 0.1
        this.productionLevel = productionLevel + (productionLevel * returns);
        return this.productionLevel;
    }

    public double getInstallFee() { return installFee; }
}
