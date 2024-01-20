package view;

import business.BrandManager;
import entitiy.Brand;
import entitiy.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

        loadBrandTable();
        loadBrandCompanent();

        this.tbl_brand.setComponentPopupMenu(brandMenu);
    }

    public void loadBrandCompanent() {
        this.tbl_brand.addMouseListener(new MouseAdapter() {     // sağ click seçenekleri
            @Override
            public void mousePressed(MouseEvent e) {
                //  super.mousePressed(e);
                int selected_row = tbl_brand.rowAtPoint(e.getPoint());
                tbl_brand.setRowSelectionInterval(selected_row, selected_row);
            }
        });

        this.brandMenu = new JPopupMenu();
        brandMenu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        brandMenu.add("Güncelle").addActionListener(e -> {
            // seçili olan brandı almak gerekir.
            int selectBrandId = Integer.parseInt(tbl_brand.getValueAt(tbl_brand.getSelectedRow(), 0).toString());
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        brandMenu.add("Sil");

    }

    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};          //veri tipini object olarak seçmek daha doğru bir yaklaşımdır.
        // ArrayList<Brand> brandList = this.brandManager.findAll();
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
        /*
        tmdl_brand.setColumnIdentifiers(col_brand);
        tbl_brand.setModel(tmdl_brand);
        tbl_brand.getTableHeader().setReorderingAllowed(false);
        tbl_brand.setEnabled(false);

        DefaultTableModel clearModel = (DefaultTableModel) tbl_brand.getModel();  // generic yapıya uymadığı için
        clearModel.setRowCount(0);      // her defasında tablonun içini boşaltır  --> [SQL TRUNCATE]

        for (Brand brand : brandList) {
            Object[] obj = {brand.getId(), brand.getName()};
            tmdl_brand.addRow(obj);
        }
        */


    }
}
