package venmo.michaelhuff.sf5dayforecast;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity implements BlankFragment.OnFragmentInteractionListener{

    public String TAG = MainActivity.class.getSimpleName();
    BlankFragment blankFragment;

    FragmentManager fm;
    Fragment fragment;

    @Inject
    ApiClient apiClient;

//    @InjectView(R.id.listview)
//    ListView listView;

    // for fragment transaction
   // @InjectView(R.id.container)
   // FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // might not need setContentView
        setContentView(R.layout.activity_main);

        // hide that stuff
        ActionBar bar = getSupportActionBar();
        bar.hide();

        if (savedInstanceState == null) {
            //TODO: do fragment transaction
            // to inflate fragment_main
            initMainContentFragment();
        }

        //you have to butter your bread
        ButterKnife.inject(this);

        // after the butterknife, comes the dagger
        // Perform injection so that when this call returns all dependencies will be available for use.
        WeatherApplication app = WeatherApplication.get(this);
        app.inject(this);

        // Where I would have put the call were I

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
        blankFragment = new BlankFragment();
        return blankFragment;
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
