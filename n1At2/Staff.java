package n1At2;

public class Staff extends Thread implements AccountHolder {
    private String salaryAccKey = null;
    private String investmentAccKey = null;

    private double depoistValue;
    private boolean hasStarted = false;

    public Staff() {
        createAccs();
    }

    public void run() {
        invest(depoistValue);
    }

    private void createAccs() {
        if(salaryAccKey == null) {
            salaryAccKey = Bank.singleton.createNewAcc(this, 0);
        }

        if(investmentAccKey == null) {
            investmentAccKey = Bank.singleton.createNewAcc(this, 0);
        }
    }

    public String getSalaryKey() { return salaryAccKey; }

    private void invest(double salary) {
        double investment = salary * 0.2;
        Bank.singleton.transfer(investment, salaryAccKey, investmentAccKey);
    }

    @Override
    public void didChangeFundsTo(double newValue, String accKey) { 
        System.out.println(holderID() + " REMAINING FUNDS: " + newValue);
    }

    @Override
    public void didReceiveFunds(double deposit, String accKey) {
        if(accKey.equals(salaryAccKey)) {
            depoistValue = deposit;
            
            if(hasStarted) {
                run();
            } else {
                start();
                hasStarted = true;
            }
            
        }
    }

    @Override
    public void didTransferFunds(double transfer, String accKey) { return; }

    @Override
    public String holderID() { return "Staff " + salaryAccKey; }
}
