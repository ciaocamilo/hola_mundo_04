package com.misiontic.holamundo04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misiontic.holamundo04.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText etName;
    private SharedPreferences settings; // SP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences("id", Context.MODE_PRIVATE); // SP
    }

    public void goToMain(View view) {
        etName = findViewById(R.id.etPersonName);
        String name = etName.getText().toString();

        Intent intentMain = new Intent(this, MainActivity.class);

        intentMain.putExtra("user", name);

        // SP
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", 1);
        editor.putString("usuario", name);
        editor.commit();
        //

        pruebaLectura(name);
        startActivity(intentMain);
    }

    public void goToNewUser(View view) {
        Intent newUserIntent = new Intent(this, NewUserActivity.class);
        startActivity(newUserIntent);
    }

    public void pruebaLectura(String nombre) { //FB
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios");

        myRef.child("u_" + nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Usuario value = dataSnapshot.getValue(Usuario.class);
                Toast.makeText(LoginActivity.this, "Value is: " + value.getNombre(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Failed to read value." + error.toException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}