package es.schooleando.xkcdcomichandlers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ComicActivity extends AppCompatActivity {
    ComicManager manager;
    ImageView comicView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        comicView = (ImageView)findViewById(R.id.imageView);

        // Inicializamos el Comic
        ComicManager manager = new ComicManager(comicView);

        // Descargamos el primer comic
        manager.downloadComic();
    }

    // Aquí faltará añadir Listeners para:
    // un botón de activar/desactivar Timer (manager.start(), manager.stop())
    // un botón para salir de la App



}
