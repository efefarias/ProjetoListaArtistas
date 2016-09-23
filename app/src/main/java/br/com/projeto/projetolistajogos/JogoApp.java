package br.com.projeto.projetolistajogos;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Felipe on 15/05/2016.
 */
public class JogoApp extends Application {

    EventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();

        eventBus = new EventBus();
    }

    public org.greenrobot.eventbus.EventBus getEventBus() {
        return eventBus;
    }
}
