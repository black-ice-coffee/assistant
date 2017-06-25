package com.assistant.service;

import com.assistant.model.GoldRate;
import com.assistant.model.Root;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class GoldRateService {
    public Root getRate() throws UnirestException, IOException, JAXBException {
//        String xmlValue = Unirest.get("http://sjc.com.vn/xml/tygiavang.xml").asString().getBody();
//        XmlMapper xmlMapper = new XmlMapper();
//        Root rate = xmlMapper.readValue(xmlValue, Root.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Root rate = (Root) jaxbUnmarshaller.unmarshal(new URL("http://sjc.com.vn/xml/tygiavang.xml"));
        return rate;
    }

    public String getRateString() {
        try {
            Root rate = getRate();
            for (Root.Ratelist.City city : rate.getRatelist().getCity()) {
                if (city.getName().equalsIgnoreCase("Hồ Chí Minh") & city.getItem().size() > 0) {
                    Root.Ratelist.City.Item item = city.getItem().get(0);
                    return "Buy: " + item.getBuy() + " Sell:" + item.getSell();
                }
            }
        } catch(Exception e){
            return "Can't get gold rate";
        }
        return "Can't get gold rate";
    }
}
