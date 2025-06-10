public class Actor implements Printable{

    int actorID;
    String firstName;
    String lastName;

    public Actor(int actorID, String firstName, String lastName) {
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void print() {
        System.out.println("First Name: " + this.firstName);
        System.out.println("Last Name: " + this.lastName);
    }
}
