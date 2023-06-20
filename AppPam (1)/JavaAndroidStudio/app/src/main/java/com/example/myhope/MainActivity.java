package com.example.myhope;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtSenha;
    Button btnLogin;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);

        edtEmail.requestFocus();


        // Obtém a instância padrão do SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Define um listener de clique para o botão "Salvar"
       btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém os valores do email e senha digitados pelo usuário
                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();

                // Verifica se os campos estão preenchidos
                if (!email.isEmpty() && !senha.isEmpty()) {
                    // Salva o email e a senha no SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("senha", senha);
                    editor.apply();

                    //OBS: DRY MUDA ESSA "TelaCategoria" e coloca o nome da que vc deu pra classe que esta a tela com as categorias
                   // Intent intent = new Intent(getApplicationContext(),TelaCategoria.class);
                   // startActivity(intent); depois so tira esses " //" e testa. OK

                    // Exibe uma mensagem de sucesso
                    Toast.makeText(MainActivity.this, "Cadastro salvo com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    // Exibe uma mensagem de erro se os campos não estiverem preenchidos
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






