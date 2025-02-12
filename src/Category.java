import java.util.Date;
import java.util.Scanner;

public class Category {
    private int id;
    private String name;

    public Category(){
        this.id=-1;
        this.name="";
    }
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // setters
    public int getId() { return id; }
    public String getName() { return name; }

    //setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    //methods
    public static Category create(Scanner sc){
        System.out.println("this is your temporary ID:");
        int id= 999999;
        System.out.println(id);
        System.out.println("Enter the name:");
        String title= sc.nextLine();
        return new Category (id, title);
    }
}