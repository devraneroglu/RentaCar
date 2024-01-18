package core;     // Db sınıfı core paketinin altında yer alır.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    // Singleton Design Pattern
    /*
      Bu yapı, Singleton tasarım deseninde "Lazy Initialization" (Tembel Başlatma) yöntemini kullanır.
      Yani, instance değişkeni ihtiyaç duyulduğunda (örneğin, getInstance metodu çağrıldığında) oluşturulur.
      Bu, uygulamanın başlatılma aşamasında gereksiz yere örnek oluşturulmasını önler ve bellek kullanımını optimize eder.
      Bu tasarım deseni, bir sınıfın yalnızca bir örneğine ihtiyaç duyulduğu durumları ele almak için kullanılır.
    */


    private static Db instance = null;   // Db sınıfının bir örneğini tanımlar.  Singleton tasarım desenin uygulamak için kullanılır.
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/rentacar";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "sql";
    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstance() {      // Singleton tasarım deseinin uygular
        try {
            if (instance == null || instance.getConnection().isClosed()) {  // Eğer instance henüz oluşturulmamış veya bağlantı kapalıysa
                instance = new Db();                                         //  yeni bir Db oluştur ve bağlantısını döndür
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();                 // aksi durumda mevcut bağlantıyı döndür.
    }

}


