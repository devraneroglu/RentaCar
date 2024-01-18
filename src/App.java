import core.Db;
import core.Helper;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        // Connection con = Db.getInstance();
        Helper.setTheme();
        LoginView loginView = new LoginView();
    }
}
