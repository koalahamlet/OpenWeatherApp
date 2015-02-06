package venmo.michaelhuff.sf5dayforecast;

import android.app.Application;
import android.content.Context;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class WeatherApplication extends Application {

    private ObjectGraph objectGraph;

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new WeatherAppModule(this));
    }

}
