import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class TaskRepository {

    private static final Connection connect=DBConnect.getConnect();

    public static void addTaskDB(Task task) {
        if (getTaskById(task.getId()) != null) {
            System.out.println("Error : A task with this Id already exist!");

        } else {
            try {
                //requête SQL
                String sql = "INSERT INTO task (title, description, createAt, status, account_id) VALUES (?, ?, ?, ?, ?)";
                //Préparation de la requête
                PreparedStatement preparedStatement = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                //Bind des paramètres
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(task.getCreateAt().getTime()));
                preparedStatement.setInt(4, task.getStatus() ? 1 : 0);
                preparedStatement.setInt(5, task.getAccountId());
                //Exécution de la requête
                int addedRows = preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                int taskId = -1;
                if (generatedKeys.next()) {
                    taskId = generatedKeys.getInt(1);
                }
                if (task.getCategories() != null) {
                    for (String category : task.getCategories()) {
                        int categoryId=CategoryRepository.addCategoryDBfromString(category);
                        if (categoryId != -1) {
                            linkTaskWithCategory(taskId, categoryId);
                        }
                    }
                }
                //test si l'enregistrement est ok, sinon on retire le livre de la library locale
                if (addedRows > 0) {
                    System.out.println("Task successfully added!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void linkTaskWithCategory(int taskId, int categoryId) {
        try {
            String sql = "INSERT INTO task_category (task_id, category_id) VALUES (?, ?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.setInt(2, categoryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error linking task with category: " + e.getMessage());
        }
    }

    public static Task getTaskById(int id) {
        String query = "SELECT t.id as task_id, t.title,t.description,t.createAt,t.status,GROUP_CONCAT(c.name SEPARATOR ', ') AS categories FROM task t JOIN task_category tc ON t.id = tc.task_id JOIN category c ON tc.category_id = c.id WHERE t.id = ? GROUP BY t.id, t.title, t.description, t.createAt, t.status";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ArrayList<String> categories = new ArrayList<>();
                if (rs.getString("categories") != null) {
                    Collections.addAll(categories, rs.getString("categories").split(", "));
                }
                return new Task(
                        rs.getInt("task_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("status"),
                        rs.getInt("account_id"),
                        categories
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération taskbyID : " + e.getMessage());
        }
        return null;
    }
    public static ArrayList<Task> findAll() {
        ArrayList<Task> tasks = new ArrayList<>();
        String query = "SELECT t.id as task_id, t.title,t.description,t.createAt,t.status, GROUP_CONCAT(c.name SEPARATOR ', ') AS categories FROM task t JOIN task_category tc ON t.id = tc.task_id JOIN category c ON tc.category_id = c.id GROUP BY t.id, t.title, t.description, t.createAt, t.status";
        try (Statement statement = connect.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                ArrayList<String> categories = new ArrayList<>();
                if (rs.getString("categories") != null) {
                    Collections.addAll(categories, rs.getString("categories").split(", "));
                }
                tasks.add(new Task(
                        rs.getInt("task_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("status"),
                        rs.getInt("account_id"),
                        categories
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return tasks;
    }
    public static void updateStatus(int taskId) {
        // Vérifier si la tâche existe
        Task task = getTaskById(taskId);
        if (task == null) {
            System.out.println("Error : No Task found with this Id!");
            return;
        }

        // Inverser le statut (true → false, false → true)
        boolean newStatus = !task.getStatus();

        // Mettre à jour la base de données
        String query = "UPDATE task SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, newStatus? 1 : 0);
            statement.setInt(2, taskId);
            statement.executeUpdate();
            System.out.println("Statut de la tâche mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }
}

