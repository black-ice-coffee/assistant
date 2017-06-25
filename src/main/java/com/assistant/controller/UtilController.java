package com.assistant.controller;

import com.assistant.Helper;
import com.assistant.model.GoldPrice;
import com.assistant.service.PriceService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequestMapping("misc/")
public class UtilController {
    @Autowired
    private PriceService priceService;

    @RequestMapping(value = "gold", method = RequestMethod.GET)
    public ResponseEntity getGoldRate() throws UnirestException, IOException, JAXBException {
        GoldPrice rate = priceService.getGoldPrice();
        for(GoldPrice.Ratelist.City city: rate.getRatelist().getCity()){
            if(city.getName().equalsIgnoreCase("Hồ Chí Minh")){
                return Helper.createSuccess(city.getItem());
            }
        }
        return Helper.createSuccess(rate);
    }

}
