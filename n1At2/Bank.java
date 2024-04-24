package n1At2;
import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<String, Account>();

    public static Bank singleton = new Bank();

    public synchronized String createNewAcc(AccountHolder owner, double startingFunds) {
        int size = accounts.size() + 1;
        String key = Integer.toString(size);

        Account newAcc = new Account(owner, startingFunds);
        accounts.put(key, newAcc);

        return key;
    }

    public double getAccFunds(String key) {
        Account acc = accounts.get(key);

        if(acc == null) {
            return 0;
        }

        return acc.getFunds();
    }

    public synchronized void transfer(double amount, String fromKey, String toKey) {
        Account fromAcc = accounts.get(fromKey);
        Account toAcc = accounts.get(toKey);
        if(fromAcc == null || toAcc == null) {
            System.exit(0);
        }

        double retrieval = fromAcc.retrieve(amount);
        if(retrieval < amount) {
            fromAcc.deposit(retrieval);
            System.exit(0);
        }

        toAcc.deposit(retrieval);

        printTransfer(retrieval, fromAcc.getHolder(), toAcc.getHolder());

        // notify acc holders
        fromAcc.getHolder().didTransferFunds(amount, fromKey);
        fromAcc.getHolder().didChangeFundsTo(fromAcc.getFunds(), fromKey);
        
        toAcc.getHolder().didReceiveFunds(retrieval, toKey);
        toAcc.getHolder().didChangeFundsTo(toAcc.getFunds(), toKey);
    }

    public void printInitialValues() {
        System.out.println(" ===================== INITIAL VALUES =====================");
        for(int i = 1; i <= accounts.size(); i++) {
            Account acc = accounts.get(Integer.toString(i));
            print(acc);
        }
    }

    private void printTransfer(double value, AccountHolder fromHolder, AccountHolder toHolder) {
        System.out.println(" ===================== TRANSFER DONE =====================");
        System.out.println();
        System.out.println("FROM " + fromHolder.holderID() + " TO " + toHolder.holderID());
        System.out.println("VALUE: " + value);
    }

    private void print(Account acc) {
        String id = acc.getHolder().holderID();
        double funds = acc.getFunds();
        System.out.println(id + " FUNDS: " + funds);
    }
}
