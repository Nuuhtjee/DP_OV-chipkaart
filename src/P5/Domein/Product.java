package P5.Domein;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;


    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public List<OVChipkaart> voegOVtoe(OVChipkaart ovChipkaart){
        ovChipkaarten.add(ovChipkaart);
        return ovChipkaarten;
    }

    @Override
    public String toString() {
        String s = String.format("Product{#%s %s voor €%s}",product_nummer,naam,prijs);
        for (OVChipkaart ovChipkaart : ovChipkaarten){
            s = s + " " +  ovChipkaart.OVString();
        }
        return s;
    }

    public String productString(){
        return String.format("Product{#%s %s  voor €%s}",product_nummer,naam,prijs);
    }
}
