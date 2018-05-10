package com.csgamer.han.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastro;
    private Button btnLogin;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnCadastro = (Button)findViewById(R.id.btnCadastro);
        btnLogin = (Button)findViewById(R.id.btnLogin);



        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroPessoaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if (email.equals("")){
                    Toast.makeText(MainActivity.this, "Inserir o email",Toast.LENGTH_LONG).show();
                }

                else if (senha.equals("")){
                    Toast.makeText(MainActivity.this, "Inserir a senha",Toast.LENGTH_LONG).show();
                } else {


                }
            }
        });
    }


}
