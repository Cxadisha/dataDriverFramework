import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;

public class SQLInsert {

    int id = 1003;
    String firstName = "Giorgi";
    String lastName = "Tskhadiashvili";
    String phone = "551575863";

    @Test
    public void SqlQueryingInsert() {

        String sql = "insert into [students].[dbo].[students]\n" +
                "(id, firstname, lastname, phone)\n" +
                "values(?,?,?,?)";

        String sql2 = "SELECT top 10 * FROM [students].[dbo].[students]";

        try (Connection conn = MySql.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql2)) {

            // - Use autocommit false mode
            conn.setAutoCommit(false);

            // set parameters
            pstmt.setInt(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phone);

            // - Insert in database students new row
            pstmt.executeUpdate();

            // - Call commit()
            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void Validate() {

        String sql = "SELECT top 10 * FROM [students].[dbo].[students]\n" +
                "where id=1003";

        try (Connection conn = MySql.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();

            //  Validate all the values of inserted row using TestNG
            Assert.assertEquals(rs.getInt(1), id);
//            Assert.assertEquals(rs.getString(2), firstName); // before update
            Assert.assertEquals(rs.getString(2), "George"); // after update
            Assert.assertEquals(rs.getString(3), lastName);
            Assert.assertEquals(rs.getString(4), phone);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
