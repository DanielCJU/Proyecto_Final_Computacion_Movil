package com.example.proyecto_final_computacion_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto_final_computacion_movil.DB.DBUserAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Start = findViewById(R.id.startBtn);
        Button Add = findViewById(R.id.addBtn);
        Button P = findViewById(R.id.Pbutton);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "admin";
                String password = "123";
                try{
                    DBUserAdapter dbUser = new DBUserAdapter(MainActivity.this);
                    dbUser.open();
                    if(dbUser.Login(username, password)){
                        Toast.makeText(MainActivity.this,"Admin already created", Toast.LENGTH_LONG).show();
                    } else {
                        dbUser.AddUser(username,password);
                        Toast.makeText(MainActivity.this,"Admin created", Toast.LENGTH_LONG).show();
                    }
                    dbUser.close();
                }catch(Exception e)
                {
                    Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "admin";
                try{
                    DBUserAdapter dbUser = new DBUserAdapter(MainActivity.this);
                    dbUser.open();
                    if(dbUser.findId(username)==1){
                        Toast.makeText(MainActivity.this,"Funciona " +dbUser.findId(username), Toast.LENGTH_LONG).show();
                        dbUser.close();
                    }
                    dbUser.close();
                }catch(Exception e)
                {
                    Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}