package com.assistant.controller;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.MessengerSendClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.*;

import static com.assistant.JsonHelper.Constants.*;
import static com.assistant.JsonHelper.getPropertyAsJsonArray;
import static com.assistant.JsonHelper.getPropertyAsJsonObject;
import static com.assistant.JsonHelper.getPropertyAsString;


@RestController
@RequestMapping("messenger/")
public class MessengerController {
    private static final String OBJECT_TYPE_PAGE = "page";
    private static final String MODE_SUBSCRIBE = "subscribe";

    private final JsonParser jsonParser = new JsonParser();



    @RequestMapping("hi")
    public String hi(){
        return "hello world";
    }

    @RequestMapping("webhook")
    public String webhook(@RequestParam(value="hub.verify_token", defaultValue="") String token,
                          @RequestParam(value="hub.challenge", defaultValue="") String challenge){

        if("my_voice_is_my_password_verify_me".equalsIgnoreCase(token)){
            return challenge;
        }
        return "error";
    }

    @RequestMapping(path = "webhook", method = RequestMethod.POST)
    public void webhookPost(@RequestBody String body){

        final JsonObject payloadJsonObject = this.jsonParser.parse(body).getAsJsonObject();

        final String objectType = getPropertyAsString(payloadJsonObject, PROP_OBJECT);
        if (objectType == null || !objectType.equalsIgnoreCase(OBJECT_TYPE_PAGE)) {
            throw new IllegalArgumentException("'object' property has to be 'page'. " +
                    "Make sure this is a page subscription");
        }

        final JsonArray entries = getPropertyAsJsonArray(payloadJsonObject, PROP_ENTRY);
        for (JsonElement entry : entries) {
            processEntry(entry.getAsJsonObject());
        }
    }

    private void processEntry(JsonObject entry) {
        final JsonArray messagingEvents = getPropertyAsJsonArray(entry, PROP_MESSAGING);
        for (JsonElement messagingEvent : messagingEvents) {
            processMessagingEvent(messagingEvent.getAsJsonObject());
        }
    }

    private void processMessagingEvent(JsonObject messagingEvent) {
        JsonObject messageObject = getPropertyAsJsonObject(messagingEvent, PROP_MESSAGE);
        JsonObject senderObject = getPropertyAsJsonObject(messagingEvent, PROP_SENDER);
        JsonObject recipientObject = getPropertyAsJsonObject(messagingEvent, PROP_RECIPIENT);

        String senderId = getPropertyAsString(senderObject, PROP_ID);
        String recipientId = getPropertyAsString(recipientObject, PROP_ID);
        String messageText = getPropertyAsString(messageObject, PROP_TEXT);

        if("weather".equalsIgnoreCase(messageText)){
            messageText = "It's rain in Ho Chi Minh city";
        }

        try {
            sendClient.sendTextMessage(recipientId, messageText);
        } catch (MessengerApiException e) {
            e.printStackTrace();
        } catch (MessengerIOException e) {
            e.printStackTrace();
        }
    }

    private static final String token = "EAAR4EdIQ4GMBAPD4WlRTgnsgb9nWxQbmIVjkBaXNCDAfTYZCzBs9HSnZCRDynFkqgaZAVZBgLWAvvfswIdgI6U9jstvo0xjo1V2NyK9NNolAdkUeaKihOcSNvWCFPdpqPPmeFtlru01Rwar8Yh4THE7bJYOIhyZAcz4qZBg7gzdgZDZD";
    private static final MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder(token).build();
}
