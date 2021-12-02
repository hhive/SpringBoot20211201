package com.example.test.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/7/12 9:45
 */
public class JdbcUtils {

    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static{
        try{
            // InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            // Properties prop = new Properties();
            // prop.load(in);
            //
            // driver = prop.getProperty("oracle.jdbc.driver.OracleDriver");
            // url = prop.getProperty("jdbc\\:oracle\\:thin\\:@//192.168.66.248\\:1521/capital");
            // username = prop.getProperty("capital");
            // password = prop.getProperty("ENC(Mp6nUMsyLBRx6kbBSxM1w3m6hIKlstds)");
            driver = "oracle.jdbc.driver.OracleDriver";
            url = "jdbc:oracle:thin:@192.168.66.248:1521:capital";
            username = "capital";
            password = "test201988";
            Class.forName(driver);

        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        System.out.println(connection);
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username,password);
    }

    public static void release(Connection conn,Statement st, ResultSet rs){

        if(rs!=null){
            try{
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;

        }
        if(st!=null){
            try{
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(conn!=null){
            try{
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
