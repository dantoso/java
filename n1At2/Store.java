package n1At2;

public class Store implements AccountHolder {
    private float salaryValue = 1400;
    private String accKey;
    private Staff staff[] = new Staff[2];

    private int staffToReceive = 0;

    public Store() {
        createAcc();
        for(int i = 0; i<2; i++) {
            Staff worker = new Staff();
            staff[i] = worker;
        }
    }

    public String getAccKey() { return accKey; }

    private void paySalaries() {
        String staffKey = staff[staffToReceive].getSalaryKey();
        Bank.singleton.transfer(salaryValue, this.accKey, staffKey);

        if(staffToReceive >= staff.length - 1) {
            staffToReceive = 0;
        } else {
            staffToReceive++;
        }
    }

    private void createAcc() {
        accKey = Bank.singleton.createNewAcc(this, 0);
    }

    @Override
    public void didChangeFundsTo(double newValue, String accKey) {
        System.out.println(holderID() + " REMAINING FUNDS: " + newValue);
        if(newValue >= salaryValue) {
           paySalaries();
        }
    }

    @Override
    public void didReceiveFunds(double deposit, String accKey) { return; }

    @Override
    public void didTransferFunds(double transfer, String accKey) { return; }

    @Override
    public String holderID() { return "Store " + accKey; }
}
