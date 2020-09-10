package P4.DAO;

import P4.Domein.OVChipkaart;
import P4.Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsl implements OVChipkaartDAO {

    private Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsl(Connection conn){
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        String query = "INSERT INTO ov_chipkaart (kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES(?,?,?,?,?)";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,ovChipkaart.getKaartnummer());
            pst.setDate(2,ovChipkaart.getGeldig_tot());
            pst.setInt(3,ovChipkaart.getKlasse());
            pst.setDouble(4,ovChipkaart.getSaldo());
            pst.setInt(5,ovChipkaart.getReiziger().getReiziger_id());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String query = "UPDATE ov_chipkaart set kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? where kaart_nummer = ?";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,ovChipkaart.getKaartnummer());
            pst.setDate(2,ovChipkaart.getGeldig_tot());
            pst.setInt(3,ovChipkaart.getKlasse());
            pst.setDouble(4,ovChipkaart.getSaldo());
            pst.setInt(5,ovChipkaart.getReiziger().getReiziger_id());
            pst.setInt(6,ovChipkaart.getKaartnummer());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        String query = "DELETE FROM ov_chipkaart where kaart_nummer = ?";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,ovChipkaart.getKaartnummer());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,reiziger.getReiziger_id());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                OVChipkaart temp = new OVChipkaart(rs.getInt(1),rs.getDate(2),rs.getInt(3),rs.getDouble(4),reiziger);
                ovChipkaarten.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ovChipkaarten;
    }

    @Override
    public OVChipkaart findByOV(OVChipkaart ovChipkaart) {
        String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";
        OVChipkaart temp = null;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,ovChipkaart.getKaartnummer());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                temp = new OVChipkaart(rs.getInt(1),rs.getDate(2),rs.getInt(3),rs.getDouble(4),rdao.findById(rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public List<OVChipkaart> findAll() {
        String query = "SELECT * FROM ov_chipkaart";
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                OVChipkaart temp = new OVChipkaart(rs.getInt(1),rs.getDate(2),rs.getInt(3),rs.getDouble(4),rdao.findById(rs.getInt(5)));
                ovChipkaarten.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ovChipkaarten;
    }
}
