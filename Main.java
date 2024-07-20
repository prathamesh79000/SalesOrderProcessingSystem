package practise.CitiusTech;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer{
    int customerId;
    String customerPassword;
    double credit;
    String email;

    public Customer(int customerId, String customerPassword, double credit, String email) {
        this.customerId = customerId;
        this.customerPassword = customerPassword;
        this.credit = credit;
        this.email = email;
    }

    public double getCredit(){
        return credit;
    }

    public int getCustomerId(){
        return customerId;
    }

    public String  getEmail(){
        return email;
    }

    public void updateCredit(double totalCost){
        if(credit >= totalCost){
            credit -= totalCost;
        }
    }

    public void displayDetails(){
        System.out.println("Customer ID: " + customerId);
        System.out.println("Customer Email: " + email);
        System.out.println("Available credit: " + credit);
    }
}

class Product{
    int productNumber;
    double productPrice;
    int productStock;
    private static final List<Product> productList = new ArrayList<Product>();

    public Product(int productNumber, double productPrice, int productStock) {
        this.productNumber = productNumber;
        this.productPrice = productPrice;
        this.productStock = productStock;
        productList.add(this);
    }

    public double getProductPrice(){
        return productPrice;
    }

    public int getProductStock(){
        return productStock;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void updateStock(int quantity){
        if(productStock >= quantity){
            productStock -= quantity;
        }else {
            throw new IllegalArgumentException("Insufficient Quantity");
        }
    }

    public void displayDetails(){
        System.out.println("Product ID: " + productNumber);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product quantity available: " + productStock);
    }

    public void displayAllProducts(){
        System.out.println("Available Products:");
        for (Product product : productList){
            System.out.println("------------------------------------");
            product.displayDetails();
            System.out.println("------------------------------------");
        }
    }
}

class Order{
    private static int nextOrderNumber = 100; // Starting order number, can be any initial value
    private int orderNumber;
    Date date;
    double totalCost;
    private Invoice invoice;
    // int customerId;
    // int productNumber;

    public Order() {
        this.orderNumber = nextOrderNumber++;
        this.date = new Date();
    }
    public Invoice getInvoice(){
        return invoice;
    }

    public void placeOrder(Customer cust, Product prd, int quantity) {
        totalCost = quantity * prd.getProductPrice();
        if (cust.getCredit() >= totalCost && prd.getProductStock() >= quantity) {
            prd.updateStock(quantity);
            cust.updateCredit(totalCost);
            System.out.println("Order placed successfully!");
            invoice = new Invoice(orderNumber, new Date(), cust, prd, quantity, totalCost);
        } else {
            System.out.println("Order Unsuccessful");
        }
    }
}

class Invoice{
    private int orderNumber;
    private Date date;
    private Customer cust;
    private Product prd;
    private int quantity;
    private double totalAmount;

    Invoice(){

    }

    public Invoice(int orderNumber, Date date, Customer customer, Product product, int quantity, double totalAmount) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.cust = customer;
        this.prd = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public void generateInvoice(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("------------------------------------");
        System.out.println("Invoice Details:");
        System.out.println("Order Number: " + orderNumber);
        System.out.println("Date: " + sdf.format(date));
        System.out.println("Customer ID: " + cust.getCustomerId());
        System.out.println("Customer Email: " + cust.getEmail());
        System.out.println("Product Number: " + prd.getProductNumber());
        System.out.println("Product Price: " + prd.getProductPrice());
        System.out.println("Quantity Ordered: " + quantity);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("------------------------------------");
    }
}

class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Customer cust1 = new Customer(101, "abcd@1234", 2000, "abcd@gmail.com");
        Product prd1 = new Product(1, 200, 50);
        Order ord1 = new Order();
        Invoice invoice = new Invoice();
        int userChoice;
        do {
            System.out.println("------------------------------------");
            System.out.println("1. See available products");
            System.out.println("2. Buy a product");
            System.out.println("3. Print invoice");
            System.out.println("Enter 0 to exit");
            System.out.println("Enter your choice: ");

            userChoice = sc.nextInt();

            switch (userChoice) {
                case 1:
                    prd1.displayAllProducts();
                    break;
                case 2:
                    ord1.placeOrder(cust1, prd1, 5);
                    break;
                case 3:
                    if (ord1.getInvoice() != null) {
                        ord1.getInvoice().generateInvoice();
                    } else {
                        System.out.println("No orders placed yet.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (userChoice != 0);

        sc.close();
    }

}