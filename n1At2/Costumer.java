package n1At2;

public class Costumer extends Thread implements AccountHolder {
    private String accKey = null;
    private boolean looping = true;
    private double startingFunds = 1000;

    private Store stores[];
    private int currentStoreIdx = 0;

    public Costumer(Store stores[]) {
        creatAcc();
        this.stores = stores;
    }

    public void run() {
        Store store = stores[currentStoreIdx];
        Bank.singleton.transfer(100, accKey, store.getAccKey());

        if(currentStoreIdx >= stores.length) {
            currentStoreIdx = 0;
        } else {
            currentStoreIdx++;
        }

        if(looping) {
            run();
        }
    }

    private void creatAcc() {
        if(accKey == null) {
            accKey = Bank.singleton.createNewAcc(this, startingFunds);
        }
    }

    @Override
    public void didChangeFundsTo(double newValue, String accKey) {
        looping = newValue > 0;
    }

    @Override
    public void didReceiveFunds(double deposit, String accKey) { return; }

    @Override
    public void didTransferFunds(double transfer, String accKey) { return; }

    @Override
    public String holderID() { return "Costumer " + accKey; }
}
