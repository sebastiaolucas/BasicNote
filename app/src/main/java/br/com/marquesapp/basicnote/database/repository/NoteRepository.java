package br.com.marquesapp.basicnote.database.repository;

import android.content.Context;

import java.util.List;

import br.com.marquesapp.basicnote.asynctask.DataBaseAsyncTask;
import br.com.marquesapp.basicnote.database.BasicNoteRoom;
import br.com.marquesapp.basicnote.database.dao.NoteDAO;
import br.com.marquesapp.basicnote.model.Note;

public class NoteRepository {

    private NoteDAO dao;

    public NoteRepository(Context context) {
        this.dao = BasicNoteRoom.getInstance(context).getRoomNoteDAO();
    }

    public void listAll(ResultCallBack<List<Note>> callBack) {
        new DataBaseAsyncTask<List<Note>>(dao::listAll, callBack::finalize)
                .execute();
    }

    public void save(Note note, ResultCallBack<Void> callBack) {
        new DataBaseAsyncTask<Void>(() -> {
            dao.save(note);
            return null;
        }, callBack::finalize)
                .execute();
    }

    public void update(Note note, ResultCallBack<Void> callBack) {
        new DataBaseAsyncTask<Void>(() -> {
            dao.update(note);
            return null;
        }, callBack::finalize)
                .execute();
    }

    public void remove(Note note, ResultCallBack<Void> callBack){
        new DataBaseAsyncTask<Void>(() -> {
            dao.remove(note);
            return null;
        }, callBack::finalize)
                .execute();
    }

    public interface ResultCallBack<T> {
        void finalize(T result);
    }
}
