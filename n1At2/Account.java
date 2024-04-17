package n1At2;

public class Account {
    private float money;

    public float getFunds() {
        return money;
    }

    public void deposit(float value) {
        money += value;
    }

    public float retrieve(float value) {
        if(money - value < 0) {
            float retrieval = money;
            money = 0;
            return retrieval;
        }

        money -= value;
        return value;
    }

    public float retrieveAll() {
        float value = money;
        money = 0;
        return value;
    }
}
