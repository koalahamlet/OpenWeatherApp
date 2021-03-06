package venmo.michaelhuff.sf5dayforecast;

import retrofit.client.Response;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by koalahamlet on 1/31/15.
 */
public interface ApiService {
    //http://api.openweathermap.org/data/2.5/forecast?id=5391959 // san francisco

    @GET("/forecast/daily?id=5391959&units=imperial&cnt=5")
    Observable<Response> getSF5DayForecast();

}
