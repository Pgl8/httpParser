package com.pgl8.httpparser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Principal extends Activity {
    //String con la url deseada
    //String url= "http://www.losvinosdejerez.com/productos.php?id=12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

           new obtenerHTML().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Necesita ser una tarea asíncrona
    //Haremos una clase interna para ello
           class obtenerHTML extends AsyncTask<String, Void, String> {
            private int cont=0;

            @Override
            protected String doInBackground(String... params) {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet("http://www.losvinosdejerez.com/productos.php?id=12");

                try {
                    HttpResponse response = client.execute(request);
                    String html;
                    InputStream in = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder str = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if(cont==688 || cont==690 || cont==691 || cont==692 || cont==693 ||
                           cont==694 || cont==701 || cont==703) {
                            str.append(line);//.replace("<br />", "\n\n"));
                        }
                        cont++;
                    }

                    in.close();
                    html = str.toString();

                    return html;

                }catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    //Quitamos etiquetas HTML en el resultado
                    String resultado = android.text.Html.fromHtml(result).toString();

                    //Cambiamos el TextView para el texto de salidaaaa
                    TextView txt = (TextView) findViewById(R.id.textView3);
                    txt.setText(resultado);
                }
            }
        }


}

