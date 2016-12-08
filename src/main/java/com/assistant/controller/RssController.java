package com.assistant.controller;

import com.assistant.Helper;
import com.assistant.model.RSSGroup;
import com.assistant.model.RSSItem;
import com.assistant.model.Respond;
import com.assistant.service.RssService;
import com.assistant.service.RssSummaryService;
import com.rometools.rome.io.FeedException;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("feed/")
public class RssController {

    RssSummaryService summaryService = new RssSummaryService();
    RssService rssService = new RssService();

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public Respond rssSummary(@RequestParam(required = true, value = "url") String url) throws BoilerpipeProcessingException, IOException, FeedException {
        return Helper.createSuccess(summaryService.getSummaries(url));
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public Respond rss(){
        return Helper.createSuccess(rssService.getRssGroups());
    }

    @RequestMapping(value = "groups", method = RequestMethod.POST)
    public Respond addGroup(@RequestBody RSSGroup group){
        return Helper.createSuccess(rssService.addGroup(group));
    }

    @RequestMapping(value = "groups/{groupId}", method = RequestMethod.POST)
    public Respond addRSS(@PathVariable String groupId, @RequestBody RSSItem item){
        RSSGroup group = rssService.addUrl(groupId, item);
        if(group != null){
            return Helper.createSuccess(group);
        } else{
            return Helper.createFail("Group " + groupId + " doesn't exist");
        }
    }
}
