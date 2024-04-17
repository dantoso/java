package n1At2;

public class Costumer implements AccountHolder {
    private String accKey;
    private boolean looping = true;

    @Override
    public void didChangeFundsTo(double newValue, String accKey) {
        looping = newValue > 0;
    }

    @Override
    public void didReceiveFunds(double deposit, String accKey) { return; }

    @Override
    public void didTransferFunds(double transfer, String accKey) { return; }
}
