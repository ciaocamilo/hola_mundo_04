package com.misiontic.holamundo04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.misiontic.holamundo04.db.MySQLiteHelper;
import com.misiontic.holamundo04.listviews.ContactListViewAdapter;
import com.misiontic.holamundo04.model.Persona;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ArrayList<Persona> contactList;
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
        int id;
        String direccion;
        String telefono;
        String fecha_nacimiento;

        MySQLiteHelper conexion_bd = new MySQLiteHelper(this);
        String sentence = "SELECT * FROM personas";

        Cursor resultados = conexion_bd.getData(sentence, null);

        try {
            resultados.moveToFirst();
            do {
                int indice = resultados.getColumnIndex("nombres");
                nombre = resultados.getString(indice);
                apellidos = resultados.getString(2);
                id = resultados.getInt(0);
                direccion = resultados.getString(3);
                telefono = resultados.getString(4);
                fecha_nacimiento = resultados.getString(5);

                Persona nuevoContacto = new Persona(nombre, apellidos, direccion, telefono, fecha_nacimiento, null);
                nuevoContacto.setId(id);

                //lista
                contactList.add(nuevoContacto);
                //

            } while (resultados.moveToNext());

            adapter = new ContactListViewAdapter(this, contactList);
            listView.setAdapter(adapter);

            // Evento o función con toque
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Persona seleccionado = (Persona) listView.getItemAtPosition(position);
                    int idPerson = seleccionado.getId();
                    goToContactUpdate(idPerson);
                }
            });
            //

        } catch (Exception e) {
            Toast.makeText(this, "Error al realizar la consulta " + e, Toast.LENGTH_LONG).show();
        } finally {
            resultados.close();
        }

    }

    public void goToContactUpdate(int idPerson) {
        Intent intentUpdate = new Intent(this, ContactUpdateActivity.class);
        intentUpdate.putExtra("person", idPerson);
        startActivity(intentUpdate);
    }

}