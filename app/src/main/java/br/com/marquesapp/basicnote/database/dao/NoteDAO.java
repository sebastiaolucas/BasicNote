package br.com.marquesapp.basicnote.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marquesapp.basicnote.model.Note;

@Dao
public interface NoteDAO {

    @Insert
    public void save(Note note);

    @Delete
    public void remove(Note note);

    @Update
    public void update(Note note);

    @Query("Select * From note")
    public List<Note> listAll();
}
