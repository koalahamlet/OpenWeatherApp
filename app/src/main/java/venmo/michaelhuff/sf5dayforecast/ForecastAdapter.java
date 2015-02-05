package venmo.michaelhuff.sf5dayforecast;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.InjectView;
import venmo.michaelhuff.sf5dayforecast.Models.Temprature;
import venmo.michaelhuff.sf5dayforecast.Models.WeatherConditions;
import venmo.michaelhuff.sf5dayforecast.Models.WeatherOverview;

public class ForecastAdapter extends ArrayAdapter<WeatherOverview> {

    @InjectView(R.id.tv_detail) TextView tvDetail;
    @InjectView(R.id.tv_main) TextView tvMain;
    @InjectView(R.id.tv_high) TextView tvHigh;
    @InjectView(R.id.tv_low) TextView tvLow;
    @InjectView(R.id.tv_humid) TextView tvHumid;
    @InjectView(R.id.tv_date) TextView tvDate;
    @InjectView(R.id.image)
    ImageView ivWeatherIcon;

//    private static class ViewHolder {}

    public ForecastAdapter(Context context, ArrayList<WeatherOverview> forecast) {
        super(context, R.layout.forecast_item, forecast);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WeatherOverview weatherOverview = getItem(position);
        WeatherConditions conditions = weatherOverview.getWeather();
        Temprature temp = weatherOverview.getTemp();
        //TODO: taking out for now because I don't know how to do a viewholder pattern with butterknife

        tvDate.setText(weatherOverview.getDate());
        tvMain.setText(conditions.getMain());
        tvDetail.setText(conditions.getDescription());
        tvHigh.setText(String.valueOf(temp.getMax()));
        tvLow.setText(String.valueOf(temp.getMin()));
        tvHumid.setText(String.valueOf(weatherOverview.getHumidity()));
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder; // view lookup cache stored in tag
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.forecast_item, parent, false);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        // Populate the data into the template view using the data object
//        viewHolder.tvDate.setText(user.name);
//        viewHolder.home.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }

}
