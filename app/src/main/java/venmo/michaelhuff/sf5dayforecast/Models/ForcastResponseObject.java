package venmo.michaelhuff.sf5dayforecast.Models;

import java.util.List;

/**
 * Created by koalahamlet on 2/3/15.
 */
public class ForcastResponseObject {
    public List<WeatherOverview> getList() {
        return list;
    }

    List<WeatherOverview> list;
}
