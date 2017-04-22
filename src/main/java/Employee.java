/**
 * Created by hasanizer on 4/22/2017.
 */
public class Employee {

    private int id;
    private String fullName;
    private String username;
    private String photo;
    private String smileAt;

    public Employee() {
        new Employee(0, "", "", "", "");
    }

    public Employee(int id, String fullName, String username, String photo, String smileAt) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.photo = photo;
        this.smileAt = smileAt;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSmileAt() {
        return smileAt;
    }


}
