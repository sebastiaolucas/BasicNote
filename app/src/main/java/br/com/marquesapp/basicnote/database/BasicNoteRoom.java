package br.com.marquesapp.basicnote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.marquesapp.basicnote.database.dao.NoteDAO;
import br.com.marquesapp.basicnote.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class BasicNoteRoom extends RoomDatabase {

    private static final String NAME_DATA_BASE = "basicnote.db";

    public abstract NoteDAO getRoomNoteDAO();

    public static BasicNoteRoom getInstance(Context context){
        return Room.databaseBuilder(
                context
                , BasicNoteRoom.class
                ,NAME_DATA_BASE)
                .build();
    }
}
