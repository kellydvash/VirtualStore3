public class Product {
    private String description;
    private double price;
    private int discount;
    private boolean inStock;
    private int amount;


    public Product(String description, double price, int discount) {
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.inStock = true;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String toString(){
        return this.description + " " + this.price;
    }
}

