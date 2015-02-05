package venmo.michaelhuff.sf5dayforecast;

import android.app.Application;

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
                WeatherApplication.class
        },
        library = true
)
public class WeatherAppModule {

    private final Application application;

    public WeatherAppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton public Application provideApplication() {
        return application;
    }

    @Provides @Singleton Picasso providePicasso() {
        return new Picasso.Builder(application.getBaseContext()).build();

//        providePicasso(OkHttpClient okHttpClient) {
//            Picasso picasso =
//                    new Picasso.Builder(Archer.getInstance()).downloader(new OkHttpDownloader(okHttpClient))
//                            .build();
//            return picasso;
//        }
    }


}
