package com.duszczyk.android1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_One_Category extends Fragment {


    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<String> authors = new ArrayList<String>();
    String categoryChoosed;
    DatabaseHelper myDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        myDb = ((MainActivity)getActivity()).getMyDb();
        categoryChoosed = ((MainActivity)getActivity()).getCategory();


        View view = inflater.inflate(R.layout.fragment_fragment__one__category, container, false);

        ListView lista = (ListView)view.findViewById(R.id.listView);

        CustomAdapter customAdapter = new CustomAdapter();

        lista.setAdapter(customAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(),names.get(i), Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).setNameChoosed(names.get(i));
                ((MainActivity)getActivity()).switchToDescribe();
            }
        });








        Cursor res = myDb.getDataCategory(categoryChoosed);

        while(res.moveToNext()) {
            names.add(res.getString(1));
            authors.add(res.getString(2));
            images.add(res.getString(6));
        }

        return view;


        //return inflater.inflate(R.layout.fragment_fragment__one__category, container, false);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return names.size();
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
            view = getLayoutInflater().inflate(R.layout.singlebook, null);

            ImageView imageView =(ImageView)view.findViewById(R.id.imageView_koszyk);
            TextView textView_name = (TextView)view.findViewById(R.id.textView_name);
            TextView textView_author = (TextView)view.findViewById(R.id.textView_author);


            Picasso.get().load(images.get(i)).into(imageView);
            textView_name.setText(names.get(i));
            textView_author.setText(authors.get(i));

            return view;
        }
    }

}
