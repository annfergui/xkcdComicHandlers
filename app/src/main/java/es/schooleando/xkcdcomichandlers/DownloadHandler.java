package es.schooleando.xkcdcomichandlers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class DownloadHandler extends Handler {
    private Handler responseHandler;

    public DownloadHandler(Looper looper) {
        super(looper);
    }

    public void setResponseHandler(Handler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case (Constantes.DOWNLOAD):
                downloadImage();
                break;
        }


        // No es necesario procesar Runnables luego no llamamos a super.handleMessage(msg)
    }


    private void downloadImage() {
        // nos descargará una imagen y una vez descargada enviaremos un mensaje LOAD_IMAGE al UI Thread indicando
        // la URI del archivo descargado.

        // Reutilizad código de prácticas anteriores aquí

        // También enviaremos mensajes PROGRESS al UI Thread mediante responseHandler.sendMessage() indicando el porcentaje de progreso, si hay.
        // Enviaremos mensajes ERROR, en caso de que haya un error en la conexión, descarga, etc...


    }


}
