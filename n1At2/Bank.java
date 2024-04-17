package n1At2;
import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<String, Account>();

    public String createNewAcc(AccountHolder owner, float startingFunds) {
        int size = accounts.size() + 1;
        String key = Integer.toString(size);

        Account newAcc = new Account(owner, startingFunds);
        accounts.put(key, newAcc);
        
        return key;
    }

    public float getAccFunds(String key) {
        Account acc = accounts.get(key);

        if(acc == null) {
            return 0;
        }

        return acc.getFunds();
    }

    public TransferError transfer(float amount, String fromKey, String toKey) {
        Account fromAcc = accounts.get(fromKey);
        Account toAcc = accounts.get(toKey);
        if(fromAcc == null || toAcc == null) {
            return TransferError.accNotFound;
        }

        float retrieval = fromAcc.retrieve(amount);
        if(retrieval < amount) {
            fromAcc.deposit(retrieval);
            return TransferError.notEnoughFunds;
        }

        toAcc.deposit(retrieval);

        // notify acc holders
        fromAcc.getHolder().didChangeFundsTo(fromAcc.getFunds());
        toAcc.getHolder().didChangeFundsTo(toAcc.getFunds());

        return null;
    }
}
