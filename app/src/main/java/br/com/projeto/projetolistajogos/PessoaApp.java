package br.com.projeto.projetolistajogos;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by f.soares.de.farias on 9/15/2016.
 */
public class PessoaApp  extends Application {

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
