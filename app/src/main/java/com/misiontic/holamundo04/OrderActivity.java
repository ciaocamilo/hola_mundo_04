package com.misiontic.holamundo04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.misiontic.holamundo04.location.MyLocation;
import com.misiontic.holamundo04.model.Pedido;
import com.misiontic.holamundo04.model.Producto;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {

    private SharedPreferences settings; // SP

    MyLocation coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("id", Context.MODE_PRIVATE); // SP

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Button btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarPedido();
            }
        });

        // Test API
        ApiRequest api = new ApiRequest();
        ArrayList<Producto> listadoProductos = api.consultarProdctos(this);
        // Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();

        // Test location
        coordenadas = new MyLocation(this, OrderActivity.this);

    }

    public void realizarPedido() {
        CheckBox cbPollo = findViewById(R.id.cbPollo);
        CheckBox cbSalami = findViewById(R.id.cbSalami);
        CheckBox cbJamon = findViewById(R.id.cbJamon);

        String strPedido = getString(R.string.strOrderBase);
        // Pedido realizado con los siguientes ingredientes:

        if (cbPollo.isChecked()) {
            strPedido = strPedido.concat(" pollo ");
        }
        if (cbSalami.isChecked()) {
            strPedido = strPedido.concat("salami ");
        }
        if (cbJamon.isChecked()) {
            strPedido = strPedido.concat("jamón ");
        }

        //SP
            String usuario = settings.getString("usuario", "error");
        //


        String ubicacion = coordenadas.getLatitud() +"," + coordenadas.getLongitud();

        // API
        Pedido nuevoPedido = new Pedido(usuario, strPedido, 1200.0, ubicacion);
        ApiRequest api = new ApiRequest();
        api.guardarPedido(nuevoPedido, this);
        //

        strPedido = strPedido.concat(" para el señor(a) " + usuario);

        Toast.makeText(this, strPedido, Toast.LENGTH_LONG).show();

    }

}