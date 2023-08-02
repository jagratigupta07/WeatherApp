package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=" + API_KEY;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String option;

        do {
            System.out.println("\nOptions:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            option = reader.readLine();

            switch (option) {
                case "1":
                    getAndPrintWeather(reader);
                    break;
                case "2":
                    getAndPrintWindSpeed(reader);
                    break;
                case "3":
                    getAndPrintPressure(reader);
                    break;
                case "0":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (!option.equals("0"));
    }

    private static String fetchDataFromAPI() throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    private static void getAndPrintWeather(BufferedReader reader) throws IOException {
        String data = fetchDataFromAPI();
        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
        String date = reader.readLine();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray forecasts = jsonObject.getJSONArray("list");

        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject forecast = forecasts.getJSONObject(i);
            String forecastDate = forecast.getString("dt_txt");

            if (forecastDate.equals(date)) {
                JSONObject main = forecast.getJSONObject("main");
                double temperature = main.getDouble("temp");
                System.out.println("Temperature on " + date + ": " + temperature + "°C");
                return;
            }
        }

        System.out.println("Data not found for the specified date.");
    }

    private static void getAndPrintWindSpeed(BufferedReader reader) throws IOException {
        String data = fetchDataFromAPI();
        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
        String date = reader.readLine();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray forecasts = jsonObject.getJSONArray("list");

        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject forecast = forecasts.getJSONObject(i);
            String forecastDate = forecast.getString("dt_txt");

            if (forecastDate.equals(date)) {
                JSONObject wind = forecast.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
                return;
            }
        }

        System.out.println("Data not found for the specified date.");
    }

    private static void getAndPrintPressure(BufferedReader reader) throws IOException {
        String data = fetchDataFromAPI();
        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
        String date = reader.readLine();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray forecasts = jsonObject.getJSONArray("list");

        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject forecast = forecasts.getJSONObject(i);
            String forecastDate = forecast.getString("dt_txt");

            if (forecastDate.equals(date)) {
                JSONObject main = forecast.getJSONObject("main");
                double pressure = main.getDouble("pressure");
                System.out.println("Pressure on " + date + ": " + pressure + " hPa");
                return;
            }
        }

        System.out.println("Data not found for the specified date.");
    }
}
