import java.sql.*;
import java.util.ArrayList;


public class CategoryRepository {

    private static final Connection connect=DBConnect.getConnect();

    public static void addCategoryDB(Category category) {
        if (findCategoryByName(category.getName()) != null) {
            System.out.println("Error : A category with this name already exist!");

        } else {
            try {
                //requête SQL
                String sql = "INSERT INTO category (name) VALUES (?)";
                //Préparation de la requête
                PreparedStatement preparedStatement = connect.prepareStatement(sql);
                //Bind des paramètres
                preparedStatement.setString(1, category.getName());
                //Exécution de la requête
                int addedRows = preparedStatement.executeUpdate();
                //test si l'enregistrement est ok
                if (addedRows > 0) {
                    System.out.println("Category successfully added!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static int addCategoryDBfromString(String category) {
        if (findCategoryByName(category) != null) {
            return findCategoryByName(category).getId();

        } else {
            try {
                //requête SQL
                String sql = "INSERT INTO category (name) VALUES (?)";
                //Préparation de la requête
                PreparedStatement preparedStatement = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                //Bind des paramètres
                preparedStatement.setString(1, category);
                //Exécution de la requête
                int addedRows = preparedStatement.executeUpdate();
                //test si l'enregistrement est ok
                if (addedRows > 0) {
                    System.out.println("Category successfully added!");
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return new category ID
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }
    }
    public static Category findCategoryByName( String name) {
        String query = "SELECT id, name FROM category WHERE name = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération findcatbyname : " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Category> findAll() {
        ArrayList<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Statement statement = connect.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return categories;
    }
}
