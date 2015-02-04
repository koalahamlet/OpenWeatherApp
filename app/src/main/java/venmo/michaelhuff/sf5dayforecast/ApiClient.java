package venmo.michaelhuff.sf5dayforecast;

import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by koalahamlet on 1/31/15.
 */
public class ApiClient {

      ApiService apiService;

    public ApiClient(ApiService apiService){
        this.apiService = apiService;
    }

    public  Observable<Response> getForecast() {
      return apiService.getSF5DayForecast()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }
}
