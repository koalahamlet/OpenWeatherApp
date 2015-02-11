package venmo.michaelhuff.sf5dayforecast;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends android.support.v4.app.Fragment {

    public String TAG = BlankFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Inject
    ApiClient apiClient;

    @InjectView(R.id.listview)
    ListView listView;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //you have to butter your bread
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        // after the butterknife, comes the dagger
        // Perform injection so that when this call returns all dependencies will be available for use.
        WeatherApplication app = WeatherApplication.get(getActivity());
        app.inject(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                ForecastAdapter adapter = new ForecastAdapter(getActivity(), forecast);
                listView.setAdapter(adapter);

            }
        };

        apiClient.getForecast().subscribe(observer);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
