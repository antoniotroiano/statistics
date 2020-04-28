package com.valtech.statistics.service.json;

import com.valtech.statistics.model.DataWorld;
import com.valtech.statistics.model.SummaryToday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetJsonValue {

    private static final String URL_WORLD = "https://covid19.mathdro.id/api";
    private static final String URL_COUNTRIES = "https://covid19.mathdro.id/api/countries";
    private static final String VALUE = "value";
    private static final String GLOBAL = "Global";
    private static final String CONFIRMED = "confirmed";
    private static final String RECOVERED = "recovered";
    private static final String DEATHS = "deaths";
    private static final String LAST_UPDATE = "lastUpdate";

    private JSONObject getJSONObject(String url) throws IOException {
        return new JSONObject(IOUtils.toString(new URL(url), StandardCharsets.UTF_8));
    }

    private int getValueOfJSONObject(JSONObject jsonObject, String key, String valueKey) throws JSONException {
        JSONObject getIntValue = (JSONObject) jsonObject.get(key);
        return getIntValue.getInt(valueKey);
    }

    //TODO: Chart.js xAchse anpassen für normales Date Format
    private String getDateNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");
        LocalDate now = LocalDate.now();
        return now.format(dtf);
    }

    public List<String> getCountryOfJSONObject() throws IOException {
        List<String> allCountries = new ArrayList<>();
        JSONObject countries = new JSONObject(IOUtils.toString(new URL(URL_COUNTRIES), StandardCharsets.UTF_8));
        JSONArray getValueOfArray = countries.getJSONArray("countries");
        for (int i = 1; i < getValueOfArray.length(); i++) {
            JSONObject jsonObject = getValueOfArray.getJSONObject(i);
            allCountries.add(jsonObject.getString("name"));
        }
        return allCountries;
    }

    private String getURLWithDate() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        String dailyCOVID19 = "https://covid19.mathdro.id/api/daily/" + yesterdayDate.format(dtf);
        URL url = new URL(dailyCOVID19);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        int code = huc.getResponseCode();
        if (code == 404) {
            //ToDo: Überprüfung kann weg und es kann eine Fehlermeldung rausgegeben werden alles andere was nicht code 200 ist
            LocalDate dayBeforeYesterdayDate = LocalDate.now().minusDays(2);
            dailyCOVID19 = "https://covid19.mathdro.id/api/daily/" + dayBeforeYesterdayDate.format(dtf);
            log.info("Create url for daily update with date {}", dayBeforeYesterdayDate);
            return dailyCOVID19;
        }
        log.info("Create url for daily update with date {}", yesterdayDate);
        return dailyCOVID19;
    }

    private String country(String country) {
        String[] countryArray = country.split(" ");
        StringBuilder bld = new StringBuilder();
        for (int i = 0; i < countryArray.length; ++i) {
            bld.append(countryArray[i] + "%20");
        }
        return bld.toString().substring(0, bld.length() - 3);
    }

    public SummaryToday getDataForSelectedCountry(String country) throws IOException {
        log.info("Invoke create data for selected country {}", country);
        SummaryToday summaryToday = new SummaryToday();

        final String URL_COUNTRY = "https://covid19.mathdro.id/api/countries/" + country(country);

        summaryToday.setCountry(country);
        summaryToday.setConfirmedToday(getValueOfJSONObject(getJSONObject(URL_COUNTRY), CONFIRMED, VALUE));
        summaryToday.setRecoveredToday(getValueOfJSONObject(getJSONObject(URL_COUNTRY), RECOVERED, VALUE));
        summaryToday.setDeathsToday(getValueOfJSONObject(getJSONObject(URL_COUNTRY), DEATHS, VALUE));
        summaryToday.setLastUpdate(getJSONObject(URL_COUNTRY).getString(LAST_UPDATE));
        summaryToday.setLocalDate(LocalDate.now());
        summaryToday.setLocalTime(LocalTime.now().withNano(0));

        JSONArray getValueOfArrayCountry = new JSONArray(IOUtils.toString(new URL(getURLWithDate()), StandardCharsets.UTF_8));

        if (getValueOfArrayCountry.isEmpty()) {
            log.info("No data for last day of country {}. Return only data of today", country);
            return summaryToday;
        }
        for (int i = 0; i < getValueOfArrayCountry.length(); i++) {
            JSONObject jsonObject = getValueOfArrayCountry.getJSONObject(i);
            JSONArray newJSONArray = new JSONArray();
            if (jsonObject.getString("countryRegion").equals(country)) {
                newJSONArray.put(jsonObject);
            }
            for (int j = 0; j < newJSONArray.length(); j++) {
                JSONObject newJSONObject = newJSONArray.getJSONObject(j);
                summaryToday.setNewConfirmedToday(summaryToday.getConfirmedToday() - newJSONObject.getInt(CONFIRMED));
                summaryToday.setNewRecoveredToday(summaryToday.getRecoveredToday() - newJSONObject.getInt(RECOVERED));
                summaryToday.setNewDeathsToday(summaryToday.getDeathsToday() - newJSONObject.getInt(DEATHS));
            }
        }
        log.info("Set new data for confirmed, recovered and deaths for country {}", country);
        return summaryToday;
    }

    public DataWorld getDataOfWorldToModel() throws IOException {
        log.info("Invoke create data of world.");

        int confirmedWorld = getValueOfJSONObject(getJSONObject(URL_WORLD), CONFIRMED, VALUE);
        int recoveredWorld = getValueOfJSONObject(getJSONObject(URL_WORLD), RECOVERED, VALUE);
        int deathsWorld = getValueOfJSONObject(getJSONObject(URL_WORLD), DEATHS, VALUE);
        String lastUpdateWorld = getJSONObject(URL_WORLD).getString(LAST_UPDATE);

        DataWorld dataWorld = new DataWorld();
        dataWorld.setConfirmed(confirmedWorld);
        dataWorld.setRecovered(recoveredWorld);
        dataWorld.setDeaths(deathsWorld);
        dataWorld.setLastUpdate(lastUpdateWorld);
        dataWorld.setLocalDate(getDateNow());

        return dataWorld;
    }
}