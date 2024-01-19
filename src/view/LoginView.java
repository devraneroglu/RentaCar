package view;

import business.UserManager;
import core.Helper;
import entitiy.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends Layaout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JTextField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_pass;
    private final UserManager userManager;


    public LoginView() {

        this.userManager = new UserManager();
        this.add(container);
        this.initilizeGui(400, 400);
        btn_login.addActionListener(e -> {
/*            if (Helper.isFieldEmpty(this.fld_username) || Helper.isFieldEmpty(this.fld_pass)) {
                Helper.showMsg("fill");
            }*/
            JTextField[] checkFieldList = {this.fld_username, this.fld_pass};

            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            } else {
                User loginuser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_pass.getText());
                if (loginuser == null) {
                    Helper.showMsg("notFound");
                } else {
                    //  System.out.println(loginuser.toString());
                    AdminView adminView = new AdminView(loginuser);
                    dispose();
                }
            }
        });
    }

}
