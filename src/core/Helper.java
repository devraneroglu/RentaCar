package core;

import view.LoginView;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel((info.getClassName()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMsg(String str) {
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Lütfen tün alanları doldurunuz ";
                title = "Hata !";
                break;
            case "done":
                msg = "İşlem başarılı";
                title = "Sonuç ! ";
                break;
            case "notfound":
                msg = str + " bulunamadı  !";
                title = "Kayıt bulunamadı ";
                break;
            case "error":
                msg = "Hatalı işlem yaptınız";
                title = "Hata";
                break;

            default:
                msg = str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field))
                return true;
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }
}
