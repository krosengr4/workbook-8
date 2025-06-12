public class Shipper implements NorthwindData {

    String companyName;
    String phoneNumber;

    public Shipper (String name, String phoneNumber) {
        this.companyName = name;
        this.phoneNumber = phoneNumber;
    }

    public void print() {
        System.out.println("Shipper Name: " + this.companyName);
        System.out.println("Phone Number: " + this.phoneNumber);
    }

}
