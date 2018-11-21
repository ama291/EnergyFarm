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
        return productionLevel;
    }

    public double getInstallFee() { return installFee; }
}
