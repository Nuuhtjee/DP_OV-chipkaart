package P4.Domein;

import java.sql.Date;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;

    private Reiziger reiziger;

    public OVChipkaart(int kaartnummer, Date geldig_tot, int klasse, double saldo, Reiziger reiziger){
        this.kaartnummer = kaartnummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaartnummer(){
        return  kaartnummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse(){
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setKaartnummer(int kaartnummer){
        this.kaartnummer = kaartnummer;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public void setKlasse(int klasse){
        this.klasse = klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }


    @Override
    public String toString() {
        String s = String.format("OV Chipkaart{#%s %s} %s",kaartnummer,saldo,reiziger.reizigerString());
        return s;
    }

    public String OVString(){
        String s = String.format("OV Chipkaart{#%s %s}",kaartnummer,saldo);
        return s;
    }
}
