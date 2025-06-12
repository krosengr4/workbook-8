public class Shipper implements NorthwindData {

    int shipperID;
    String companyName;
    String phoneNumber;

    public Shipper (int shipperID, String name, String phoneNumber) {
        this.shipperID = shipperID;
        this.companyName = name;
        this.phoneNumber = phoneNumber;
    }

    public void print() {
        System.out.println("Shhipper ID: " + shipperID);
        System.out.println("Shipper Name: " + this.companyName);
        System.out.println("Phone Number: " + this.phoneNumber);
    }

}
