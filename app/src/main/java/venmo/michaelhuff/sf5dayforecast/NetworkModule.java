package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by koalahamlet on 2/4/15.
 */
@Module(complete = false, library = true)
public class NetworkModule {
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
