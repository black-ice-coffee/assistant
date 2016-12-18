package com.assistant.service;

import com.assistant.Helper;
import com.assistant.entity.NoteEntity;
import com.assistant.model.Note;
import com.assistant.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotes() {
        List<NoteEntity> entities = noteRepository.findAllByOrderByModifiedDateDesc();
        List<Note> notes = new ArrayList<>();
        for (NoteEntity entity : entities) {
            notes.add(toDto(entity));
        }
        return notes;
    }

    public Note saveNode(Note note) {
        NoteEntity entity = fromDto(note);
        if(entity.getId() == null){
            entity.setId(Helper.generateId());
            entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }
        entity.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        entity = noteRepository.saveAndFlush(entity);
        return toDto(entity);
    }

    Note toDto(NoteEntity entity) {
        Note note = new Note();
        note.id = entity.getId();
        note.content = entity.getContent();
        note.title = entity.getTitle();
        note.createdDate = entity.getCreatedDate();
        note.modifiedDate = entity.getModifiedDate();
        return note;
    }

    NoteEntity fromDto(Note note) {
        NoteEntity entity = new NoteEntity();
        entity.setId(note.id);
        entity.setContent(note.content);
        entity.setTitle(note.title);
        return entity;
    }
}
