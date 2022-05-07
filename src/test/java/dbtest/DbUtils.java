package dbtest;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtils {

    private static Logger logger = LoggerFactory.getLogger(DbUtils.class);

    /*
        文字列を大文字に変換する
     */
    public static String upper(String str) {
        String result = str.toUpperCase();
        return result;
    }

    /**
     * DBのレコードをXMLファイルに出力する
     */
    public static String saveToXml(String filename) {
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/sampledb";
            Connection jdbcConnection = DriverManager.getConnection(jdbcUrl, "sampleuser", "samplepasswd");
            IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
            QueryDataSet queryDataSet = new QueryDataSet(connection);
            queryDataSet.addTable("DEPT", "SELECT * FROM DEPT;");
            XmlDataSet.write(queryDataSet, new FileOutputStream(filename));
            connection.close();
        } catch (Exception e) {
            logger.error("saveToXml()error", e);
            return null;
        }
        return "OK";
    }
}
