package Application;

import Application.Repositories.CustomerRepo;

public class App {
    public static void main(String[] args) {
        CustomerRepo repo = new CustomerRepo();
        System.out.println(repo.findByIdCustomer(1).getPurchaseListForCustomer());
    }
}
