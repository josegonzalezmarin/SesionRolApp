package es.upm.sesionrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class PersonajeListAdapter extends RecyclerView.Adapter<PersonajeListAdapter.PersonajeViewHolder> {

    class PersonajeViewHolder extends RecyclerView.ViewHolder {
        private final TextView userItemView;

        private PersonajeViewHolder(View itemView) {
            super(itemView);
            userItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<PersonajeEntity> itemsList;

    /**
     * Constructor
     *
     * @param context context
     */
    public PersonajeListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.character_summary, parent, false);
        return new PersonajeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        if (itemsList != null) {
            PersonajeEntity current = itemsList.get(position);
            holder.userItemView.setText(current.getName());
            //TODO
            //Aqui tienes que meter lo que se ensena en la vista
        } else {
            // Covers the case of data not being ready yet.
            holder.userItemView.setText("No item");
        }
    }

    public void setItems(List<PersonajeEntity> userList){
        itemsList = userList;
        notifyDataSetChanged();
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return (itemsList == null)
                ? 0
                : itemsList.size();
    }

    public PersonajeEntity getGrupoAtPosition (int position) {
        return itemsList.get(position);
    }
}

