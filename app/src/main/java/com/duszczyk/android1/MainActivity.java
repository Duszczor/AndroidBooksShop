package com.duszczyk.android1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper myDb;
    String categoryChoosed;
    String nameChoosed;
    Toolbar toolbar;
    int liczbaKoszyk = 0;
    float suma_koszyk=0.0f;
    TextView textView_koszykSuma;

    List<PozycjaKoszyk> listaKoszyk = new ArrayList<PozycjaKoszyk>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FragmentDatabase()).addToBackStack(null).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_koszyj,menu);
        final MenuItem item = menu.findItem(R.id.koszykIlosc);
        item.setTitle("Suma: " +format("%.2f%n", suma_koszyk)+" ZL");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int res_id = item.getItemId();
        if(res_id==R.id.koszykFoto){
            //Toast.makeText(getApplicationContext(), "Liczba ksiazek w koszyku: "+liczbaKoszyk, Toast.LENGTH_LONG).show();
            switchToKoszyk();
        }
//        if(res_id==R.id.koszykIlosc){
//            View view = getLayoutInflater().inflate(R.layout.toolbar, null);
//            textView_koszykSuma = (TextView)view.findViewById(R.id.textView_koszykIlosc);
//            textView_koszykSuma.setText("Suma: "+suma_koszyk+"ZL");
//            //Toast.makeText(getApplicationContext(), "SUMA KOSZYKA WYNOSI: "+suma_koszyk+" ZL", Toast.LENGTH_LONG).show();
//        }
        return true;
    }

    public void switchToKoszyk(){
        Fragment_koszyk fragBooks = new Fragment_koszyk();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragBooks).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void switchToBooksList() {
        Fragment_Books_list fragBooks = new Fragment_Books_list();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragBooks).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void switchToOneCategory() {
        Fragment_One_Category fragBooks = new Fragment_One_Category();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragBooks).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void switchToDescribe() {
        Fragment_Book_Description fragBooks = new Fragment_Book_Description();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragBooks).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void setCategory(String cat){
        categoryChoosed = cat;
    }

    public String getCategory(){
        return categoryChoosed;
    }

    public DatabaseHelper getMyDb() {
        return myDb;
    }

    public void setMyDb(DatabaseHelper data){
        myDb = data;
    }

    public void setNameChoosed(String name){
        nameChoosed = name;
    }

    public String getNameChoosed(){
        return nameChoosed;
    }

    public void setLiczbaKoszyk(int a){
        liczbaKoszyk = a;
    }

    public int getLiczbaKoszyk(){
        return liczbaKoszyk;
    }

    public void addOneKoszyk(){
        liczbaKoszyk+=1;
    }

    public boolean czyJest(String name){
        for(int i=0;i<listaKoszyk.size();i++){
            if(listaKoszyk.get(i).nazwa==name){
                return true;
            }
        }
        return false;
    }

    public void zwiekszLiczbe(String name){
        for(int i=0;i<listaKoszyk.size();i++){
            if(listaKoszyk.get(i).nazwa.contains(name)){
                listaKoszyk.get(i).zwiekszLiczbe();
            }
        }
    }

    public void dodajDoKoszyka(String nazwa, String cena, String zdjecie){
        if(!czyJest(nazwa)){
            listaKoszyk.add(new PozycjaKoszyk(nazwa,cena,zdjecie));
        } else {
            zwiekszLiczbe(nazwa);
        }
    }

    public void dodajDoSumy(String cena){
        suma_koszyk += Float.valueOf(cena.toString());
    }

    public void odejmijOdSumy(String cena){
        suma_koszyk -= Float.valueOf(cena.toString());
    }

    public void usunLubZmien(String name){
        boolean koniec = true;
        for(int i=0;i<listaKoszyk.size() && koniec;i++){
            if(listaKoszyk.get(i).nazwa.contains(name)){
                koniec = false;
                if(listaKoszyk.get(i).ilosc > 1){
                    listaKoszyk.get(i).ilosc -=1;
                } else {
                    listaKoszyk.remove(i);
                }
            }
        }
    }

    public void setLista(List<PozycjaKoszyk> listaKoszyk1){
        listaKoszyk = listaKoszyk1;
    }

    public List<PozycjaKoszyk> getLista(){
        return listaKoszyk;
    }

}
