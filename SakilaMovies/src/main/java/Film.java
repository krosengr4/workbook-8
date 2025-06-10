public class Film implements Printable{

    String title;
    String description;
    int releaseYear;
    int length;
    String rating;

    public Film (String title, String description, int releaseYear, int length, String rating) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
        this.rating = rating;
    }

    public void print() {
        System.out.println("Title: " + this.title);
        System.out.println("Description: " + this.description);
        System.out.println("Release Year: " + this.releaseYear);
        System.out.println("Length: " + this.length);
        System.out.println("Rating: " + this.rating);
    }
}
