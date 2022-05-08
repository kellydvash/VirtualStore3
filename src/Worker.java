public class Worker extends Client{
    private Rank rank;

    public Worker(String firstName, String lastName, String userName, String password, boolean member, Rank rank) {
        super(firstName, lastName, userName, password, member);
        this.rank=rank;

    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
    public double updateTheBillOfTheWorker( double bill){
        double finalBill=0;
        if (this.getRank()==Rank.REGULAR_WORKER){
            finalBill= bill*0.9;

        }
        if (this.getRank()==Rank.MANAGER){
            finalBill=bill*0.8;
        }
        if (this.getRank()==Rank.MEMBER_IN_MANAGEMENT_TEAM){
            finalBill=bill*0.7;

        }
        return finalBill;
    }
}
