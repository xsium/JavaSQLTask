import java.sql.*;
import java.util.ArrayList;

public class AccountRepository {

    private static final Connection connect=DBConnect.getConnect();


    public static void addAccountDB(Account account) {
        if (getAccountByEmail(account.getEmail()) != null) {
            System.out.println("Error : An account with this mail already exist!");

        }else {
            try {
                //requête SQL
                String sql = "INSERT INTO Account (firstname, lastname, email, password)" + "VALUES (?, ?, ?, ?)";
                //Préparation de la requête
                PreparedStatement preparedStatement = connect.prepareStatement(sql);
                //Bind des paramètres
                preparedStatement.setString(1, account.getFirstname());
                preparedStatement.setString(2, account.getLastname());
                preparedStatement.setString(3, account.getEmail());
                preparedStatement.setString(4, account.getPassword());
                //Exécution de la requête
                int addedRows = preparedStatement.executeUpdate();
                //test si l'enregistrement est ok
                if (addedRows > 0) {
                    System.out.println("Account successfully Created!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static Account getAccountByEmail(String email) {
        String query = "SELECT id, firstname, lastname, email, password FROM account WHERE email = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }
    public static String getAccountById(int id) {
        String query = "SELECT firstname, lastname FROM account WHERE id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            String result;
            if (rs.next()) {
                result= rs.getString("firstname")+" "+rs.getString("lastname");
                return result;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Account> findAll() {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "SELECT id, firstname, lastname, email, password FROM account";
        try (Statement statement = connect.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return accounts;
    }
}
