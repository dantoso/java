package n1At2;
import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<String, Account>();

    public String createNewAcc() {
        int size = accounts.size();
        String key = Integer.toString(size);

        Account newAcc = new Account();
        accounts.put(key, newAcc);
        
        return key;
    }

    public float getAccBalance(String key) {
        Account acc = accounts.get(key);

        if(acc == null) {
            return 0;
        }

        return acc.getMoney();
    }

    public TransferError transfer(float amount, String fromKey, String toKey) {
        Account fromAcc = accounts.get(fromKey);
        Account toAcc = accounts.get(toKey);
        if(fromAcc == null || toAcc == null) {
            return TransferError.accNotFound;
        }

        float retrieval = fromAcc.retrieve(amount);
        if(retrieval < amount) {
            return TransferError.notEnoughFunds;
        }

        toAcc.deposit(retrieval);
        
        return null;
    }
}
