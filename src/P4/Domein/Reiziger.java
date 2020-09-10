package P4.Domein;

import java.util.Date;
import java.util.List;

public class Reiziger {

    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    private Adres adres;
    private List<OVChipkaart> OVchipkaarten;

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres){
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres(){
        return adres;
    }
    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOVchipkaarten(){
        return OVchipkaarten;
    }

    public void voegOVChipkaartToe(OVChipkaart ov){
        OVchipkaarten.add(ov);
    }

    public void verwijderOVChipkaart(OVChipkaart ov){
        OVchipkaarten.remove(ov);
    }

    public void setOVchipkaarten(List<OVChipkaart> OVchipkaarten) {
        this.OVchipkaarten = OVchipkaarten;
    }

    public String toString(){
        String s = String.format("Reiziger {#%s: %s. %s %s (%s)} %s", reiziger_id,voorletters,tussenvoegsel == null ? "" : tussenvoegsel,achternaam,geboortedatum, adres == null ? "" : adres.adresString());
        for (OVChipkaart ovChipkaart : OVchipkaarten){
            s = s + " " +  ovChipkaart.OVString();
        }
        return s;
    }

    public String reizigerString(){
        String s = String.format("Reiziger {#%s: %s. %s %s (%s)}", reiziger_id,voorletters,tussenvoegsel == null ? "" : tussenvoegsel,achternaam,geboortedatum) ;
        return s;
    }
}
