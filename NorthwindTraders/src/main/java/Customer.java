public class Customer implements Printable{

    String contactName;
    String companyName;
    String city;
    String country;
    String phoneNumber;

    public Customer(String contactName, String companyName, String city, String country, String phoneNumber) {
        this.contactName = contactName;
        this.companyName = companyName;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }

    public void print() {
        System.out.println("Contact Name: " + this.contactName);
        System.out.println("Company Name: " + this.companyName);
        System.out.println("City: " + this.city);
        System.out.println("Country: " + this.country);
        System.out.println("Phone Number: " + this.phoneNumber);
    }

}
