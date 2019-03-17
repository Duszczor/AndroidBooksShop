package com.duszczyk.android1;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_koszyk extends Fragment {

    public List<PozycjaKoszyk> listaK = new ArrayList<PozycjaKoszyk>();
    DatabaseHelper myDb;
    TextView textView_koszykSuma;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myDb = ((MainActivity)getActivity()).getMyDb();

        listaK.clear();
        listaK.addAll(((MainActivity)getActivity()).listaKoszyk);

        View view = inflater.inflate(R.layout.fragment_fragment_koszyk, container, false);

        ListView lista = (ListView)view.findViewById(R.id.listaKoszyk);

        CustomAdapter customAdapter = new CustomAdapter();

        lista.setAdapter(customAdapter);




        return view;

        //return inflater.inflate(R.layout.fragment_fragment_koszyk, container, false);
    }

    static void refreshActionBarMenu(Activity activity)
    {
        activity.invalidateOptionsMenu();
    }

    public void usunLubZmien(String name){
        boolean koniec = true;
        for(int i=0;i<listaK.size() && koniec;i++){
            if(listaK.get(i).nazwa.contains(name)){
                koniec = false;
                if(listaK.get(i).ilosc > 1){
                    ((MainActivity)getActivity()).odejmijOdSumy(listaK.get(i).cena);
                    listaK.get(i).ilosc -=1;
                } else {
                    ((MainActivity)getActivity()).odejmijOdSumy(listaK.get(i).cena);
                    listaK.remove(i);
                }
            }
        }
        ((MainActivity)getActivity()).setLista(listaK);
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listaK.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.singlekoszyk, null);

            ImageView imageView =(ImageView)view.findViewById(R.id.imageView_koszyk);
            TextView textView_name = (TextView)view.findViewById(R.id.textView_koszykName);
            TextView textView_Cena = (TextView)view.findViewById(R.id.textView_koszykCena);
            TextView textView_Ilosc = (TextView)view.findViewById(R.id.textView_koszykIlosc);
            Button button_dodaj = (Button)view.findViewById(R.id.button_dodaj);
            Button button_minus = (Button)view.findViewById(R.id.button_minus);

//            Picasso.get().load(((MainActivity)getActivity()).listaKoszyk.get(i).zdjecie).into(imageView);
//            textView_name.setText(((MainActivity)getActivity()).listaKoszyk.get(i).nazwa);
//            textView_Ilosc.setText("Ilosc: "+((MainActivity)getActivity()).listaKoszyk.get(i).ilosc);
//            float cena = Float.valueOf(((MainActivity)getActivity()).listaKoszyk.get(i).cena.toString());
//            float nowa_cena = cena*((MainActivity)getActivity()).listaKoszyk.get(i).ilosc;
//            textView_Cena.setText("Cena: "+nowa_cena);

            Picasso.get().load(listaK.get(i).zdjecie).into(imageView);
            textView_name.setText(listaK.get(i).nazwa);
            textView_Ilosc.setText("Ilosc: "+(listaK.get(i).ilosc));
            float cena = Float.valueOf(listaK.get(i).cena.toString());
            float nowa_cena = cena*(listaK.get(i).ilosc);
            textView_Cena.setText("Cena: "+nowa_cena);


            button_minus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //do something
                    usunLubZmien(listaK.get(i).nazwa);
                    notifyDataSetChanged();
                    refreshActionBarMenu(getActivity());

                }
            });
            button_dodaj.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listaK.get(i).ilosc+=1;
                    ((MainActivity)getActivity()).setLista(listaK);
                    ((MainActivity)getActivity()).dodajDoSumy(listaK.get(i).cena);
                    notifyDataSetChanged();
                    refreshActionBarMenu(getActivity());

                }
            });

            return view;
        }
    }

}
