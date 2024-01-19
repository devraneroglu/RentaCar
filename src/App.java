import business.UserManager;
import core.Db;
import core.Helper;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        // Connection con = Db.getInstance();
        Helper.setTheme();
        UserManager usermanager = new UserManager();
        AdminView adminView = new AdminView(usermanager.findByLogin("admin", "1234"));
    }
}
