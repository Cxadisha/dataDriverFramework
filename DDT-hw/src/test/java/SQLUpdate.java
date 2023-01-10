import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;

public class SQLUpdate {

    String updatedName = "George";

    @Test
    public void SqlUpdate() {


        String sqlUpdate = "update [students].[dbo].[students]\n" +
                "set firstName=?\n" +
                "where id=?";

        try (Connection conn = MySql.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            conn.setAutoCommit(false);

            int id = 1003;

            pstmt.setString(1, updatedName);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

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

            Assert.assertEquals(rs.getString(2), updatedName);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
