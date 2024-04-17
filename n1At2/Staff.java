package n1At2;

public class Staff implements AccountHolder {
    private String salaryAccKey;
    private String investmentAccKey;

    public String getSalaryKey() {
        return salaryAccKey;
    }

    private void invest(double salary) {
        double investment = salary * 0.2;
        Bank.singleton.transfer(investment, salaryAccKey, investmentAccKey);
    }

    @Override
    public void didChangeFundsTo(double newValue, String accKey) { return; }

    @Override
    public void didReceiveFunds(double deposit, String accKey) {
        if(accKey.equals(salaryAccKey)) {
            invest(deposit);
        }
    }

    @Override
    public void didTransferFunds(double transfer, String accKey) { return; }
}
