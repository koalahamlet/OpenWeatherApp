package venmo.michaelhuff.sf5dayforecast;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class WeatherApplication extends Application {

//    private ApplicationComponent component; // kill this with fire
    private ObjectGraph objectGraph;

    @Override public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);


    }


    private List<Object> getModules() {
        return Arrays.<Object>asList(new WeatherAppModule(this));
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }

}
