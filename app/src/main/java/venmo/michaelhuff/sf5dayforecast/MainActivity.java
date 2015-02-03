package venmo.michaelhuff.sf5dayforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observer;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        //you have to butter your bread
        ButterKnife.inject(this);


        ApiClient apiClient = new ApiClient();
        Observer<Response> observer = new Observer<Response>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                e.printStackTrace();
                if (e instanceof RetrofitError) {
                    RetrofitError error = (RetrofitError) e;
                    if (error.getResponse().getStatus() == 401) {
                        // something to handle a 401
                    }
                }
            }

            @Override public void onNext(Response response) {
                int status = response.getStatus();
                Type listType = new TypeToken<ForcastResponseObject>() {
                    }.getType();
                ForcastResponseObject TEST = new ForcastResponseObject();
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();

                GsonConverter gsonConverter = new GsonConverter(gson);
                try{
                    TEST = (ForcastResponseObject) gsonConverter.fromBody(response.getBody(), listType);
                    Log.d("onNext", TEST.toString());
                } catch (Exception e) {
                        onError(e);
                        e.printStackTrace();
                        Log.e("onNext", e.toString());
                        Log.e("onNext", e.getMessage());
                }
            }

        };


        apiClient.getForecast().subscribe(observer);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
