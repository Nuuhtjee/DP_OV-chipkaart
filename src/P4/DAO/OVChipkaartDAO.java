package P4.DAO;

import P4.Domein.OVChipkaart;
import P4.Domein.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public OVChipkaart findByReiziger(List<Reiziger> reizigers);
}
