import java.util.ArrayList;
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
        Account login= null;
        boolean logged=false;
        String cat="        へ     ♡  ╱|、\n     ૮ - ՛ )    (`- 7\n      / ⁻ ៸|      |、⁻〵\n   乀(ˍ,ل ل      じしˍ,)ノ";
        System.out.println("Welcome to our taskManager");
        System.out.println(cat);

        while (on) {
            try {
                if (login == null && !logged) {
                    System.out.println("Please Log In to use our services, if you do not have an account you can create one here :");
                    System.out.println("--> Type 1 to create an account.");
                    System.out.println("--> Type 2 to sign in.");
                    System.out.println("--> Type quit to close the app.");
                    String query = scanner.nextLine();
                    switch (query) {
                        case "1":
                            AccountRepository.addAccountDB(Account.create(scanner));
                        case "2":
                            System.out.println("Enter your Email:");
                            String mail= scanner.nextLine();
                            System.out.println("Enter your Password:");
                            String pass= scanner.nextLine();
                            if(AccountRepository.getAccountByEmail(mail)!=null){
                                if(AccountRepository.getAccountByEmail(mail).getPassword().equals(pass)){
                                    logged=true;
                                    login=AccountRepository.getAccountByEmail(mail);
                                    System.out.println("You're logged in!");
                                }
                                else{
                                    System.out.println("No account with this email or the password is incorrect.");
                                }
                            }else{
                                System.out.println("No account with this email or the password is incorrect.");
                            }
                            break;
                        case "cat":
                            System.out.println(cat);
                            break;
                        case "quit":
                            on = false;
                            break;
                        default:
                            System.out.println("invalid command");
                    }
                } else if(login != null && logged){
                    System.out.println("Awaiting your command, type help to get the list of commands :");
                    String query = scanner.nextLine();

                    switch (query) {
                        case "add":
                            System.out.println("What do your want to add?");
                            System.out.println("->type 1 for an account");
                            System.out.println("->type 2 for a task");
                            System.out.println("->type 3 for a category");
                            System.out.println("->type 4 to cancel and return to the main menu");
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
                                case "4":
                                    System.out.println("Returning to main menu");
                                    break;
                                default:
                                    System.out.println("invalid command");
                            }
                            break;
                        case "displayAllTask":
                            ArrayList<Task> listTask = TaskRepository.findAll();
                            for (int i = 0; i < listTask.size(); i++) {
                                listTask.get(i).display();
                            }
                            break;
                        case "ToggleTask":
                            System.out.println("Enter the id of the task to toggle:");
                            int id = scanner.nextInt();
                            query = scanner.nextLine();
                            TaskRepository.updateStatus(id);
                            break;
                        case "help":
                            help();
                            break;
                        case "logout":
                            login=null;
                            logged=false;
                            System.out.println("You've been logged out, see you soon!");
                            break;
                        case "quit":
                            login=null;
                            logged=false;
                            on = false;
                            break;
                        case "cat":
                            System.out.println(cat);
                            break;
                        default:
                            System.out.println("invalid command");
                    }
                }
                else{
                    System.out.println("Sorry our service is unavailable at the moment.");
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
        System.out.println("Logout : allow the user to return to the login.");
        System.out.println("cat : bring out the cute little cats ! I know you wanna pet them.");
        System.out.println("quit : close the library application.");
        System.out.println("-------------------------------------------------");
    }

}