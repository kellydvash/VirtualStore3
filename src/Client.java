import java.time.LocalDate;
public class Client extends User {
    final int COUNT_ITEMS=0;
    private double totalPaid;
    private int totalItems;
    public boolean isMember;
    private LocalDate lastDateOfShop;

    public Client(String firstName, String lastName, String userName, String password , boolean isMember){
        super(firstName,lastName,userName,password);
        this.totalItems=COUNT_ITEMS;
        this.isMember=isMember;
        this.lastDateOfShop=LocalDate.ofEpochDay(4/2022);

    }
    public void updateTheSumOfTheItems(){
        this.totalItems=(this.totalItems+1);
    }
public void updateTheLastDate(){
        this.lastDateOfShop=LocalDate.now();
}

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        this.isMember = member;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public LocalDate getLastDateOfShop() {
        return lastDateOfShop;
    }

    public void setLastDateOfShop(LocalDate lastDateOfShop) {
        this.lastDateOfShop = lastDateOfShop;
    }

    public String toString(){
        StringBuilder present= new StringBuilder();
        present.append("Hello ").append(this.getFirstName()).append(" ").append(this.getLastName()).append(" ");
        if (this.isMember()){
            present.append("VIP! \n" );
        }else present.append("! \n");
        present.append(" the amount of items that the client chose is - ").append(this.getTotalItems()).append("\n");
        present.append("The sum of all the items that the client chose to shop is - ").append(this.getTotalPaid()).append("\n");
        present.append(" \n the last date that the client visit the store is - ").append(this.getLastDateOfShop());
        return String.valueOf(present);

    }
    public void updateTotalPaid(double bill){
        this.totalPaid = this.totalPaid + bill;
    }
}
