import java.util.HashSet;
public class Cart {
    private final int START_TO_SHOP=0;
    private HashSet <Product> productList;
    private double bill;
    public Cart(){
        this.bill= START_TO_SHOP;
        this.productList= new HashSet<>();
    }

    public int getSTART_TO_SHOP() {
        return START_TO_SHOP;
    }

    public HashSet<Product> getItemsList() {
        return productList;
    }

    public void setItemsList(HashSet<Product> itemsList) {
        this.productList = productList;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
    public void addToTheBill(double price){
        this.bill=this.bill+price;
    }
    public void addForProductList(Product product){
        this.productList.add(product);

    }
    public String toString(){
        StringBuilder cart = new StringBuilder();
        cart.append("your cart is - \n");
        for (Product currentProduct : this.productList){
            cart.append(currentProduct.toString()).append("\n");
        }
        cart.append("-----------------------");
        cart.append("your bill now is : ").append(getBill()).append("\n ------------------------- \n ");

        return cart.toString();
    }
}
