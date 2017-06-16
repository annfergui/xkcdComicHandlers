package es.schooleando.xkcdcomichandlers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadHandler extends Handler {

    private WeakReference<ComicManager.ImageHandler> imageHandler;
    private WeakReference<Context> contexto;

    public DownloadHandler(Looper looper, Context context, ComicManager.ImageHandler imageHandler) {
        super(looper);
        contexto = new WeakReference<>(context);
        this.imageHandler = new WeakReference<>(imageHandler);
    }


    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case (Constantes.DOWNLOAD):

                //comprobamos si tenemos un mensaje de error
                 Message ms=imageHandler.get().obtainMessage();
                 ms.what=Constantes.ERROR;

                String strURL = (String) msg.obj;

                ConnectivityManager cm = (ConnectivityManager) contexto.get().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null && ni.isConnected()) {
                    HttpURLConnection con = null;
                    URL url;


                    try {
                        StringBuilder result = new StringBuilder();
                        url = new URL(strURL);

                        con = (HttpURLConnection) url.openConnection();
                        String redirect = con.getHeaderField("Location");
                        if (redirect != null) {
                            con = (HttpURLConnection) new URL(redirect).openConnection();

                        }
                        con.connect();

                        if (con.getResponseCode() == 200 || con.getResponseCode() == 201) {
                            InputStream in = new BufferedInputStream(con.getInputStream());
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));
                            String line;
                            while ((line = br.readLine()) != null) {
                                result.append(line);
                            }
                            con.disconnect();

                            //creamos el objeto JSON
                            JSONObject json = new JSONObject(result.toString());

                            String imageURL = json.getString("img");

                            url = new URL(imageURL);
                            con = (HttpURLConnection) url.openConnection();
                            con.setConnectTimeout(5000);
                            con.setReadTimeout(5000);
                            con.setRequestMethod("HEAD");
                            con.connect();

                            int tamanyo = con.getContentLength();
                            in = url.openStream();

                            ByteArrayOutputStream out = new ByteArrayOutputStream();

                            byte[] by = new byte[1024];

                            for (int i; (i = in.read(by)) != -1; ) {
                                out.write(by, 0, i);
                                Message m = imageHandler.get().obtainMessage();
                                //mandamos un mensaje de progreso si el message nos devuelve el valor
                                // de Progress
                                m.what = Constantes.PROGRESS;
                                if (tamanyo > 0) {
                                    m.obj = out.size() * 100 / tamanyo;
                                } else {
                                    m.obj = i * -1;
                                }
                                //enviamos el mensaje de progreso
                                imageHandler.get().sendMessage(m);
                            }
                            File outputDir = contexto.get().getExternalCacheDir();
                            String[] data = imageURL.split("/");
                            String[] f = data[data.length - 1].split("\\.");
                            File outputFile = File.createTempFile(f[0], "." + f[1], outputDir);
                            outputFile.deleteOnExit();

                            FileOutputStream fos = new FileOutputStream(outputFile);
                            fos.write(out.toByteArray());
                    //le pasamos al UI Thread la URI de la imagen mediante ms
                            ms.obj = outputFile.getPath() + "|" + json.getInt("num");
                            ms.what = Constantes.LOAD_IMAGE;

                            out.close();
                            in.close();
                        }
                    } catch (MalformedURLException e) {
                        ms.obj = "url incorrecta: " + e.getMessage();
                    } catch (IOException e) {
                        ms.obj = "error de lectura: " + e.getMessage();

                    } catch (JSONException e) {
                        ms.obj = "Json erroneo: " + e.getMessage();

                    } catch (Exception e) {
                        ms.obj = "Excepcion: " + e.getMessage();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                    }

                } else {
                    ms.obj = "No existe conexión";
                }
               //le pasamos al UI Thread la URI de la imagen mediante ms
                imageHandler.get().sendMessage(ms);
                break;
        }

    }
}


        // No es necesario procesar Runnables luego no llamamos a super.handleMessage(msg)



  //  private void downloadImage() {
        // nos descargará una imagen y una vez descargada enviaremos un mensaje LOAD_IMAGE al UI Thread indicando
        // la URI del archivo descargado.

        // Reutilizad código de prácticas anteriores aquí

        // También enviaremos mensajes PROGRESS al UI Thread mediante responseHandler.sendMessage() indicando el porcentaje de progreso, si hay.
        // Enviaremos mensajes ERROR, en caso de que haya un error en la conexión, descarga, etc...
        //responseHandler.sendMessage(...);

   // }



