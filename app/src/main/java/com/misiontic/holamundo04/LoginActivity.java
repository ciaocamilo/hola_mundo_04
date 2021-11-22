package com.misiontic.holamundo04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

        startActivity(intentMain);
    }

}