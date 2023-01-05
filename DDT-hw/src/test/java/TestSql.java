import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.codeborne.selenide.Selenide.*;

public class TestSql {

    @Test
    public void test() {

        String sql = "SELECT top 10 * FROM [students].[dbo].[students]";

        try (Connection conn = MySql.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                open("https://demoqa.com/automation-practice-form");
                $(By.id("firstName")).sendKeys(rs.getString("firstName"));
                $(By.id("lastName")).sendKeys(rs.getString("lastName"));
                $(By.id("userNumber")).sendKeys(rs.getString("phone"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
