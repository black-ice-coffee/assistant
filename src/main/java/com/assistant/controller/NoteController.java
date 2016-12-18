package com.assistant.controller;

import com.assistant.Helper;
import com.assistant.model.Note;
import com.assistant.model.Respond;
import com.assistant.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "notes", method = RequestMethod.GET)
    public Respond getNotes(){
        return Helper.createSuccess(noteService.getNotes());
    }

    @RequestMapping(value = "notes", method = RequestMethod.POST)
    public Respond addNote(@RequestBody Note note){
        return Helper.createSuccess(noteService.saveNode(note));
    }
}
