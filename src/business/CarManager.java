package business;

import core.Helper;
import dao.BookDao;
import dao.CarDao;
import entitiy.Book;
import entitiy.Car;
import entitiy.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarManager {
    public final CarDao carDao;
    public final BookDao bookDao;

    public CarManager() {
        this.carDao = new CarDao();
        this.bookDao = new BookDao();
    }

    public Car getById(int id) {
        return this.carDao.getById(id);
    }

    public ArrayList<Car> findAll() {
        return this.carDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Car> cars) {
        ArrayList<Object[]> carList = new ArrayList<>();
        for (Car obj : cars) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getModel().getBrand().getName();
            rowObject[i++] = obj.getModel().getName();
            rowObject[i++] = obj.getPlate();
            rowObject[i++] = obj.getColor();
            rowObject[i++] = obj.getKm();
            rowObject[i++] = obj.getModel().getYear();
            rowObject[i++] = obj.getModel().getType();
            rowObject[i++] = obj.getModel().getFuel();
            rowObject[i++] = obj.getModel().getGear();
            carList.add((rowObject));
        }
        return carList;
    }

    public boolean save(Car car) {
        if (this.getById(car.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.carDao.save(car);
    }

    public boolean update(Car car) {
        if (this.getById(car.getId()) == null) {
            Helper.showMsg(car.getId() + "ID kayıtlı araç bulunamadı");
            return false;
        }
        return this.carDao.update(car);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + "ID kayıtlı araç bulunamadı");
            return false;
        }
        return this.carDao.delete(id);
    }

    public ArrayList<Car> searchByBooking(String start_date, String finish_date, Model.Type type, Model.Gear gear, Model.Fuel fuel) {
        String query = "SELECT * FROM public.car LEFT JOIN public.model  ";
        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> joinWhere = new ArrayList<>();
        ArrayList<String> bookOrWhere = new ArrayList<>();

        joinWhere.add("public.car.car_model_id = public.model.model_id");

        start_date = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        finish_date = LocalDate.parse(finish_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();


        if (gear != null) where.add("model.model_gear = '" + gear.toString() + "' ");
        if (fuel != null) where.add("model.model_fuel = '" + fuel.toString() + "' ");
        if (type != null) where.add("model.model_type = '" + type.toString() + "' ");

        String whereStr = String.join(" AND ", where);
        String joinStr = String.join(" AND ", joinWhere);
        if (joinStr.length() > 0) query += " ON " + joinStr;
        if (whereStr.length() > 0) query += " WHERE " + whereStr;

        ArrayList<Car> searchedCarList = this.carDao.selectByQuery(query);
        bookOrWhere.add(" ( '" + start_date + "' BETWEEN book_str_date AND book_fnsh_date  ) ");
        bookOrWhere.add(" ( '" + finish_date + "' BETWEEN book_str_date AND book_fnsh_date  ) ");
        bookOrWhere.add(" ( book_str_date BETWEEN '" + start_date + "' AND '" + finish_date + "' ) ");
        bookOrWhere.add(" ( book_fnsh_date BETWEEN '" + start_date + "' AND '" + finish_date + "' ) ");

        String bookOrWhereStr = String.join(" OR ", bookOrWhere);
        String bookQuery = "SELECT * FROM public.book WHERE " + bookOrWhereStr;

        ArrayList<Book> busyCarList = this.bookDao.selectByQuery(bookQuery);
        ArrayList<Integer> busyCarID = new ArrayList<>();
        for (Book book : busyCarList) {
            busyCarID.add(book.getCar_id());
        }
        searchedCarList.removeIf(car -> busyCarID.contains(car.getId()));

        // System.out.println(busyCarList);
        // System.out.println(bookQuery);

        //    System.out.println(query);
        //    return new ArrayList<>();
        //  return this.carDao.selectByQuery(query);
        return searchedCarList;
    }

}
