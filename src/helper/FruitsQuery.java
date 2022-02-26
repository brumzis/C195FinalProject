package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class FruitsQuery {

    public static int insert(String fruitName, int colorId) throws SQLException {
        String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES (?, ?)";  //excluded primary key column
        PreparedStatement ps =  JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, colorId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
