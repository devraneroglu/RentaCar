package view;

import business.BookManager;
import core.Helper;
import entitiy.Book;
import entitiy.Car;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class BookingView extends Layaout {
    private JPanel container;
    private JTextField flb_customer_name;
    private JTextField fld_TCkn;
    private JTextField fld_telnr;
    private JTextField fld_eposta;
    private JTextField fld_begdate;
    private JTextField fld_enddate;
    private JTextField fld_tutar;
    private JTextField fld_notes;
    private JButton btn_save_book;
    private JLabel lbl_car_info;
    private Car car;
    private BookManager bookManager;


    public BookingView(Car selectedCar, String strt_date, String enddate) {
        this.car = selectedCar;
        this.bookManager = new BookManager();
        //  this.container = new JPanel();
        this.add(container);
        this.initilizeGui(300, 600);
        lbl_car_info.setText("Araç : " + this.car.getPlate() + " -- " + this.car.getModel().getBrand().getName() + " -- " + this.car.getModel().getName());

        this.fld_begdate.setText(strt_date);
        this.fld_enddate.setText(enddate);

        btn_save_book.addActionListener(e -> {
            JTextField[] checkFieldLsit = {
                    this.flb_customer_name,
                    this.fld_TCkn,
                    this.fld_telnr,
                    this.fld_eposta,
                    this.fld_tutar,
                    this.fld_notes,
                    this.fld_begdate,
                    this.fld_enddate
            };
            if (Helper.isFieldListEmpty(checkFieldLsit)) {
                Helper.showMsg("fill");
            } else {
                Book book = new Book();
                book.setbCase("done");
                book.setCar_id(this.car.getId());
                book.setName(this.flb_customer_name.getText());
                book.setStrt_date(LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFnsh_date(LocalDate.parse(enddate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setIdno(this.fld_TCkn.getText());
                book.setMpno(this.fld_telnr.getText());
                book.setMail(this.fld_eposta.getText());
                book.setNote(this.fld_notes.getText());
                book.setPrc(Integer.parseInt(this.fld_tutar.getText()));

                if (this.bookManager.save(book)) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }


}
