package P5.DAO;

import P5.Domein.OVChipkaart;
import P5.Domein.Product;

import java.util.List;

public interface ProductDAO {
    public boolean save(Product product);
    public boolean update(Product product);
    public boolean delete(Product product);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
    Product findByNummer(int id);

}
