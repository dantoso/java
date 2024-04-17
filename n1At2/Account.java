package n1At2;

public class Account {
    private AccountHolder holder;
    private float funds;

    public Account(AccountHolder holder, float startingFunds) {
        this.holder = holder;
        this.funds = startingFunds;
    }

    public AccountHolder getHolder() {
        return holder;
    }

    public float getFunds() {
        return funds;
    }

    public void deposit(float value) {
        funds += value;
    }

    public float retrieve(float value) {
        if(funds - value < 0) {
            float retrieval = funds;
            funds = 0;
            return retrieval;
        }

        funds -= value;

        return value;
    }

    public float retrieveAll() {
        float value = funds;
        funds = 0;
        return value;
    }
}
