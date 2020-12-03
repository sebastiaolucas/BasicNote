package br.com.marquesapp.basicnote.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note implements Serializable {

    private static final String TEXT_TITLE_TO_STRING = "Title: ";
    private static final String TEXT_NOTE_TO_STRING = "Note:";

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String text;

    public Note(long id, String title, String text){
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("--------------\n")
                .append(TEXT_TITLE_TO_STRING)
                .append(getTitle())
                .append("\n\n")
                .append(TEXT_NOTE_TO_STRING)
                .append(getText());
        return builder.toString();
    }
}
