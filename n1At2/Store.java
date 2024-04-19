package n1At2;

public class Store implements AccountHolder {
    private float salaryValue = 1400;
    private String accKey;
    private Staff staff[] = new Staff[2];

    public Store() {
        createAcc();
        for(int i = 0; i<2; i++) {
            Staff worker = new Staff();
            staff[i] = worker;
        }
    }

    public String getAccKey() { return accKey; }

    private void paySalaries() {
        for(int i = 0; i<staff.length; i++) {
            String staffKey = staff[i].getSalaryKey();
            Bank.singleton.transfer(salaryValue, this.accKey, staffKey);
        }
    }

    private void createAcc() {
        Bank.singleton.createNewAcc(this, 0);
    }

    @Override
    public void didChangeFundsTo(double newValue, String accKey) {
        if(newValue >= salaryValue * staff.length) {
           paySalaries();
        }
    }

    @Override
    public void didReceiveFunds(double deposit, String accKey) { return; }

    @Override
    public void didTransferFunds(double transfer, String accKey) { return; }
}
