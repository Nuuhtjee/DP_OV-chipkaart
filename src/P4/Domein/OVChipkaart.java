package P4.Domein;

import java.time.LocalDate;

public class OVChipkaart {
    private int kaartnummer;
    private LocalDate geldig_tot;
    private int klasse;
    private double saldo;
    private int reiziger_id;

    private Reiziger reiziger;

    public OVChipkaart(int kaartnummer, LocalDate geldig_tot, int klasse, double saldo, Reiziger reiziger){
        this.kaartnummer = kaartnummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger.getReiziger_id();
        this.reiziger = reiziger;
    }

    public int getKaartnummer(){
        return  kaartnummer;
    }

    public LocalDate getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse(){
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setKaartnummer(int kaartnummer){
        this.kaartnummer = kaartnummer;
    }

    public void setGeldig_tot(LocalDate geldig_tot) {
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

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartnummer=" + kaartnummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger_id=" + reiziger_id +
                '}';
    }
}
