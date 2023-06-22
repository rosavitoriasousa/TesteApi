package com.example.conexaoapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText nmMake;
    private TextView vlPreco;
    private TextView nmMarca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nmMake = findViewById(R.id.makeInput);
        vlPreco = findViewById(R.id.precoText);
        nmMarca = findViewById(R.id.marcaText);
        if (getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void buscaMake(View view) {

        String queryString = nmMake.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager)
                  getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            nmMarca.setText("");
            nmMake.setText("Carregando");
        }
        else {
            if (queryString.length() == 0) {
                nmMarca.setText(" ");
                nmMake.setText("Informe um termo de busca");
            } else {
                nmMarca.setText(" ");
                nmMake.setText("Verifique sua conexão");
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new CarregaMake(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);
            // Obtem o JSONArray dos itens de livros
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            // inicializa o contador
            int i = 0;
            String preco  = null;
            String marca = null;
            // Procura pro resultados nos itens do array
            while (i < itemsArray.length() &&
                    ( marca == null && preco == null)) {
                // Obtem a informação
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                //  Obter autor e titulo para o item,
                // erro se o campo estiver vazio
                try {
                    preco = volumeInfo.getString("preco");
                    marca = volumeInfo.getString("marca");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // move para a proxima linha
                i++;
            }
            //mostra o resultado qdo possivel.
            if (preco != null && marca != null) {
                vlPreco.setText(preco);
                nmMarca.setText(marca);
                //nmMake.setText(R.string.str_empty);
            } else {
                // If none are found, update the UI to show failed results.
                vlPreco.setText(R.string.no_results);
                nmMarca.setText(R.string.str_empty);
            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
           vlPreco.setText(R.string.no_results);
            nmMarca.setText(R.string.str_empty);
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}





