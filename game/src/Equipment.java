import java.util.Random;

public class Equipment {
    private double price;
    private double productionLevel;
    private double productionStd;
    private double installFee;
    private String name;

    public Equipment(String name, double price, double productionLevel, double productionStd, double installFee) {
        this.name = name;
        this.price = price;
        this.productionLevel = productionLevel;
        this.productionStd = productionStd;
        this.installFee = installFee;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getProductionLevel() { return this.productionLevel; }
    public double getProductionStd() { return this.productionStd; }


    public void updateProductionLevel() {
        Random r = new Random();
        double returns = r.nextGaussian() * this.productionStd; // Mean 0, Standard Deviation Custom
        this.productionLevel = productionLevel + (productionLevel * returns);
    }

    public double getInstallFee() { return installFee; }
}
