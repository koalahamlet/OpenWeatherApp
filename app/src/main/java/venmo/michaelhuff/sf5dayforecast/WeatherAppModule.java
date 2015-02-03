package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by koalahamlet on 2/3/15.
 */
public class WeatherAppModule {

    private final WeatherApplication application;

    public WeatherAppModule(WeatherApplication application) {
        this.application = application;
    }

    @Provides @Singleton RestAdapter.Builder provideRestAdapterBuilder() {
        return new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/data/2.5")
                .setLogLevel(RestAdapter.LogLevel.FULL);

//        service = restAdapter.create(ApiService.class);
//        return new RestAdapter.Builder().setEndpoint(AppConfig.API_BASE_URL)
//                .setLogLevel(AppConfig.REST_ADAPTER_LOG_LEVEL)
//                .setRequestInterceptor(headers).setErrorHandler(provideErrorHandler())
//                .setConverter(converter);
    }


    @Provides @Singleton ApiService provideApiService(RestAdapter.Builder restAdapterBuilder) {
        final RestAdapter restAdapter = restAdapterBuilder.build();
        return restAdapter.create(ApiService.class);
    }

}
