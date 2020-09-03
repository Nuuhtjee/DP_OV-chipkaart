package P3.DAO;

import P3.Domein.Adres;
import P3.Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn){
        this.conn = conn;
        this.rdao = new ReizigerDAOPsql(conn);
    }


    @Override
    public boolean save(Adres adres) {
        String query = "INSERT into adres (adres_id, postcode, huisnummer,straat, woonplaats,reiziger_id) VALUES (?,?,?,?,?,?)";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,adres.getId());
            pst.setString(2,adres.getPostcode());
            pst.setString(3,adres.getHuisnummer());
            pst.setString(4,adres.getStraat());
            pst.setString(5,adres.getWoonplaats());
            pst.setInt(6,adres.getReiziger().getReiziger_id());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Het maken van een nieuwe adres is mislukt");
        }
        return result;
    }

    @Override
    public boolean update(Adres adres) {
        String query = "Update adres set adres_id = ?, postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? where adres_id = ?";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,adres.getId());
            pst.setString(2,adres.getPostcode());
            pst.setString(3,adres.getHuisnummer());
            pst.setString(4,adres.getStraat());
            pst.setString(5,adres.getWoonplaats());
            pst.setInt(6,adres.getReiziger().getReiziger_id());
            pst.setInt(7,adres.getId());

            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Het updaten van het adres is mislukt");
        }
        return result;
    }

    @Override
    public boolean delete(Adres adres) {
        String query = "Delete from adres where adres_id = ?";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,adres.getId());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Het verwijderen van het adres is mislukt");
        }
        return result;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        String query = "Select * from adres where reiziger_id = ?";
        Adres adres = null;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,reiziger.getReiziger_id());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                adres = new Adres(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rdao.findById(rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adres;
    }

    @Override
    public List<Adres> findAll() {
        String query = "Select * from adres";
        List<Adres> adres = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                adres.add(new Adres(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rdao.findById(rs.getInt(6))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adres;
    }
}
