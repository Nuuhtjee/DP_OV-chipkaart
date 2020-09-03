package P3.Domein;

public class Adres {

    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;


    private Reiziger reiziger;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger){
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
        this.reiziger_id = reiziger.getReiziger_id();
        reiziger.setAdres(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        String s = String.format("Adres{# %s %s %s} %s ",id,woonplaats,postcode,reiziger.reizigerString());
        return s;
    }

    public String adresString(){
        String s = String.format("Adres{# %s %s %s}",id,woonplaats,postcode);
        return s;
    }
}
