package n1At2;

public interface AccountHolder {
    void didChangeFundsTo(double newValue, String accKey);
    void didReceiveFunds(double deposit, String accKey);
    void didTransferFunds(double transfer, String accKey);
}
