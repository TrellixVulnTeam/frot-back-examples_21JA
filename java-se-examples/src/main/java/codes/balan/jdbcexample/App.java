package codes.balan.jdbcexample;

import java.sql.*;


/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) throws Exception {

        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(3));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
