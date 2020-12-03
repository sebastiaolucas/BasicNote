package br.com.marquesapp.basicnote;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.com.marquesapp.basicnote.adapter.ListNotesAdapter;
import br.com.marquesapp.basicnote.database.repository.NoteRepository;
import br.com.marquesapp.basicnote.model.Note;

import static br.com.marquesapp.basicnote.KeysNote.KEY_NOTE;

public class MainActivity extends AppCompatActivity {

    private static final String CLIPBOARD_LABEL = "all";

    private NoteRepository repository;
    private ListNotesAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();

        configureFAB();
        configureRecyclerView();
    }

    private void configureFAB() {
        fab.setOnClickListener(view -> {
            Note note = new Note(0,"","");
            startNewNoteActivity(note);
        });
    }

    private void initComponents() {
        repository = new NoteRepository(this);
        fab = findViewById(R.id.fab_add);
        this.adapter = new ListNotesAdapter(this,
                (position, note)-> startNewNoteActivity(note));
    }

    private void configureRecyclerView() {
        RecyclerView listNotes = findViewById(R.id.list_notes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listNotes.setLayoutManager(linearLayoutManager);
        adapter.setOnLongClickListener(this::confirmRemove);
        listNotes.setAdapter(adapter);
    }

    private void startNewNoteActivity(Note note) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        intent.putExtra(KEY_NOTE, note);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        repository.listAll(adapter::atualiza);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int action = item.getItemId();
        if(action == R.id.action_copy){
            repository.listAll(this::CopyAllToClipBoard);
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmRemove(int position, Note note){
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_remove)
                .setMessage(R.string.text_remove)
                .setNegativeButton(R.string.button_no, null)
                .setPositiveButton(R.string.button_yes,
                        (dialogInterface, i) -> repository.remove(note,
                                result -> adapter.remove(position, note)))
                .show();
    }

    private void CopyAllToClipBoard(List<Note> notes){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(CLIPBOARD_LABEL,copyAll(notes)));
        showMessage(R.string.message_copy);
    }

    private void showMessage(int idRef) {
        Snackbar.make(findViewById(R.id.list_notes),idRef,Snackbar.LENGTH_LONG)
                .show();
    }

    private String copyAll(List<Note> notes) {
        StringBuilder builder = new StringBuilder();
        notes.forEach((note) -> {
            builder.append(note);
            builder.append("\n");
        });
        return builder.toString();
    }
}
