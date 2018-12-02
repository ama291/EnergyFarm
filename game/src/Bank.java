import java.util.Arrays;
import java.util.Random;

public class Bank {

    //Attributes
    private int maturity;
    private double interestRate;
    private double[] debtPayments;

    //Constructors
    public Bank(double startRate, int debtMaturity, int gameLen) {
        interestRate = startRate;
        debtPayments = new double[gameLen];
        maturity = debtMaturity;
        Arrays.fill(debtPayments, 0);
    }

    //Methods
    public double getRate() { return interestRate; }

    public double computePayment(double borrowed) {
        return borrowed + (borrowed * interestRate);
    }

    public void addPayment(int year, double amount) {
        debtPayments[year] += amount;
    }

    public double getPayment(int year) {
        return debtPayments[year];
    }

    public void updateRate() {
        Random r = new Random();
        double returns = r.nextGaussian() * 0.005;
        interestRate += returns;
    }

}
