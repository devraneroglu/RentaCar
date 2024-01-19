package dao;

import core.Db;
import entitiy.Brand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection con;

    public BrandDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()) {
                brandList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public Brand match(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getInt("brand_id"));
        brand.setName(rs.getString("brand_name"));
        return brand;
    }


}
