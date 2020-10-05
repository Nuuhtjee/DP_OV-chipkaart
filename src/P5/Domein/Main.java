package P5.Domein;

import P5.DAO.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();

        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        OVChipkaartDAOPsl odao = new OVChipkaartDAOPsl(connection);
        ProductDAOPsql pdao = new ProductDAOPsql(connection);

        rdao.setAdao(adao);
        rdao.setOdao(odao);
        adao.setRdao(rdao);
        odao.setRdao(rdao);
        odao.setPdao(pdao);
        pdao.setOdao(odao);

        testReizigerDAO(rdao);
        testAdresDAO(adao, rdao);
        testOVChipkaartDAO(odao,rdao);
        testProductDAO(pdao,rdao,odao);

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
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum),null);
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
        sietske.setGeboortedatum(Date.valueOf(geboortedatum));
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
        Reiziger tom = new Reiziger(8, "T", "van", "Schaars", Date.valueOf(gbdatum),null);

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

        //Delete eerder gemaakte adres
        System.out.print("[Test] Eerst " + AlleAdressen.size() + " adressen, na adresDAO.save() ");
        adao.delete(tomadres);
        rdao.delete(tom);
        AlleAdressen = adao.findAll();
        System.out.println(AlleAdressen.size() + " adressen\n");

    }

    private static void testOVChipkaartDAO(OVChipkaartDAOPsl odao, ReizigerDAOPsql rdao) {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle ov chipkaarten op uit de database
        List<OVChipkaart> AlleOVChipkaarten = odao.findAll();
        System.out.println("[Test] OVDAO.findAll() geeft de volgende OV chipkaarten:");
        for (OVChipkaart o: AlleOVChipkaarten) {
            System.out.println(o);
        }
        System.out.println();

        // Maak een nieuwe ov chipkaart aan en persisteer deze in de database
        String gbdatum = "1990-03-11";
        Reiziger jan = new Reiziger(10, "J", "van", "Gaars", Date.valueOf(gbdatum),null);

        rdao.save(jan);
        String geldig = "1990-03-11";
        OVChipkaart janOV = new OVChipkaart(12345,Date.valueOf(geldig),1,500.00,jan);
        OVChipkaart janOV1 = new OVChipkaart(54321,Date.valueOf(geldig),1,20.00,jan);

        System.out.print("[Test] Eerst " + AlleOVChipkaarten.size() + " OV Chipkaarten, na OVDAO.save() ");
        odao.save(janOV);
        odao.save(janOV1);
        AlleOVChipkaarten = odao.findAll();
        System.out.println(AlleOVChipkaarten.size() + " OV chipkaarten\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

//        //Haal ov chipkaarten op met reiziger_id
        System.out.println("[Test] OVDAO.findByReiziger() geeft de volgende OV Chipkaarten:");
        List<OVChipkaart> ovChipkaartenJan = odao.findByReiziger(jan);

        for (OVChipkaart o: ovChipkaartenJan) {
            System.out.println(o);
        }
        System.out.println();

//        //Update klasse en saldo voor eerder toegevoegde ov chipkaart
        
        System.out.println("[Test] Jan heeft eerst de volgende waardes op zijn eerste OV");
        System.out.println(odao.findByOV(janOV));
        System.out.println();

        janOV.setKlasse(2);
        janOV.setSaldo(5.00);
        odao.update(janOV);

        System.out.println("[Test] Na OVDAO.update() heeft hij volgende waardes:");
        System.out.println(odao.findByOV(janOV));
        System.out.println();

        //Delete eerder gemaakte reiziger
        System.out.print("[Test] Eerst " + AlleOVChipkaarten.size() + " OV chipkaarten, na OVDAO.delete() ");
        odao.delete(janOV);
        odao.delete(janOV1);
        rdao.delete(jan);
        AlleOVChipkaarten = odao.findAll();
        System.out.println(AlleOVChipkaarten.size() + " OV chipkaarten\n");
    }

    private static void testProductDAO(ProductDAO pdao, ReizigerDAOPsql rdao, OVChipkaartDAOPsl odao) {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle producten op uit de database
        List<Product> AlleProducten = pdao.findAll();
        System.out.println("[Test] pdao.findAll() geeft de volgende producten:");
        for (Product p: AlleProducten) {
            System.out.println(p);
        }
        System.out.println();

        // Maak een product aan en persisteer deze in de database
        Product newProduct = new Product(7,"Senioren","Korting voor de oude mensen",26.00);



        System.out.print("[Test] Eerst " + AlleProducten.size() + " producten, na pdao.save() ");
        pdao.save(newProduct);
        AlleProducten = pdao.findAll();
        System.out.println(AlleProducten.size() + " producten\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

//        //Haal producten op met ov chipkaart
        System.out.println("[Test] pdao.findByOVChipkaart geeft de volgende producten:");
        Reiziger reiziger = rdao.findById(2);
        List<OVChipkaart> ovChipkaart = odao.findByReiziger(reiziger);
        List<Product> producten = pdao.findByOVChipkaart(ovChipkaart.get(0));
        Product product2 = pdao.findByNummer(2);

        for (Product prod: producten) {
            System.out.println(prod);
        }
        System.out.println();

//        //Update naam voor eerder toegevoegde product

        System.out.println("[Test] Product nr 7 heeft eerst de volgende waardes");
        System.out.println(pdao.findByNummer(7));
        System.out.println();

        newProduct.setNaam("Senioren reisproduct");
        pdao.update(newProduct);

        System.out.println("[Test] Na pdao.update() heeft het de volgende waardes:");
        System.out.println(pdao.findByNummer(7));
        System.out.println();

//        Synchronizeren Test

        System.out.println("[Test] Eerst " + " producten in de java list ");
        System.out.println(producten);

        producten.removeIf(product -> product.getProduct_nummer() == 2);
        System.out.println("[Test] Na functie " + " producten in de java list ");
        System.out.println(producten);

        System.out.println("[Test] Eerst " + " producten in de Database");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart.get(0)));

        pdao.update(product2);
        
        System.out.println("[Test] Na Update " + " producten in de Database");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart.get(0)));
        System.out.println();

        //Delete eerder gemaakte reiziger
        System.out.print("[Test] Eerst " + AlleProducten.size() + " producten, na pdao.delete() ");
        pdao.delete(newProduct);
        AlleProducten = pdao.findAll();
        System.out.println(AlleProducten.size() + " producten\n");
    }
}
