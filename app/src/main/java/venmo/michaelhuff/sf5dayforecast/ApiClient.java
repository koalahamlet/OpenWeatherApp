package venmo.michaelhuff.sf5dayforecast;

import javax.inject.Inject;

import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by koalahamlet on 1/31/15.
 */
public class ApiClient {


    @Inject ApiService apiService;

    // commented out r
//    public ApiClient(){
//
////    RestAdapter restAdapter = new RestAdapter.Builder()
////                .setEndpoint("http://api.openweathermap.org/data/2.5")
////                .setLogLevel(RestAdapter.LogLevel.FULL)
////                .build();
//         service = restAdapter.create(ApiService.class);
//    }

    public  Observable<Response> getForecast() {
      return   apiService.getSF5DayForecast()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }
}
