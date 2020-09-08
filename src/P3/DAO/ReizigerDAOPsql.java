package P3.DAO;

import P3.Domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {


    private Connection conn;
    private AdresDAO adao;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        String query = "INSERT INTO reiziger (reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VAlUES (?,?,?,?,?)";
        boolean result = false;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,reiziger.getReiziger_id());
            pst.setString(2,reiziger.getVoorletters());
            pst.setString(3,reiziger.getTussenvoegsel());
            pst.setString(4,reiziger.getAchternaam());
            pst.setDate(5, (Date) reiziger.getGeboortedatum());
            result = pst.execute();
        } catch (SQLException e) {
            System.out.println("Het maken van een nieuwe reiziger en opslaan in de database is mislukt");
        }
        return result;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        String query = "UPDATE reiziger  set reiziger_id = ?, voorletters = ?, tussenvoegsel = ? , achternaam = ?, geboortedatum = ? where reiziger_id = ?";
        boolean result = false;
        try{
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,reiziger.getReiziger_id());
            pst.setString(2,reiziger.getVoorletters());
            pst.setString(3,reiziger.getTussenvoegsel());
            pst.setString(4,reiziger.getAchternaam());
            pst.setDate(5, (Date) reiziger.getGeboortedatum());
            pst.setInt(6,reiziger.getReiziger_id());
            result = pst.execute();
    } catch (SQLException e) {
            System.out.println("Het updaten van een reiziger is mislukt");
    }
        return result;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        String query = "DELETE from reiziger where reiziger_id = ?";
        boolean result = false;
        try{
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,reiziger.getReiziger_id());
            result = pst.execute();
        } catch (SQLException e) {
            System.out.println("Het verwijderen van een reiziger is mislukt");
        }
        return result;
    }

    @Override
    public Reiziger findById(int id) {
        String query = "SELECT * From reiziger where reiziger_id=?";
        Reiziger temp = null;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                temp = new Reiziger(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5));
            }
        } catch (SQLException e) {
            System.out.println("Het vinden van een reiziger met het gegeven ID is mislukt");
        }
        return temp;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        String query = "SELECT * From reiziger where geboortedatum= ?";
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDate(1,java.sql.Date.valueOf(datum));
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                reizigers.add(new Reiziger(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5)));
            }
        } catch (SQLException e) {
            System.out.println("Het vinden van een reiziger met het gegeven geboortedatum is mislukt");

        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() {
        adao = new AdresDAOPsql(conn);
        String query = "SELECT * From reiziger";
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Reiziger temp = new Reiziger(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5));
                reizigers.add(temp);
                adao.findByReiziger(temp);
            }
        } catch (SQLException e) {
            System.out.println("Het vinden van alle reizigers is mislukt");
        }
        return reizigers;
    }
}
