package es.schooleando.xkcdcomichandlers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.net.URI;

public class ComicActivity extends AppCompatActivity implements ImageHandler.ImageHandlerListener {
    ComicManager manager;
    ImageView comicView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        comicView = (ImageView)findViewById(R.id.imageView);

        // Inicializamos el Comic
        ComicManager manager = new ComicManager(this);

        // Descargamos el primer comic
        manager.init();
    }

    @Override
    public void onDownload(URI uri) {

    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onError(int id, String error) {

    }

    // Aquí faltará añadir Listeners para:
    // un botón de activar/desactivar Timer (manager.start(), manager.stop())
    // un botón para salir de la App



}
