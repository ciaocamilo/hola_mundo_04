package com.misiontic.holamundo04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.misiontic.holamundo04.db.MySQLiteHelper;

public class PersonFormActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etLastname;
    private EditText etAddress;
    private EditText etPhone;
    private EditText etBirthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_form);

        etName = findViewById(R.id.etFormPersonName);
        etLastname = findViewById(R.id.etFormLastname);
        etAddress = findViewById(R.id.etFormAddress);
        etPhone = findViewById(R.id.etFormPhone);
        etBirthday = findViewById(R.id.etFormBirthday);
    }

    public void guardarFormulario(View view) {
        String name = etName.getText().toString();
        String lastname = etLastname.getText().toString();
        String address = etAddress.getText().toString();
        String phone = etPhone.getText().toString();
        String birthday = etBirthday.getText().toString();

        // DB
        MySQLiteHelper conexion_bd = new MySQLiteHelper(this);
        String insert_query = "INSERT INTO personas (nombres, apellidos, direccion, telefono, fecha_nacimiento) " +
                "VALUES ('"+name+"', '"+lastname+"', '"+address+"', '"+phone+"', '"+birthday+"')";
        conexion_bd.insertData(insert_query);
        //
    }


}