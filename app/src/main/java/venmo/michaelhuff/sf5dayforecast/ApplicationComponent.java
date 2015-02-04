package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by koalahamlet on 2/3/15.
 */
@Singleton
@Component(
        modules = WeatherAppModule.class
)

interface ApplicationComponent {

    void inject(WeatherApplication weatherApplication);
    void inject(MainActivity mainActivity);

}

