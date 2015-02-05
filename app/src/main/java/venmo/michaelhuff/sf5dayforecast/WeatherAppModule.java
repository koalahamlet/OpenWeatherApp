package venmo.michaelhuff.sf5dayforecast;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by koalahamlet on 2/3/15.
 */
@Module(
        includes = {
                NetworkModule.class
        },
        injects = {
                MainActivity.class,
                WeatherApplication.class,
                ForecastAdapter.class
        },
        library = true
)
public class WeatherAppModule {

    private  final Application application;

    public WeatherAppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton public Application provideApplication() {
        return application;
    }

    @Provides @Singleton
    Picasso providePicasso(OkHttpClient okHttpClient) {
        return new Picasso.Builder(application).downloader(new OkHttpDownloader(okHttpClient)).build();
    }

    public Application getInstance(){
        return application;
    }

}
