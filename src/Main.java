import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnect.getConnect();
        Scanner scanner = new Scanner(System.in);
        try {
            taskManager(scanner);
        }
        catch(InputMismatchException e){
            System.out.println("Invalid Format.");
        }
    }
    public static void taskManager(Scanner scanner) {
        boolean on = true;
        Account login= new Account();
        String cat="        へ     ♡  ╱|、\n     ૮ - ՛ )    (`- 7\n      / ⁻ ៸|      |、⁻〵\n   乀(ˍ,ل ل      じしˍ,)ノ";
        System.out.println("Welcome to our taskManager");
        System.out.println(cat);

        while (on) {
            try {
                System.out.println("Awaiting your command, type help to get the list of commands :");
                String query = scanner.nextLine();

                switch (query) {
                    case "add":
                        System.out.println("What do your want to add?");
                        System.out.println("type 1 for an account");
                        System.out.println("type 2 for a task");
                        System.out.println("type 3 for a category");
                        query = scanner.nextLine();
                        switch (query) {
                            case "1":
                                AccountRepository.addAccountDB(Account.create(scanner));
                            case "2":
                                TaskRepository.addTaskDB(Task.create(scanner, login));
                                break;
                            case "3":
                                CategoryRepository.addCategoryDB(Category.create(scanner));
                                break;
                            default:
                                System.out.println("invalid command");
                        }
                        break;
                    case "displayAllTask":
                        ArrayList<Task> listTask = TaskRepository.findAll();
                        for(int i=0; i<listTask.size();i++){
                            listTask.get(i).display();
                        }
                        break;
                    case "ToggleTask":
                        System.out.println("Enter the id of the task to toggle:");
                        int id= scanner.nextInt();
                        query=scanner.nextLine();
                        TaskRepository.updateStatus(id);
                        break;
                    case "help":
                        help();
                        break;
                    case "quit":
                        on=false;
                        break;
                    case "cat":
                        System.out.println(cat);
                        break;
                    default:
                        System.out.println("invalid command");
                }
            } catch (InputMismatchException e) {
                throw new InputMismatchException("please, respect the instructions");
            }
        }
        System.out.println("Thanks for using our task manager!");
    }
    public static void help(){
        System.out.println("-------------------------------------------------");
        System.out.println("available commands:");
        System.out.println("-------------------------------------------------");
        System.out.println("add : allow the user to manually add an entity.");
        System.out.println("displayAllTask : display the list of all the task for the user.");
        System.out.println("ToggleTask : allow the user to change the state of a task.");
        System.out.println("cat : bring out the cute little cats ! I know you wanna pet them.");
        System.out.println("quit : close the library application.");
        System.out.println("-------------------------------------------------");
    }
    public static void findAll(Scanner sc){

    }

}