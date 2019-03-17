package com.duszczyk.android1;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Fragment_Books_list extends Fragment {

    String categoryChoosed;
    ArrayList<String> kategorie = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        kategorie.clear();
        kategorie.add("Kryminały");
        kategorie.add("Fantasy");
        kategorie.add("Przygodowe");
        kategorie.add("Bajki");
        kategorie.add("Naukowe");


        View view = inflater.inflate(R.layout.fragment_fragment__books_list, container, false);

        ListView booksList = (ListView)view.findViewById(R.id.categoryListView);

        CustomAdapter customAdapter = new CustomAdapter();
        booksList.setAdapter(customAdapter);

        booksList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String category = kategorie.get(i);
                        categoryChoosed = category;
                        ((MainActivity)getActivity()).setCategory(categoryChoosed);
                        ((MainActivity)getActivity()).switchToOneCategory();
                        //Toast.makeText(getActivity(),categoryChoosed, Toast.LENGTH_LONG).show();

                    }
                }
        );



        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return kategorie.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.singlecategory, null);

            TextView textView_kategoria = (TextView)view.findViewById(R.id.textView_category);

            textView_kategoria.setText(kategorie.get(i));

            return view;
        }
    }
    public String getCategory(){
        return categoryChoosed;
    }
}





