package n1At2;

public class Main {
    public static void main(String args[]) {
        System.out.println("starting");

        Store stores[] = new Store[2];
        for(int i = 0; i < stores.length; i++) {
            stores[i] = new Store();
        }

        Costumer costumers[] = new Costumer[5];
        for(int i = 0; i < costumers.length; i++) {
            costumers[i] = new Costumer(stores);
        }

        print(stores);
        print(costumers);

        for(int i = 0; i < costumers.length; i++) {
            costumers[i].start();
        }
    }

    static void print(AccountHolder[] arr) {
        System.out.print("[ ");
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i].holderID() + ", ");
        }
        System.out.println("]");
    }
}
