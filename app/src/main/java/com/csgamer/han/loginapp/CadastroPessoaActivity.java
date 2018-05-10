package com.csgamer.han.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import dao.DaoAdapter;
import dao.DaoUsuarios;
import domain.Usuario;

public class CadastroPessoaActivity extends AppCompatActivity {

    private long id = 0l;
    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtEmail;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfirma;
    private RadioGroup radioGroup;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button btnCadastrar;
    private Usuario usuario;
    private DaoUsuarios daoUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        setTitle("Cadastro de usuário");

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtSobrenome = (EditText)findViewById(R.id.edtSobrenome);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtUsuario = (EditText)findViewById(R.id.edtUsuario);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtConfirma = (EditText)findViewById(R.id.edtConfirma);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rbMasculino = (RadioButton)findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton)findViewById(R.id.rbFeminino);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        usuario = new Usuario();

        DaoAdapter daoAdapter = new DaoAdapter(this);
        daoAdapter.onCreate(daoAdapter.getWritableDatabase());
        daoUsuarios = new DaoUsuarios(this);

        Bundle b = getIntent().getExtras();
        if(b != null){

            id = b.getLong("id");
            DaoUsuarios daoUsuarios = new DaoUsuarios(this);
            //pegamos a pessoa do banco
            Usuario usuario = daoUsuarios.getUsuario(id);
            //setamos os inputs com a informação da pessoa do banco
            edtNome.setText(usuario.getNome());
            edtSobrenome.setText(usuario.getSobrenome());
            edtEmail.setText(usuario.getEmail());
            edtUsuario.setText(usuario.getUsuario());
            edtSenha.setText(usuario.getSenha());

            RadioButton radioSelecionado = findViewById(radioGroup.getCheckedRadioButtonId());
            radioSelecionado.setText(usuario.getSexo());

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                String sobrenome = edtSobrenome.getText().toString();
                String email = edtEmail.getText().toString();
                String usuario = edtUsuario.getText().toString();
                String senha = edtSenha.getText().toString();
                String confirma = edtConfirma.getText().toString();


                if (nome.equals("")){
                    edtNome.setError("Digite o nome");
                    return;

                }if (sobrenome.equals("")){
                    edtSobrenome.setError("Digite o sobrenome");
                    return;
                }if (email.equals("")){
                    edtEmail.setError("Digite o email");
                    return;
                }if (usuario.equals("")){
                    edtUsuario.setError("Digite o nome de usuário");
                    return;
                }if (senha.equals("")){
                    edtSenha.setError("Confirme a senha");
                    return;
                }if (confirma.equals("")){
                    edtConfirma.setError("Digite a senha");
                    return;
                }

                //FAZER O TRATAMENTO DO TAMANHO DO NOME E SENHA AQUI EM BAIXO

                if(edtNome.getText().toString().length() < 4){
                    edtNome.setError("Nome deve ter ao menos 4 caracteres");
                    return;
                }

                if (edtSenha.getText().toString().length() < 8){
                    edtSenha.setError("A senha deve conter ao menos 8 caracteres");
                    return;
                }

                if (!edtSenha.getText().toString().equals(edtConfirma.getText().toString())){
                    edtConfirma.setError("As senhas não conferem");
                    return;
                }

                String result = "";

                RadioButton radioSelecionado = findViewById(radioGroup.getCheckedRadioButtonId());

                Usuario u = new Usuario();
                u.setId(id);
                u.setNome(edtNome.getText().toString());
                u.setSobrenome(edtSobrenome.getText().toString());
                u.setEmail(edtEmail.getText().toString());
                u.setUsuario(edtUsuario.getText().toString());
                u.setSenha(edtSenha.getText().toString());

                u.setSexo(radioSelecionado.getText().toString());

                DaoUsuarios daoUsuarios = new DaoUsuarios(CadastroPessoaActivity.this);
                if(id == 0l){
                    //inserindo no banco
                    if(daoUsuarios.insert(u) > 0){
                        Toast.makeText(CadastroPessoaActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                        limpar();
                        onResume();
                        login();
                    } else{
                        Toast.makeText(CadastroPessoaActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    //alterando no banco
                    if(daoUsuarios.update(u)){
                        Toast.makeText(CadastroPessoaActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                        limpar();
                        onResume();
                        login();
                    } else{
                        Toast.makeText(CadastroPessoaActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();

            }




        });


    }

    public void limpar (View v){
        limpar();
    }

    private void limpar(){
        usuario = new Usuario();
        edtNome.setText("");
        edtSobrenome.setText("");
        edtEmail.setText("");
        edtUsuario.setText("");
        edtSenha.setText("");

    }

    private void login(){
        Intent intent = new Intent(CadastroPessoaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
