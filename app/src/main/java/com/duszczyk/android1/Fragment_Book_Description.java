package com.duszczyk.android1;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Book_Description extends Fragment {

    DatabaseHelper myDb;
    String nameChoosed;

    String nazwa;
    String cena;
    String zdjecie;
    TextView textView_koszykSuma;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        myDb = ((MainActivity)getActivity()).getMyDb();

        View view = inflater.inflate(R.layout.fragment_fragment__book__description, container, false);


        ImageView imageView =(ImageView)view.findViewById(R.id.imageViewDescription);
        TextView textView_name = (TextView)view.findViewById(R.id.textView_nameDesc);
        TextView textView_author = (TextView)view.findViewById(R.id.textView_authorDesc);
        TextView textView_description = (TextView)view.findViewById(R.id.textView_descriptionDesc);
        TextView textView_price = (TextView)view.findViewById(R.id.textView_price);
        Button buttonAdd = (Button)view.findViewById(R.id.button_addKoszyk);



        nameChoosed = ((MainActivity)getActivity()).getNameChoosed();

        Cursor res = myDb.getDataName(nameChoosed);

        res.moveToNext();

        nazwa = res.getString(1);
        textView_name.setText(nazwa);
        textView_author.setText(res.getString(2));
        textView_description.setText(res.getString(4));
        cena = res.getString(3);
        textView_price.setText(cena);
        zdjecie = res.getString(6);
        Picasso.get().load(zdjecie).into(imageView);

        buttonAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).dodajDoKoszyka(nazwa,cena,zdjecie);
                        ((MainActivity)getActivity()).dodajDoSumy(cena);
                        refreshActionBarMenu(getActivity());
                        Toast.makeText(getActivity(),"Dodano "+nazwa+" do koszyka!", Toast.LENGTH_LONG).show();
                    }
                }
        );


        return view;
        //return inflater.inflate(R.layout.fragment_fragment__book__description, container, false);
    }

    static void refreshActionBarMenu(Activity activity)
    {
        activity.invalidateOptionsMenu();
    }

}
