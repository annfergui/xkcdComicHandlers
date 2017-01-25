package es.schooleando.xkcdcomichandlers;

import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by ruben on 5/01/17.
 */

public class ComicManager {
    private HandlerThread downloadHandlerThread;
    private DownloadHandler downloadHandler; // Funcionará asociado al Worker Thread (HandlerThread)
    private ImageHandler imageHandler;            // Funcionará asociado al UI Thread

    public ComicManager(ImageHandler.ImageHandlerListener listener) {
        downloadHandlerThread = new HandlerThread("myHandlerThread");
        imageHandler = new ImageHandler(Looper.getMainLooper(), listener);
        downloadHandler = new DownloadHandler(downloadHandlerThread.getLooper());

        imageHandler.setResponseHandler(downloadHandler);
        downloadHandler.setResponseHandler(imageHandler);

        downloadHandlerThread.start();
    }

    public void init() {
        // Configuramos el tiempo en imageHandler
        imageHandler.initTimer(10);

        // Forzamos una descarga inicial
        downloadComic();

    }

    public void stop() {
        // TODO: Enviamos un Toast de que se está parando la descarga automática

        // Desactivamos el timer deel imageHandler para que evite enviar mensajes retardados
        imageHandler.disableTimer();
        // TODO: Paramos el HandlerThread, limpiando su cola de mensajes y esperando a que acabe su trabajo activo si lo tiene
    }

    // enviamos un mensaje para descargar un Comic (cuando pulsemos sobre el imageView)
    public void downloadComic() {
        // TODO: crear mensaje
        // downloadHandler.sendMessage();

    }
}
