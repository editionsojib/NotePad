package com.editions.notepad;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    NoteDBHelper noteDBHelper;

    ArrayList<NoteModel> arrayList = new ArrayList<>();
    Context context;

    public NoteAdapter(Context context, ArrayList<NoteModel> arrayList, NoteDBHelper noteDBHelper) {
        this.context = context;
        this.arrayList = arrayList;
        this.noteDBHelper = noteDBHelper;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NoteModel noteModel = arrayList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textTitle.setText(noteModel.getTitle());
        viewHolder.textMessage.setText(noteModel.getMessage());




        // Recycler View Item OnClick Listener for item Update
        viewHolder.cardView.setOnClickListener(v-> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(arrayList.get(position).getId()));
            intent.putExtra("title", arrayList.get(position).getTitle());
            intent.putExtra("message", arrayList.get(position).getMessage());
            context.startActivity(intent);
        });






        //Recycler View Item  OnLongClick Listener for item delete
        viewHolder.cardView.setOnLongClickListener(v -> {

            String clickId = String.valueOf(noteModel.getId());


            new AlertDialog.Builder(context)
                    .setTitle("DeleteItem")
                    .setIcon(R.drawable.baseline_delete_forever_24)
                    .setMessage("You Want To Delete This Item")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String clickId = String.valueOf(noteModel.getId());
                            boolean result = noteDBHelper.deleteData(clickId);
                            if (result) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                arrayList.remove(position);
                                notifyDataSetChanged();
                                notifyItemRemoved(position);
                            } else {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();


            return true;
        });//END Recycler View Item  OnLongClick Listener





    }//END onBindViewHolder

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textMessage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textMessage = itemView.findViewById(R.id.textMessage);
            cardView = itemView.findViewById(R.id.CardView);
        }
    }
}
