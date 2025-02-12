import java.util.Scanner;

public class Account {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    // Constructeur
    public Account(){
        this.id=1;
        this.firstname="";
        this.lastname="";
        this.email="";
        this.password="";
    }
    public Account(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    //getters
    public int getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    //methods
    public static Account create(Scanner sc){
        System.out.println("this is your temporary ID:");
        int id= 999999;
        System.out.println(id);
        System.out.println("Enter the firstname:");
        String firstname= sc.nextLine();
        System.out.println("Enter the lastname:");
        String lastname= sc.nextLine();
        System.out.println("Enter the mail :");
        String mail= sc.nextLine();
        System.out.println("Enter the password :");
        String password= sc.nextLine();
        return new Account(id, firstname, lastname, mail, password);
    }
}
