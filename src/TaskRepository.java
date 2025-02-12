import java.sql.*;
import java.util.ArrayList;


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
                PreparedStatement preparedStatement = connect.prepareStatement(sql);
                //Bind des paramètres
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(task.getCreateAt().getTime()));
                preparedStatement.setInt(4, task.getStatus() ? 1 : 0);
                preparedStatement.setInt(5, task.getAccountId());
                //Exécution de la requête
                int addedRows = preparedStatement.executeUpdate();
                //test si l'enregistrement est ok, sinon on retire le livre de la library locale
                if (addedRows > 0) {
                    System.out.println("Task successfully added!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static Task getTaskById(int id) {
        String query = "SELECT id,title,description,createAt,status,account_id FROM task WHERE id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("status"),
                        rs.getInt("account_id")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }
    public static ArrayList<Task> findAll() {
        ArrayList<Task> tasks = new ArrayList<>();
        String query = "SELECT id,title,description,createAt,status,account_id FROM task";
        try (Statement statement = connect.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("createAt"),
                        rs.getBoolean("status"),
                        rs.getInt("account_id")
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

