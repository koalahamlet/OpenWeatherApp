package venmo.michaelhuff.sf5dayforecast;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by koalahamlet on 2/4/15.
 */
@Module(complete = false, library = true)
public class NetworkModule {
    @Provides @Singleton RestAdapter provideRestAdapter() {
        OkClient okClient = new OkClient();
        return new RestAdapter.Builder()
                .setClient(okClient)
                .setEndpoint("http://api.openweathermap.org/data/2.5")
                .setLogLevel(RestAdapter.LogLevel.FULL).build();
    }

    @Provides @Singleton ApiService provideApiService(RestAdapter restAdapter) {
        return restAdapter.create(ApiService.class);
    }

    @Provides @Singleton ApiClient provideApiClient(ApiService apiService) {
        return new ApiClient(apiService);
    }

    @Provides @Singleton
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
