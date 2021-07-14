
import POJO.CompleteProduct;
import POJO.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteJDBC {

    public static void connectToDatabase() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiDB.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void createTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE PRODUCTS " +

                    // ID's PRIMARY KEY DELETED

                    "(ID             INT, " +
                    " name           TEXT, " +
                    " size           TEXT, " +
                    " color          TEXT, " +
                    " sizeId         TEXT, " +
                    " colorId        TEXT, " +
                    " price          INT, " +
                    " discount       INT, " +
                    " count          INT)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void insert(CompleteProduct product, Connection c) {
//        Connection c = null;
        Statement stmt = null;

        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.db");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO PRODUCTS (ID,name,size,color,sizeId,colorId,price,discount) " +
                    "VALUES ('" + product.getId() + "', '" + product.getName() + "', '" + product.getSize() + "', '" + product.getColor() + "', '" + product.getSizeId() + "', '" + product.getColorId() +"', '" + product.getPrice() + "', '" + product.getDiscount() +"' );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
//            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static ResultSet select() {
        Product res = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTS;" );

            rs.close();
            stmt.close();
            c.close();
            System.out.println("Operation done successfully");
            return rs;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    public static void updatePrice(Product product) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE PRODUCT set COUNT = " + product.getPrices() + " where ID=" + product.getId() + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static void delete() {


        /* NOT IMPLEMENTED YET :D */


//        Connection c = null;
//        Statement stmt = null;
//
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:khanoumi.db");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();
//            String sql = "DELETE from COMPANY where ID=2;";
//            stmt.executeUpdate(sql);
//            c.commit();
//
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
//
//            while ( rs.next() ) {
//                int id = rs.getInt("id");
//                String  name = rs.getString("name");
//                int age  = rs.getInt("age");
//                String  address = rs.getString("address");
//                float salary = rs.getFloat("salary");
//
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
//                System.out.println( "AGE = " + age );
//                System.out.println( "ADDRESS = " + address );
//                System.out.println( "SALARY = " + salary );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Operation done successfully");
    }

}
