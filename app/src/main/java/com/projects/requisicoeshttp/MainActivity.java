package com.projects.requisicoeshttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.requisicoeshttp.api.CEPService;
import com.projects.requisicoeshttp.api.DataService;
import com.projects.requisicoeshttp.model.CEP;
import com.projects.requisicoeshttp.model.Foto;
import com.projects.requisicoeshttp.model.Postagem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnRecuperar;
    private TextView txtResultado;
    private Retrofit retrofit;
    private List<Foto> fotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecuperar = findViewById(R.id.btn);
        txtResultado = findViewById(R.id.txt);

        retrofit = new Retrofit.Builder()
//                .baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                // Usar a versão 2.4.0 para obter o GsonConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  salvarPostagem();
//                recuperarListaRetrofit();
//                  recuperarCEPRetrofit();

//                MyTask task = new MyTask();
//                String urlApi = "https://blockchain.info/ticker";
//                String urlCep = "https://viacep.com.br/ws/01001000/json";
//                String urlMovies = "https://rafals-dsmovie.herokuapp.com/movies";
//                String urlMoviesLocal = "http://localhost:8080/movies?size=12&page=1";
//                task.execute(urlCep);

            }
        });


    }

    private void salvarPostagem(){

        //Configura objeto postagem
//        Postagem postagem = new Postagem("1234","Título postagem!","Corpo postagem");

        //Recupera o serviço e salva postagem
        DataService service = retrofit.create(DataService.class);

        Call<Postagem> call  = service.salvarPostagem("1234","Título postagem!","Corpo postagem");

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if(response.isSuccessful()){
                    Postagem postagemResposta = response.body();
                    txtResultado.setText("Código: " + response.code() +"\nid: " + postagemResposta.getId() + "\nTitulo: " + postagemResposta.getTitle());
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });

    }

    private void recuperarListaRetrofit(){

        DataService service = retrofit.create(DataService.class);
        Call<List<Foto>> call = service.recuperarFotos();

        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                fotos = response.body();

                for (int i= 0; i< fotos.size(); i++){
                    Foto foto = fotos.get(i);
                    Log.d("resultado","resultado: " + foto.getId());
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });
    }

    private void recuperarCEPRetrofit(){
        CEPService cepService = retrofit.create(CEPService.class);
//        Call<CEP> call = cepService.recuperarCEP();
        Call<CEP> call = cepService.recuperarCEP("01310100");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()){
                    CEP cep = response.body();
                    txtResultado.setText(cep.getLocalidade());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }

    class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader reader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recupera os dados em bytes
                inputStream = conexao.getInputStream();

                //Lé os dados em bytes e decodifica para caractesres
                reader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos ccracteres do InputStreamReader
                BufferedReader bufferedReader = new BufferedReader(reader);
                buffer = new StringBuffer();
                String linha = "";

                while((linha = bufferedReader.readLine()) != null){
                        buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

//            String logradouro = null;
//            String cep = null;
//            String complemento = null;
//            String bairro = null;
//            String localidade = null;
//            String uf = null;
//
//            try {
//                JSONObject jsonObject = new JSONObject(resultado);
//                logradouro = jsonObject.getString("logradouro");
//                cep = jsonObject.getString("cep");
//                complemento = jsonObject.getString("complemento");
//                bairro = jsonObject.getString("bairro");
//                localidade = jsonObject.getString("localidade");
//                uf = jsonObject.getString("uf");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            txtResultado.setText(logradouro + "\n" + cep + "\n" + complemento + "\n" + bairro + "\n" + localidade + "\n" + uf);
        }
    }
}