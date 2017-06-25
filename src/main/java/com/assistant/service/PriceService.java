package com.assistant.service;

import com.assistant.model.ExrateList;
import com.assistant.model.GoldPrice;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PriceService {
    public GoldPrice getGoldPrice() throws UnirestException, IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(GoldPrice.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        GoldPrice rate = (GoldPrice) jaxbUnmarshaller.unmarshal(new URL("http://sjc.com.vn/xml/tygiavang.xml"));
        return rate;
    }

    public String getGoldPriceString() {
        try {
            GoldPrice rate = getGoldPrice();
            for (GoldPrice.Ratelist.City city : rate.getRatelist().getCity()) {
                if (city.getName().equalsIgnoreCase("Hồ Chí Minh") & city.getItem().size() > 0) {
                    GoldPrice.Ratelist.City.Item item = city.getItem().get(0);
                    return "SJC: " + item.getBuy() + "/" + item.getSell();
                }
            }
        } catch (Exception e) {
            return "Can't get gold rate";
        }
        return "Can't get gold rate";
    }

    public ExrateList getExRate() throws JAXBException, MalformedURLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ExrateList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ExrateList rate = (ExrateList) jaxbUnmarshaller.unmarshal(new URL("https://www.vietcombank.com.vn/ExchangeRates/ExrateXML.aspx"));
        return rate;
    }

    public String getExRateString(String code) {
        try {
            ExrateList rateList = getExRate();
            for (ExrateList.Exrate rate : rateList.getExrate()) {
                if (rate.getCurrencyCode().equalsIgnoreCase(code)) {
                    return code + ": " + rate.getBuy() + "/" + rate.getSell();
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public String getExRateString(List<String> codes) {
        try {
            ExrateList rateList = getExRate();
            StringBuilder builder = new StringBuilder();
            for (String code : codes) {
                for (ExrateList.Exrate rate : rateList.getExrate()) {
                    if (rate.getCurrencyCode().equalsIgnoreCase(code)) {
                        String price = code + ": " + rate.getBuy() + "/" + rate.getSell();
                        builder.append(price);
                        builder.append('\n');
                    }
                }
            }
            return builder.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String getExRateString() {
        List<String> interestedCodes = Arrays.asList("USD", "EUR", "JPY", "SGD", "AUD", "CAD", "THB");
        return getExRateString(interestedCodes);
    }
}
