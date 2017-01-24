package es.schooleando.xkcdcomichandlers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by ruben on 24/01/17.
 */

public class ImageHandler extends Handler {
    Handler responseHandler;
    private boolean timerActive;             // Controlamos si el timer está activo o no
    private int seconds;                     // Segundos del timer

    public ImageHandler(Looper looper) {
        super(looper);
    }

    public void initTimer(int seconds) {
        this.timerActive = true;
        this.seconds = seconds;
    }

    public void disableTimer() {
        this.timerActive=false;
        this.seconds=0;
    }

    public void setResponseHandler(DownloadHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void handleMessage(Message msg) {
        switch(msg.what) {
            case (Constantes.LOAD_IMAGE):
                // TODO: Obtenemos la URI del archivo temporal y cargamos el imageView

                // si está activo el timer posteriormente enviaremos un mensaje retardado de DOWNLOAD_COMIC al HandlerThread, solo si está activo el Timer.
                if (timerActive) {
                    // TODO: terminar de construir el mensaje DOWNLOAD_COMIC
                    // downloadHandler.sendMessageDelayed(..., seconds);
                }
                break;
            case (Constantes.PROGRESS):
                // actualizaremos el progressBar
                break;
            case (Constantes.ERROR):
                // mostraremos un Toast del error. Cancelamos el Timer para evitar errores posteriores
                break;
        }
    }
}


