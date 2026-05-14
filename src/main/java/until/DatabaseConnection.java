package until;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FootballClubDB;encrypt=false;trustServerCertificate=true";
        String user = "sa";
        String password = "123456";  // thay bằng mật khẩu của bạn
        return DriverManager.getConnection(url, user, password);
    }
}