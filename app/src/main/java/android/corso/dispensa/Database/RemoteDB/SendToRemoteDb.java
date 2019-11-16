package android.corso.dispensa.Database.RemoteDB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class SendToRemoteDb {


    public void send(ProdottoEntity prodottoEntity) throws IOException {


        InputStream inputStream = null;

        String brand = "BRAND=" + prodottoEntity.getBrand();
        String type = "TYPE=" + prodottoEntity.getProducttype();
        String barcode = "BARCODE=" + prodottoEntity.getIdbarcode();
        String category = "CATEGORY=" + prodottoEntity.getCategory();

        String urlSTR = "http://databasedispensa.altervista.org/getFromAndroid.php?" + brand + "&" + type + "&" + barcode + "&" + category;
        URL url = new URL(urlSTR);
        Log.d("URL: ", url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(20000);
        connection.setConnectTimeout(30000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        connection.connect();


        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = connection.getInputStream();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            char[] buffer = new char[10240];
            int len = reader.read(buffer);


        }
        if (inputStream != null) {

            inputStream.close();
        }


    }


    @SuppressLint("StaticFieldLeak")
    public void getJSON(final Context context, Long barcode) {
        final String url1 = "http://databasedispensa.altervista.org/getFromAndroid.php?BARCODE=" + barcode;
        new AsyncTask<String, String, String>() {

            protected String doInBackground(String... params) {

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(url1);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                        Log.d("Response: ", "> " + line);

                    }

                    return buffer.toString();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Log.d("JS_>", result);
            }
        }.execute();
    }
}