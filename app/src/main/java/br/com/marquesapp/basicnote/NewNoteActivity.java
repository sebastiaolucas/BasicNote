package br.com.marquesapp.basicnote;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import br.com.marquesapp.basicnote.database.repository.NoteRepository;
import br.com.marquesapp.basicnote.model.Note;

import static br.com.marquesapp.basicnote.KeysNote.KEY_NOTE;

public class NewNoteActivity extends AppCompatActivity {

    private NoteRepository repository;
    private Note note;
    private EditText fieldTitle;
    private EditText fieldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponents();
        fillComponents();
        configureTitle();
    }

    private void initComponents() {
        TextInputLayout textTitle = findViewById(R.id.field_title);
        this.fieldTitle = textTitle.getEditText();
        TextInputLayout textText = findViewById(R.id.field_text);
        this.fieldText = textText.getEditText();
        repository = new NoteRepository(this);
        this.note = (Note) getIntent().getSerializableExtra(KEY_NOTE);
    }

    private void fillComponents() {
        this.fieldTitle.setText(this.note.getTitle());
        this.fieldText.setText(this.note.getText());
    }

    private void configureTitle() {
        if(note.getId() != 0){
            setTitle(getString(R.string.title_edit)+" "+note.getTitle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveOrUpdate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveOrUpdate() {
        fillNote();
        if(isNotNoteEmpty()){
            if(note.getId() == 0){
                repository.save(note, result -> finish());
            }else{
                repository.update(note, result -> finish());
            }
        }else{
            showMessage(R.string.message_empty);
        }
    }

    private boolean isNotNoteEmpty() {
        return !note.getText().isEmpty() && !note.getTitle().isEmpty();
    }

    private void fillNote() {
        this.note.setTitle(fieldTitle.getText().toString());
        this.note.setText(fieldText.getText().toString());
    }

    private void showMessage(int idRef) {
        Snackbar.make(findViewById(R.id.field_text), idRef, Snackbar.LENGTH_LONG)
            .show();
    }

}
