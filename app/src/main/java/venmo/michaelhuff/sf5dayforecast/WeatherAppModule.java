package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by koalahamlet on 2/3/15.
 */
@Module
public class WeatherAppModule {

    private final WeatherApplication application;

    public WeatherAppModule(WeatherApplication application) {
        this.application = application;
    }

    @Provides @Singleton RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/data/2.5")
                .setLogLevel(RestAdapter.LogLevel.FULL).build();
    }

    @Provides @Singleton ApiService provideApiService(RestAdapter restAdapter) {
        return restAdapter.create(ApiService.class);
    }

    @Provides @Singleton ApiClient provideApiClient(ApiService apiService) {
        return new ApiClient(apiService);
    }

}
