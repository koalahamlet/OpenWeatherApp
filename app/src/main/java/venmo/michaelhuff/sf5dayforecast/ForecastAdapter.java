package venmo.michaelhuff.sf5dayforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import venmo.michaelhuff.sf5dayforecast.Models.Temprature;
import venmo.michaelhuff.sf5dayforecast.Models.WeatherConditions;
import venmo.michaelhuff.sf5dayforecast.Models.WeatherOverview;

public class ForecastAdapter extends ArrayAdapter<WeatherOverview> {

    @Inject
    Picasso picasso;

    public static class ViewHolder {
        @InjectView(R.id.tv_detail)
        TextView tvDetail;
        @InjectView(R.id.tv_main)
        TextView tvMain;
        @InjectView(R.id.tv_high)
        TextView tvHigh;
        @InjectView(R.id.tv_low)
        TextView tvLow;
        @InjectView(R.id.tv_humid)
        TextView tvHumid;
        @InjectView(R.id.tv_date)
        TextView tvDate;
        @InjectView(R.id.image)
        ImageView ivWeatherIcon;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }

    public ForecastAdapter(Context context, ArrayList<WeatherOverview> forecast) {
        super(context, R.layout.forecast_item, forecast);
        WeatherApplication app = WeatherApplication.get(context);
        app.inject(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get appropriate data
        WeatherOverview weatherOverview = getItem(position);
        WeatherConditions conditions = weatherOverview.getWeather();
        Temprature temp = weatherOverview.getTemp();


        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.forecast_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.tvDate.setText(weatherOverview.getDate());
        viewHolder.tvMain.setText(conditions.getMain());
        viewHolder.tvDetail.setText(conditions.getDescription());
        viewHolder.tvHigh.setText(String.valueOf(temp.getMax()) + "˚F");
        viewHolder.tvLow.setText(String.valueOf(temp.getMin()) + "˚F");
        viewHolder.tvHumid.setText(String.valueOf(weatherOverview.getHumidity()) + "%");

        StringBuilder sb = new StringBuilder();

        sb.append("http://openweathermap.org/img/w/").append(conditions.getIcon()).append(".png");
        picasso.with(this.getContext()).load(sb.toString()).fit().centerCrop()
                .into(viewHolder.ivWeatherIcon);

        // Return the completed view to render on screen
        return convertView;
    }


}
