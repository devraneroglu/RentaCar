package view;

import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entitiy.Brand;
import entitiy.Car;
import entitiy.Model;
import entitiy.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private JComboBox cmb_s_model_brand;
    private JComboBox cmb_s_model_type;
    private JComboBox cmb_s_model_fuel;
    private JComboBox cmb_s_model_gear;
    private JButton btn_search_model;
    private JButton btn_clear;
    private JPanel pnl_car;
    private JScrollPane scrl_car;
    private JTable tbl_car;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;
    private JPopupMenu car_menu;
    private Object[] col_model;
    private Object[] col_car;

    public AdminView(User user) {
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.carManager = new CarManager();
        this.add(container);
        this.initilizeGui(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz " + this.user.getUsername());

        loadBrandTable();
        loadBrandCompanent();


        loadModelTable(null);
        loadModelCompanent();
        loadModelFilter();

        loadCarTable();
        loadCarCompanent();

    }

    public void loadBrandCompanent() {
        tableRowSelect(this.tbl_brand);

        this.brand_menu = new JPopupMenu();
        brand_menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
            loadBrandTable();
            loadModelTable(null);
            loadModelFilter();
        });
        brand_menu.add("Güncelle").addActionListener(e -> {
            // seçili olan brandı almak gerekir.
            int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilter();
                }
            });
        });
        brand_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMsg("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilter();
                } else {
                    Helper.showMsg("error");
                }
            }

        });
        this.tbl_brand.setComponentPopupMenu(brand_menu);

    }

    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};          //veri tipini object olarak seçmek daha doğru bir yaklaşımdır.
        // ArrayList<Brand> brandList = this.brandManager.findAll();
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);

    }

//    public void loadModelTable() {
//        Object[] col_model = {"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
//        ArrayList<Object[]> modelList = this.modelManager.getForTable(col_model.length, this.modelManager.findAll());
//        this.createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
//    }

    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        if (modelList == null) {
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }
        this.createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
    }

    public void loadModelFilter() {

        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);

        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);

        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);

        loadModelFilterBrand();
    }

    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }


    public void loadModelCompanent() {
        tableRowSelect(this.tbl_model);

        this.model_menu = new JPopupMenu();
        this.model_menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                    loadBrandTable();
                    loadModelFilter();
                }
            });
        });
        this.model_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                    loadBrandTable();
                    loadModelFilter();
                }
            });
        });
        this.model_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectModelId)) {
                    Helper.showMsg("done");
                    loadModelTable(null);
                    loadBrandTable();
                    loadModelFilter();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(model_menu);

        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    //  selectedBrand.getKey(),
                    brandId,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem());
            //  System.out.println(modelListBySearch);
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.btn_clear.addActionListener(e1 -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });

    }

    public void loadCarTable() {
        Object[] col_car = {"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt", "Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        this.createTable(this.tmdl_car, this.tbl_car, col_car, carList);
    }


    public void loadCarCompanent() {
        tableRowSelect(this.tbl_car);

        this.car_menu = new JPopupMenu();
        car_menu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });

        });
        car_menu.add("Güncelle").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(tbl_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectCarId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        car_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectCarId = this.getTableSelectedRow(tbl_car, 0);
                if (this.carManager.delete(selectCarId)) {
                    Helper.showMsg("done");
                    loadCarTable();
                } else {
                    Helper.showMsg("error");
                }
            }

        });
        this.tbl_car.setComponentPopupMenu(car_menu);

    }


}
