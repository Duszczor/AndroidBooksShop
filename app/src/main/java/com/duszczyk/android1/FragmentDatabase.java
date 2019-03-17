package com.duszczyk.android1;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


public class FragmentDatabase extends Fragment {


    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    EditText editName, editSurname, editPrice, editDescribe, editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnViewUpdate;
    Button btnDelete;
    Button btnBooks;
    Button btnClear;
    Button btnAddB;
    String actualSpinner = "Kryminały";
    DatabaseHelper myDb;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_database, container, false);

        myDb = ((MainActivity)getActivity()).getMyDb();

        editName = (EditText)view.findViewById(R.id.editText_name);
        editSurname = (EditText)view.findViewById(R.id.editText_surname);
        editPrice = (EditText)view.findViewById(R.id.editText_price);
        editDescribe = (EditText)view.findViewById(R.id.editText_describe);
        editTextId = (EditText)view.findViewById(R.id.editText_ID);

        btnAddData = (Button)view.findViewById(R.id.button_add);
        btnViewAll = (Button)view.findViewById(R.id.button_view);
        btnViewUpdate = (Button)view.findViewById(R.id.button_update);
        btnDelete = (Button)view.findViewById(R.id.button_del);
        btnBooks = (Button)view.findViewById(R.id.button_books);

        btnClear = (Button)view.findViewById(R.id.button_clear);
        btnAddB = (Button)view.findViewById(R.id.button_addBooks);


        //get the spinner from the xml.
        spinner = (Spinner)view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.books_kinds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i)+" selected", Toast.LENGTH_LONG).show();
                actualSpinner = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        addBooks();
        clearBooks();
        Books();
        ((MainActivity)getActivity()).setMyDb(myDb);


        return view;

    }
    public void Books() {
        btnBooks.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).switchToBooksList();
                    }
                }
        );
    }
    public void addBooks() {
        btnAddB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources res = getResources();
                        String[] books = res.getStringArray(R.array.books);
                        for(int i=0;i<books.length-1;i=i+6) {
                            myDb.insertDataImage(books[i], books[i+1], Float.valueOf(books[i+2].toString()), books[i+3], books[i+4], books[i+5]);
                        }
                        Toast.makeText(getActivity(), "Books has been added!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void clearBooks(){
        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteAll();
                        if(deleteRows > 0)
                            Toast.makeText(getActivity(), "Data Cleared", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Data not Cleared", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(editTextId.getText().toString());
                        if(deleteRows > 0)
                            Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData() {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(), editSurname.getText().toString(),
                                Float.valueOf(editPrice.getText().toString()), editDescribe.getText().toString(),
                                spinner.getSelectedItem().toString());
                        if(isUpdate == true)
                            Toast.makeText(getActivity(),"Data Update", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(),"Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show massage
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Surname Author :"+ res.getString(2)+"\n");
                            buffer.append("Price :"+ res.getString(3)+"\n");
                            buffer.append("Describe :"+ res.getString(4)+"\n");
                            buffer.append("Category :"+ res.getString(5)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(), Float.valueOf(editPrice.getText().toString()),
                                editDescribe.getText().toString(), actualSpinner);
                        if(isInserted == true)
                            Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }





}
