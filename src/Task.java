import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Task {
    private int id;
    private String title;
    private String description;
    private Date createAt;
    private boolean status;
    private int accountId;
    private ArrayList<String> categories;

    public Task() {
        this.id = -1;
        this.title = "";
        this.description = "";
        this.createAt = new Date();
        this.status = false;
        this.accountId = -1;
        this.categories=null;
    }

    public Task(int id, String title, String description, Date createAt, boolean status, int accountId, ArrayList<String> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createAt = createAt;
        this.status = status;
        this.accountId = accountId;
        this.categories=categories;
    }

    //getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public boolean getStatus(){
        return status;
    }
    public int getAccountId() {
        return accountId;
    }
    public ArrayList<String> getCategories() {
        return categories;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    //methods
    public static Task create(Scanner sc, Account account){
        System.out.println("this is your temporary ID:");
        int id= 999999;
        System.out.println(id);
        System.out.println("Enter the title:");
        String title= sc.nextLine();
        System.out.println("Enter the description:");
        String description= sc.nextLine();
        Date date= new Date();
        boolean status = false;
        int accountId = account.getId();
        ArrayList<String> categories= new ArrayList<String>();
        boolean catLoop=true;
        while(catLoop){
            System.out.println("Enter the categories of the task, use 'ok' to validate your entries,'reset' to clear all previous categories, 'cancel' to redo the last entry");
            String category= sc.nextLine();
            switch (category) {
                case "ok":
                    catLoop=false;
                    break;

                case "reset":
                    categories.clear();
                    break;

                case "cancel":
                    categories.removeLast();
                    break;

                default:
                    categories.add(category);
            }
        }
        return new Task (id, title, description, date, status, accountId, categories);
    }
    public void display(){
        System.out.println("===============================================");
        System.out.println("Task ID: "+ this.getId());
        System.out.println("Task Title: "+ this.getTitle());
        System.out.println("Task Description: "+ this.getDescription());
        System.out.println("Task Status: "+ (this.getStatus()?"Validé":"À Faire"));
        System.out.println("Task Created: "+ this.getCreateAt());
        System.out.println("Author: "+ AccountRepository.getAccountById(this.getAccountId()));
        System.out.println("===============================================");
    }

}

