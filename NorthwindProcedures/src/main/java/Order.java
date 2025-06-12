public class Order implements NorthwindData{

    int orderID;
    String orderDate;
    String shipDate;
    double freight;
    String shipName;
    String shipAddress;
    String shipCity;
    String shipCountry;

    public Order (int orderID, String orderDate, String shipDate, double freight, String shipName, String shipAddress, String shipCity, String shipCountry) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.freight = freight;
        this.shipName= shipName;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipCountry = shipCountry;
    }

    public void print() {
        System.out.println("OrderID: " + this.orderID);
        System.out.println("Date Ordered: " + this.orderDate);
        System.out.println("Date Shipped: " + this.shipDate);
        System.out.println("Freight: " + this.freight);
        System.out.println("Ship Name: " + this.shipName);
        System.out.println("Ship Address: " + this.shipAddress);
        System.out.println("Ship City: " + this.shipCity);
        System.out.println("Ship Country: " + this.shipCountry);
    }

}
