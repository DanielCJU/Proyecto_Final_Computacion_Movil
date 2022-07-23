package com.example.proyecto_final_computacion_movil;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.os.Bundle;

import com.example.proyecto_final_computacion_movil.DB.DBUserAdapter;

public class PassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        ListView lvPass = (ListView) findViewById(R.id.lvContraseñas);
        final EditText eTNuevaContraseña = (EditText)findViewById(R.id.eTNuevaContraseña);
        int[] id = { R.id.txtListElement };
        String[] Upasswords = new String[] { "upass" };

        Bundle Identidad = getIntent().getExtras();
        final int UID = Identidad.getInt("UserId");

        DBUserAdapter dbUser = new DBUserAdapter(PassActivity.this);
        dbUser.open();

        Cursor mCursor = dbUser.getAllPasswords(UID);
        if(mCursor != null && mCursor.moveToFirst()){
            SimpleCursorAdapter cAdapter;

            cAdapter = new SimpleCursorAdapter(this, R.layout.list_template, mCursor, Upasswords, id, 0);
            lvPass.setAdapter(cAdapter);

        }

        Button NewPass = findViewById(R.id.ncBtn);
        NewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Upass = eTNuevaContraseña.getText().toString();
                try{
                    dbUser.open();
                    if(!dbUser.PassExists(Upass, UID)){
                        dbUser.AddPass(UID, Upass);
                        Toast.makeText(PassActivity.this,"Contraseña añadida", Toast.LENGTH_LONG).show();
                    } else  {
                        Toast.makeText(PassActivity.this,"Contraseña ya existe ", Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e){
                    Toast.makeText(PassActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}