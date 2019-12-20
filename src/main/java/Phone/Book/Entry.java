package Phone.Book;

public class Entry {
    private String firstName;
    private String lastName;
    private String type;
    private String number;

    public Entry(String firstName, String lastName, String type, String number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
