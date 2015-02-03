package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by koalahamlet on 2/3/15.
 */
public class WeatherApplication extends android.app.Application {

    @Singleton
    @Component(modules = WeatherAppModule.class)
    public interface ApplicationComponent {
        void inject(WeatherApplication weatherApplication);
        void inject(MainActivity mainActivity);
    }

    @Inject ApiService apiService;
    @Inject ApiClient apiClient;


    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = Dagger_WeatherApplication_ApplicationComponent.builder()
                .androidModule(new WeatherAppModule(this))
                .build();
        component().inject(this); // As of now, LocationManager should be injected into this.
    }

    public ApplicationComponent component() {
        return component;
    }
}
