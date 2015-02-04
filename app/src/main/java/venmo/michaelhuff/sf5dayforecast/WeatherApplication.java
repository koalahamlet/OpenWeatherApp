package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Inject;

/**
 * Created by koalahamlet on 2/3/15.
 */
public class WeatherApplication extends android.app.Application {

    @Inject ApiService apiService;
//    @Inject ApiClient apiClient;


    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();

         component = Dagger_ApplicationComponent.builder()
                 .weatherAppModule(new WeatherAppModule(this))
                .build();
        component().inject(this); // As of now, LocationManager should be injected into this.

    }

    public ApplicationComponent component() {
        return component;
    }
}
