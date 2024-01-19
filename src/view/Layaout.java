package view;

import core.Helper;

import javax.swing.*;

public class Layaout extends JFrame {
    public void initilizeGui(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Admin Panel");
        this.setSize(width, height);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize()));
        this.setVisible(true);
    }
}
