package view;

import business.BrandManager;
import entitiy.Brand;
import entitiy.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminView extends Layaout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private BrandManager brandManager;
    private JPopupMenu brandMenu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.add(container);
        this.initilizeGui(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz " + this.user.getUsername());
        Object[] col_brand = {"Marka ID", "Marka Adı"};          //veri tipini object olarak seçmek daha doğru bir yaklaşımdır.
        ArrayList<Brand> brandList = brandManager.findAll();
        tmdl_brand.setColumnIdentifiers(col_brand);

        for (Brand brand : brandList) {
            Object[] obj = {brand.getId(), brand.getName()};
            tmdl_brand.addRow(obj);
        }

        tbl_brand.setModel(tmdl_brand);
        tbl_brand.getTableHeader().setReorderingAllowed(false);
        tbl_brand.setEnabled(false);
        this.tbl_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //  super.mousePressed(e);
                int selected_row = tbl_brand.rowAtPoint(e.getPoint());
                tbl_brand.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        this.brandMenu = new JPopupMenu();
        brandMenu.add("Yeni").addActionListener(e -> {
            System.out.println("Yeni butonuna tıkladınız ");
        });
        brandMenu.add("Güncelle");
        brandMenu.add("Sil");

        this.tbl_brand.setComponentPopupMenu(brandMenu);

    }
}
