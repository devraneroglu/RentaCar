package dao;

import core.Db;
import core.Helper;
import entitiy.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String sql = "SELECT * FROM public.brand ORDER BY brand_id ASC";
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

    public Boolean save(Brand brand) {
        String quuery = "INSERT INTO public.brand ( brand_name) VALUES (?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(quuery);
            pr.setString(1, brand.getName());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    // amacı
    public Brand getById(int id) {
        Brand obj = null;
        String query = "SELECT * FROM public.brand WHERE brand_id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Boolean update(Brand brand) {
        String query = "UPDATE public.brand SET brand_name = ? WHERE brand_id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, brand.getName());
            pr.setInt(2, brand.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean delete(int id) {
        String query = "DELETE FROM public.brand WHERE brand_id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Brand match(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getInt("brand_id"));
        brand.setName(rs.getString("brand_name"));
        return brand;
    }


}
