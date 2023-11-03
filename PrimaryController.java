package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;

public class PrimaryController {

    @FXML
    private TextField cityInput;

    @FXML
    private Text weatherText;

    private final String cityAPI = "https://open-meteo.com/en/docs#latitude=33.767&longitude=-118.1892";

    private final String weatherAPI = "https://open-meteo.com/en/docs/geocoding-api";

    @FXML
    void getWeatherData(ActionEvent event) throws MalformedURLException {
        JSONObject todaysWeather = GetTodaysWeatherInformation(getWoeid());

        System.out.println(todaysWeather);

        weatherText.setText(
                "Min temperature: " + todaysWeather.get("min_temp") +
                        "\nCurrent temperature: " + todaysWeather.get("the_temp") +
                        "\nMax temperature: " + todaysWeather.get("max_temp")
        );
    }

    public String getWoeid() throws MalformedURLException {
        APIConnector apiConnectorCity = new APIConnector(cityAPI);

        JSONObject jsonData = (JSONObject) (apiConnectorCity.getJSONArray(cityInput.getText())).get(0);

        return jsonData.get("woeid").toString();
    }

    public JSONObject GetTodaysWeatherInformation(String woeid) throws MalformedURLException {
        APIConnector apiConnectorWeather = new APIConnector(weatherAPI);

        JSONObject weatherJSONObject = apiConnectorWeather.getJSONObject(woeid + "/");

        JSONArray weatherArray = (JSONArray) weatherJSONObject.get("consolidated_weather");

        return  (JSONObject) weatherArray.get(0);
    }

}
