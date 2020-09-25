package P5.DAO;

import P5.Domein.OVChipkaart;
import P5.Domein.Product;
import P5.Domein.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovChipkaart);
    public boolean update(OVChipkaart ovChipkaart);
    public boolean delete(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findByReiziger(Reiziger reizigers);
    public List<OVChipkaart> findByProduct(Product product);
    public OVChipkaart findByOV(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findAll();
}
