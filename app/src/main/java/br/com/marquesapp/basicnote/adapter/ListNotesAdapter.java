package br.com.marquesapp.basicnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.marquesapp.basicnote.R;
import br.com.marquesapp.basicnote.model.Note;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> notes;
    private OnClickListener onClickListener;
    private OnClickListener onLongClickListener;

    public ListNotesAdapter(Context context, OnClickListener onClickListener){
        this.context = context;
        this.notes = new ArrayList<>();
        this.onClickListener = onClickListener;
        this.onLongClickListener = (position, note) -> {};
    }

    public void setOnLongClickListener(OnClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void atualiza(List<Note> notes){
        notifyItemRangeRemoved(0,getItemCount());
        this.notes.clear();
        this.notes.addAll(notes);
        notifyItemRangeInserted(0,getItemCount());
    }

    public void remove(int position, Note note){
        this.notes.remove(note);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.fillView(note);
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private Note note;
        private TextView textTitle;
        private TextView textNote;

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            initComponents();
            configureOnClick();
            configureOnLongClick();
        }

        public void fillView(Note note) {
            this.note = note;
            textTitle.setText(note.getTitle());
            textNote.setText(note.getText());
        }

        private void initComponents() {
            textTitle = itemView.findViewById(R.id.text_title);
            textNote = itemView.findViewById(R.id.text_note);
        }

        private void configureOnClick(){
            itemView.setOnClickListener(this::onClick);
        }

        private void configureOnLongClick(){
            itemView.setOnLongClickListener(this::onLongClick);
        }

        private void onClick(View view) {
            onClickListener.onClick(getAdapterPosition(), note);
        }

        private boolean onLongClick(View view) {
            onLongClickListener.onClick(getAdapterPosition(), note);
            return true;
        }
    }

    public interface OnClickListener{
        void onClick(int position, Note note);
    }
}
