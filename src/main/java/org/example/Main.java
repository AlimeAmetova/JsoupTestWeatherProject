package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
//Код выполняет запрос к веб-сайту "https://www.gismeteo.ru/weather-simferopol-4995/now/"
//и извлекает информацию о погоде в городе Симферополь.


public class Main {
    public static void main(String[] args)  {
        String weatherInfo = getWeatherInfo();
        System.out.println(weatherInfo);
    }

    public static String getWeatherInfo() {
        String url = "https://www.gismeteo.ru/weather-simferopol-4995/now/";

        try {
            Document document = Jsoup.connect(url).get();

            // В этом примере используется селектор CSS внутри метода select() для выбора нужных элементов из HTML-документа.
            var temperature = document.select(".weathertabs .weather").text();
            String temperatureC;
            String temperatureF;
            List<String> listTemp = Arrays.asList(temperature.split(" "));
            temperatureC = listTemp.get(0);
            temperatureF = listTemp.get(1);

            Element conditionElement = document.selectFirst(".now .now-desc");
            String condition = conditionElement != null ? conditionElement.text() : "Ошибка condition";

            //В этом примере используется селектор CSS, который выбирает элемент на веб-странице на основе его пути в дереве HTML.
            Element humidityElement = document.selectFirst("body > section.content.wrap > div.content-column.column1 > section.section.section-content.section-bottom-shadow > div > div.now-info > div > div.now-info-item.humidity > div > div.item-value");
            String humidity = humidityElement != null ? humidityElement.text() : "Ошибка humidity";


            Element windElement = document.selectFirst("body > section.content.wrap > div.content-column.column1 > section.section.section-content.section-bottom-shadow > div > div.now-info > div > div.now-info-item.wind > div.item-value");
            String wind = windElement != null ? windElement.text() : "Ошибка wind";


            Element waterElement = document.selectFirst("body > section.content.wrap > div.content-column.column1 > section.section.section-content.section-bottom-shadow > div > div.now-info > div > div.now-info-item.water > div.item-value > span.unit.unit_temperature_c");
            String water = waterElement != null ? waterElement.text() : "Ошибка water";

            return "Погода в г. Симферополь\n" +
                    "Сейчас на улице:\n" +
                    "Температура по Цельсию: " + temperatureC + " по Фарингейту " + temperatureF + "\n" +
                    "Состояние погоды: " + condition + "\n" +
                    "Влажность: " + humidity + " %" + "\n" +
                    "Ветер: " + wind + "\n" +
                    "Температуры воды в море: " + water + "\n";

        } catch (IOException e) {
            e.printStackTrace();
            return "Произошла ошибка при получении информации о погоде.";
        }
    }
}
