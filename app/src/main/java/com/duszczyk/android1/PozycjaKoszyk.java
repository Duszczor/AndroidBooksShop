package com.duszczyk.android1;

public class PozycjaKoszyk {

    public String nazwa;
    public String cena;
    public String zdjecie;
    public int ilosc;

    public PozycjaKoszyk(String naz, String cen, String zdj){
        nazwa = naz;
        cena = cen;
        zdjecie = zdj;
        ilosc = 1;
    }

    public void zwiekszLiczbe(){
        ilosc += 1;
    }

}
