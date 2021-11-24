package com.misiontic.holamundo04;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.misiontic.holamundo04.db.MySQLiteHelper;
import com.misiontic.holamundo04.listviews.ContactListViewAdapter;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ArrayList<String> contactList;
    private static ListView listView;
    private static ContactListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactList = new ArrayList<>();
        listView = findViewById(R.id.contactList);
        showContacts();
    }

    public void showContacts() {
        String nombre;
        String apellidos;

        MySQLiteHelper conexion_bd = new MySQLiteHelper(this);
        String sentence = "SELECT * FROM personas";

        Cursor resultados = conexion_bd.getData(sentence, null);

        try {
            resultados.moveToFirst();
            do {
                int indice = resultados.getColumnIndex("nombres");
                nombre = resultados.getString(indice);
                apellidos = resultados.getString(2);

                //lista
                contactList.add(nombre + " " + apellidos);
                adapter = new ContactListViewAdapter(this, contactList);
                listView.setAdapter(adapter);
                //

            } while (resultados.moveToNext());
        } catch (Exception e) {
            Toast.makeText(this, "Error al realizar la consulta " + e, Toast.LENGTH_LONG).show();
        } finally {
            resultados.close();
        }

    }

}