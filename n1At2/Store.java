package n1At2;

public class Store {
    private String accKey;
    private Staff staff[] = new Staff[2];

    public Store() {
        for(int i = 0; i<2; i++) {
            Staff worker = new Staff();
            staff[i] = worker;
        }
    }
}
