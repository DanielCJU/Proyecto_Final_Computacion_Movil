package com.example.proyecto_final_computacion_movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_final_computacion_movil.DB.DBUserAdapter;

public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText txtUserName = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                try{
                    if(username.length() > 0 && password.length() >0)
                    {
                        DBUserAdapter dbUser = new DBUserAdapter(LoginActivity.this);
                        dbUser.open();

                        if(dbUser.Login(username, password))
                        {
                            Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent (LoginActivity.this, PassActivity.class);
                            Bundle Identidad = new Bundle();
                            Identidad.putInt("UserId", dbUser.findId(username));
                            intent.putExtras(Identidad);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }
                        dbUser.close();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}