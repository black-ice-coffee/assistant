package com.assistant.controller;

import com.assistant.model.RSSGroup;
import com.assistant.service.RssService;
import com.assistant.service.RssSummaryService;
import com.rometools.rome.io.FeedException;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("feed/")
public class RssController {

    RssSummaryService summaryService = new RssSummaryService();
    RssService rssService = new RssService();

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public List<RssSummaryService.Summary> rssSummary(@RequestParam(required = true, value = "url") String url) throws BoilerpipeProcessingException, IOException, FeedException {
        return summaryService.getSummaries(url);
    }

    @RequestMapping(value = "rss", method = RequestMethod.GET)
    public List<RSSGroup> rss(){
        return rssService.getRssGroups();
    }
}
