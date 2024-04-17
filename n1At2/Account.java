package n1At2;

public class Account {
    private AccountHolder holder;
    private double funds;

    public Account(AccountHolder holder, double startingFunds) {
        this.holder = holder;
        this.funds = startingFunds;
    }

    public AccountHolder getHolder() {
        return holder;
    }

    public double getFunds() {
        return funds;
    }

    public void deposit(double value) {
        funds += value;
    }

    public double retrieve(double value) {
        if(funds - value < 0) {
            double retrieval = funds;
            funds = 0;
            return retrieval;
        }

        funds -= value;

        return value;
    }

    public double retrieveAll() {
        double value = funds;
        funds = 0;
        return value;
    }
}
