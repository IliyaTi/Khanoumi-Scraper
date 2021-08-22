
import POJO.CompleteProduct;
import POJO.Product;

import java.sql.*;

public class SQLiteJDBC {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void connectToDatabase() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void createProductsTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCTS " +

                    // ID's PRIMARY KEY DELETED

                    "(no           INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "ID             int, " +
                    " brand          varchar, " +
                    " name           varchar, " +
                    " size           varchar, " +
                    " color          varchar, " +
                    " sizeId         int, " +
                    " colorId        int, " +
                    " price          int, " +
                    " discount       int, " +
                    " count          int," +
                    "foreign key (brand) references brands(brandName));";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void createBrandsTable(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.sqlite");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS brands " +
                    "(name       VARCHAR PRIMARY KEY)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertIntoBrands(String name,Connection connection){
        Statement stmt = null;

        try {
//            stmt = connection.createStatement();
//            String sql = "insert or replace into brands values ('" + name + "')";
//            stmt.executeUpdate(sql);
//            stmt.close();

            PreparedStatement ps = connection.prepareStatement("insert or replace into brands values ( ? )");
            ps.setString(1, name);

            ps.executeUpdate();

//            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static synchronized void insertIntoProducts(CompleteProduct product, Connection c) {
//        Connection c = null;
        Statement stmt = null;

        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:khanoumiIDDB.db");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");

            PreparedStatement ps = c.prepareStatement("INSERT INTO PRODUCTS (ID,brand,name,size,color,sizeId,colorId,price,discount) " +
                    "VALUES ( ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ? ,  ?  );");
            ps.setInt(1,product.getId());
            ps.setString(2, product.getBrand());
            ps.setString(3, product.getName());
            ps.setString(4, product.getSize());
            ps.setString(5, product.getColor());
            ps.setString(6, product.getSizeId());
            ps.setString(7, product.getColorId());
            ps.setInt(8, product.getPrice());
            ps.setInt(9, product.getDiscount());

            ps.executeUpdate();
//            stmt = c.createStatement();
//            String sql = "INSERT INTO PRODUCTS (ID,brand,name,size,color,sizeId,colorId,price,discount) " +
//                    "VALUES ('" + product.getId() +"', '" + product.getBrand() + "', '" + product.getName() + "', '" + product.getSize() + "', '" + product.getColor() + "', '" + product.getSizeId() + "', '" + product.getColorId() +"', '" + product.getPrice() + "', '" + product.getDiscount() +"' );";
//            stmt.executeUpdate(sql);

//            stmt.close();
//            c.commit();
//            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("! Records created successfully for " + product.getId() + " - " + product.getName());
    }

    public static ResultSet select() {
        Product res = null;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiDB.sqlite");
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
            c = DriverManager.getConnection("jdbc:sqlite:khanoumiDB.sqlite");
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
