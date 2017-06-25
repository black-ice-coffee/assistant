package com.assistant.controller;

import com.assistant.Helper;
import com.assistant.model.RSSGroup;
import com.assistant.model.RSSItem;
import com.assistant.service.NewsService;
import com.assistant.service.RssConfigurationService;
import com.rometools.rome.io.FeedException;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("feed/")
public class RssController {

    @Autowired
    private RssConfigurationService rssService;

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public ResponseEntity rssSummary(@RequestParam(required = true, value = "url") String url) throws BoilerpipeProcessingException, IOException, FeedException {
        return Helper.createSuccess(newsService.getNewsOfRss(url));
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public ResponseEntity getGroups(){
        return Helper.createSuccess(rssService.getRssGroups());
    }

    @RequestMapping(value = "groups", method = RequestMethod.POST)
    public ResponseEntity addGroup(@RequestBody RSSGroup group){
        return Helper.createSuccess(rssService.addGroup(group));
    }

    @RequestMapping(value = "groups/{groupId}", method = RequestMethod.POST)
    public ResponseEntity addRSS(@PathVariable String groupId, @RequestBody RSSItem item){
        RSSGroup group = rssService.addUrl(groupId, item);
        if(group != null){
            return Helper.createSuccess(group);
        } else{
            return Helper.createFail("Group " + groupId + " doesn't exist");
        }
    }

    @RequestMapping(value = "groups/{groupId}", method = RequestMethod.GET)
    public ResponseEntity getGroup(@PathVariable String groupId){
        RSSGroup group = rssService.findGroup(groupId);
        if(group != null){
            return Helper.createSuccess(group);
        } else{
            return Helper.createFail("Group " + groupId + " doesn't exist");
        }
    }
}
