package Domein;

import DAO.ReizigerDAO;
import DAO.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();

        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);
        testReizigerDAO(reizigerDAOPsql);
        closeConnection();
    }

    public static void getConnection() {
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip","postgres","Linde10");

        } catch (SQLException e) {
            System.out.println("Connectie maken met de database is mislukt");
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Het sluiten van de connectie met de database is mislukt");
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> AlleReizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : AlleReizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + AlleReizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        AlleReizigers = rdao.findAll();
        System.out.println(AlleReizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

//        //Haal reiziger op met reizigers_id
        System.out.println("[Test] ReizigerDAO.findByID() geeft de volgende reiziger:");
        Reiziger reiziger = rdao.findById(6);
        System.out.println(reiziger);
        System.out.println();

        //Haal alle reizigers op met geboortedatum
        List<Reiziger> reizigers = rdao.findByGbdatum("2002-12-03");
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

//        //Update gbdatum en tussenvoegsel voor eerder toegevoegde reiziger

        System.out.println("[Test] Sietske heeft eerst de volgende waardes");
        System.out.println(rdao.findById(77));
        System.out.println();

        String geboortedatum = "2000-01-13";
        Reiziger sietskeUpdate = new Reiziger(77,"S", "van", "Boers", java.sql.Date.valueOf(geboortedatum));
        rdao.update(sietskeUpdate);
        System.out.println("[Test] Na ReizigerDAO.update() heeft ze volgende waardes:");
        System.out.println(rdao.findById(77));
        System.out.println();

        //Delete eerder gemaakte reiziger
        System.out.print("[Test] Eerst " + AlleReizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietskeUpdate);
        AlleReizigers = rdao.findAll();
        System.out.println(AlleReizigers.size() + " reizigers\n");
    }
}
