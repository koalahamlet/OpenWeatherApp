package venmo.michaelhuff.sf5dayforecast;

import android.app.Application;

public class WeatherApplication extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
         component = Dagger_ApplicationComponent.builder()
                 .weatherAppModule(new WeatherAppModule(this))
                .build();
        component().inject(this); // As of now, things should be should be injected into this.
    }

    public ApplicationComponent component() {
        return component;
    }
}
