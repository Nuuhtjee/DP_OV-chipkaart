package P4.DAO;

import P4.Domein.OVChipkaart;
import P4.Domein.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovChipkaart);
    public boolean update(OVChipkaart ovChipkaart);
    public boolean delete(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findByReiziger(Reiziger reizigers);
    public OVChipkaart findByOV(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findAll();
}
