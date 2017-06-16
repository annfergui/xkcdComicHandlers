package es.schooleando.xkcdcomichandlers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.net.URI;

public class ComicActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // Inicializamos el Comic
        ComicManager manager = new ComicManager(this,5);

        // Descargamos el primer comic

    }


    // Aquí faltará añadir Listeners para:
    // un botón de activar/desactivar Timer (manager.start(), manager.stop())
    // un botón para salir de la App



}
