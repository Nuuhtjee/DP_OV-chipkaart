package P3.Domein;

import P3.DAO.AdresDAO;
import P3.DAO.AdresDAOPsql;
import P3.DAO.ReizigerDAO;
import P3.DAO.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();

        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);
        testReizigerDAO(reizigerDAOPsql);

        AdresDAOPsql adresDAOPsql = new AdresDAOPsql(connection);
        testAdresDAO(adresDAOPsql, reizigerDAOPsql);
        closeConnection();
    }



    private static void getConnection() {
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip","postgres","Linde10");

        } catch (SQLException e) {
            System.out.println("Connectie maken met de database is mislukt");
        }
    }

    private static void closeConnection() {
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
        sietske.setGeboortedatum(java.sql.Date.valueOf(geboortedatum));
        sietske.setTussenvoegsel("van");
        rdao.update(sietske);
        System.out.println("[Test] Na ReizigerDAO.update() heeft ze volgende waardes:");
        System.out.println(rdao.findById(77));
        System.out.println();

        //Delete eerder gemaakte reiziger
        System.out.print("[Test] Eerst " + AlleReizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietske);
        AlleReizigers = rdao.findAll();
        System.out.println(AlleReizigers.size() + " reizigers\n");
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> AlleAdressen = adao.findAll();
        System.out.println("[Test] adresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : AlleAdressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuwe adres aan en persisteer deze in de database
        String gbdatum = "1970-03-11";
        Reiziger tom = new Reiziger(8, "T", "van", "Schaars", java.sql.Date.valueOf(gbdatum));

        rdao.save(tom);
        Adres tomadres = new Adres(8,"3451EE","12","Tomlaan","Amsterdam",tom);

        System.out.print("[Test] Eerst " + AlleAdressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(tomadres);
        AlleAdressen = adao.findAll();
        System.out.println(AlleAdressen.size() + " adressen\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

//        //Haal adres op met reiziger_id
        System.out.println("[Test] adresDAO.findByReiziger() geeft de volgende adres:");
        Adres adres = adao.findByReiziger(tom);
        System.out.println(adres);
        System.out.println();

//        //Update postcode en huisnummer voor eerder toegevoegde adres

        System.out.println("[Test] Tom heeft eerst de volgende waardes");
        System.out.println(adao.findByReiziger(tom));
        System.out.println();

        tomadres.setPostcode("3333EE");
        tomadres.setHuisnummer("11");
        adao.update(tomadres);
        System.out.println("[Test] Na adresDAO.update() heeft hij volgende waardes:");
        System.out.println(adao.findByReiziger(tom));
        System.out.println();

        //Delete eerder gemaakte reiziger
        System.out.print("[Test] Eerst " + AlleAdressen.size() + " adressen, na adresDAO.save() ");
        adao.delete(tomadres);
        rdao.delete(tom);
        AlleAdressen = adao.findAll();
        System.out.println(AlleAdressen.size() + " adressen\n");

    }
}
