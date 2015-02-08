package venmo.michaelhuff.sf5dayforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observer;
import venmo.michaelhuff.sf5dayforecast.Models.ForcastResponseObject;
import venmo.michaelhuff.sf5dayforecast.Models.WeatherOverview;


public class MainActivity extends ActionBarActivity {

    public String TAG = MainActivity.class.getSimpleName();
    BlankFragment mDashBoardFragment;

    FragmentManager fm;
    Fragment fragment;

    @Inject
    ApiClient apiClient;

    @InjectView(R.id.listview)
    ListView listView;

    // for fragment transaction
   // @InjectView(R.id.container)
   // FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // might not need setContentView
        setContentView(R.layout.fragment_main);

        // hide that stuff
        ActionBar bar = getSupportActionBar();
        bar.hide();

        if (savedInstanceState == null) {
            //TODO: do fragment transaction
            // to inflate fragment_main

//            initMainContentFragment();
        }

        //you have to butter your bread
        ButterKnife.inject(this);

        // after the butterknife, comes the dagger
        // Perform injection so that when this call returns all dependencies will be available for use.
        WeatherApplication app = WeatherApplication.get(this);
        app.inject(this);

        Observer<Response> observer = new Observer<Response>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
                if (e instanceof RetrofitError) {
                    RetrofitError error = (RetrofitError) e;
                    if (error.getResponse().getStatus() == 401) {
                        // something to handle a 401
                    }
                }
            }

            @Override
            public void onNext(Response response) {
                int status = response.getStatus();
                ForcastResponseObject responseObject;
                ArrayList<WeatherOverview> forecast = new ArrayList<WeatherOverview>();
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();

                GsonConverter gsonConverter = new GsonConverter(gson);
                try {
                    responseObject = (ForcastResponseObject) gsonConverter.fromBody(response.getBody(), ForcastResponseObject.class);
                    forecast = (ArrayList) responseObject.getList();
                    Log.d("onNext", responseObject.toString());
                } catch (Exception e) {
                    onError(e);
                    Log.e("onNext", e.toString());
                    Log.e("onNext", e.getMessage());
                }

                if (forecast != null) {
                    // if call was not null
                    // get a calendar instance
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy");
                    for (int i = 0; i < forecast.size(); i++) {
                        // then add the current date as a string to the array.
                        forecast.get(i).setDate(sdf.format(cal.getTime()));
                        //increment by one for the next time
                        cal.add(Calendar.DATE, 1);
                    }
                }

                //Set the adapter
                ForecastAdapter adapter = new ForecastAdapter(getApplication().getApplicationContext(), forecast);
                listView.setAdapter(adapter);

            }

        };

        apiClient.getForecast().subscribe(observer);

    }


        /* Private Methods */

    private void initMainContentFragment() {
        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.container);
        if (fragment == null) {
            fm.beginTransaction().add(R.id.container, createMainFragment()).commit();
        }
    }

    private Fragment createMainFragment() {
        Log.d(TAG, "createMainFragment");
        mDashBoardFragment = new BlankFragment();
        return mDashBoardFragment;
    }

}
