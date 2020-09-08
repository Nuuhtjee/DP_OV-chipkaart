package P4.DAO;

import P4.Domein.OVChipkaart;
import P4.Domein.Reiziger;

import java.sql.Connection;
import java.util.List;

public class OVChipkaartDAOPsl implements OVChipkaartDAO {

    private Connection conn;

    public OVChipkaartDAOPsl(Connection conn){
        this.conn = conn;
    }

    @Override
    public OVChipkaart findByReiziger(List<Reiziger> reizigers) {
        return null;
    }
}
