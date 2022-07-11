package codes.balan.jdbcexample;

import java.sql.*;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:h2:C:\\dev\\repos\\jdbc-example\\h2.db;USER=sa;PASSWORD=");

//        select name, age from person where age < 19; drop table person;

//        Statement stmt = conn.createStatement();
//        Random rnd = new Random();
//        stmt.executeUpdate(String.format("insert into person (name, age) values ('%s', %s)", "Joder "+rnd.nextInt(), rnd.nextInt()));
//        stmt.close();

        int filtroEdad = 19;
        String filtroNombre = "oder;\"";

        PreparedStatement pstmt = conn.prepareStatement("select name, age from person where age < ? and nombre ilike ?");
        pstmt.setInt(1, filtroEdad);
        pstmt.setString(2, filtroNombre);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String nombre = rs.getString(1);
            int edad = rs.getInt(2);

            System.out.println(String.format("Nombre: %s, Edad:%s", nombre, edad));
        }
        pstmt.close();
        conn.close();
    }
}
