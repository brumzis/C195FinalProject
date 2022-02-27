package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static int update(int fruitId, String fruitName) throws SQLException {
        String sql = "UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int fruitId) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM FRUITS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int fruitId = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            System.out.print(fruitId + " | ");
            System.out.print(fruitName + "\n");
        }
    }

    public static void select(int colorId) throws SQLException {
        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, colorId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int fruitId = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            int fruitColor = rs.getInt("Color_ID");
            System.out.print(fruitId + " | ");
            System.out.print(fruitName + " | ");
            System.out.print(fruitColor + "\n");
        }
    }
}
