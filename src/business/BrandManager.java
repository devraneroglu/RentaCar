package business;

import core.Helper;
import dao.BrandDao;
import entitiy.Brand;
import entitiy.Model;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;
    private final ModelManager modelManager;

    public BrandManager() {
        this.brandDao = new BrandDao();
        this.modelManager = new ModelManager();

    }

    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size) {             //kolon sayısı kadar içeride object oluşturuduk.
        ArrayList<Object[]> brandRowList = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRowList.add(rowObject);
        }
        return brandRowList;
    }

    public boolean save(Brand brand) {
        if (brand.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.brandDao.save(brand);
    }

    public Brand getById(int id) {
        return this.brandDao.getById(id);
    }

    public Boolean update(Brand brand) {
        if (this.getById(brand.getId()) == null) {
            Helper.showMsg("notFound");
        }
        return this.brandDao.update(brand);
    }

    public Boolean delete(int id) {                       // ID yi kontrol et eğer input id db de varsa koşul sağlanıyorsa --> Dao daki delete e git;
        if (this.getById(id) == null) {
            Helper.showMsg(id + "ID kayıtlı marka bulunamadı ");
            return false;
        }
        for (Model model : this.modelManager.getByListBrandId(id)) {
            this.modelManager.delete(model.getId());
        }
        return this.brandDao.delete(id);
    }
}
