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

import com.github.database.rider.core.exporter.DataSetExporter;
import com.github.database.rider.core.api.exporter.DataSetExportConfig;
import com.github.database.rider.core.api.dataset.DataSetFormat;

public class DbUtils {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sampledb";
    private static final String DB_USER = "sampleuser";
    private static final String DB_PASSWORD = "samplepasswd";
   
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
    public static String saveToXml(String filename, String tableName, String whereClause) {
        try {
            IDatabaseConnection connection = DbUtils.connect();
            QueryDataSet queryDataSet = new QueryDataSet(connection);
            String query = DbUtils.createQuery(tableName, whereClause);
            queryDataSet.addTable(tableName, query);
            XmlDataSet.write(queryDataSet, new FileOutputStream(filename));
            connection.close();
        } catch (Exception e) {
            logger.error("saveToXml()error", e);
            return null;
        }
        return "OK";
    }

    // XML以外(xls,json,yml)はdatabase-riderを使って出力
    /**
     * DBのレコードをJSONファイルに出力する
     */
    public static String saveToJson(String filename, String tableName, String whereClause) {
        try {
            DbUtils.saveTo(DataSetFormat.JSON, filename, tableName, whereClause);
        } catch (Exception e) {
            logger.error("saveToJson()error", e);
            return null;
        }
        return "OK";
    }

    /**
     * DBのレコードをEXCELファイルに出力する
     */
    public static String saveToXls(String filename, String tableName, String whereClause) {
        try {
            DbUtils.saveTo(DataSetFormat.XLS, filename, tableName, whereClause);
        } catch (Exception e) {
            logger.error("saveToXls()error", e);
            return null;
        }
        return "OK";
    }

    /**
     * DBのレコードをYMLファイルに出力する
     */
    public static String saveToYml(String filename, String tableName, String whereClause) {
        try {
            DbUtils.saveTo(DataSetFormat.YML, filename, tableName, whereClause);
        } catch (Exception e) {
            logger.error("saveToYml()error", e);
            return null;
        }
        return "OK";
    }

    private static String saveTo(DataSetFormat format, String filename, String tableName, String whereClause) throws Exception {
        DatabaseConnection connection = DbUtils.connect();
        String query = DbUtils.createQuery(tableName, whereClause);
        String[] queryList = new String[]{query};
        DataSetExportConfig datasetExportConfig = 
        new DataSetExportConfig().outputFileName(filename).dataSetFormat(format).queryList(queryList);
        DataSetExporter.getInstance().export(connection, datasetExportConfig);
        connection.close();
        return "OK";
    }

    private static DatabaseConnection connect() throws Exception {
        Connection jdbcConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        return new DatabaseConnection(jdbcConnection);
    }

    private static String createQuery(String tableName, String whereClause) {
        String query = "SELECT * FROM " + tableName;
        if (whereClause != null && whereClause.length() != 0) {
            query = query + " WHERE " +whereClause;
        }
        return query;
    }
}
