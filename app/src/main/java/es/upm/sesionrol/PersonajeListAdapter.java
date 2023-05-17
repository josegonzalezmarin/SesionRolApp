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

    private List<PersonajeEntity> itemsList;


    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView race;
        private final TextView dndclass;
        private final TextView lvl;
        private final TextView exp;

        private PersonajeViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameView);
            race = itemView.findViewById(R.id.raceView);
            dndclass = itemView.findViewById(R.id.classView);
            lvl = itemView.findViewById(R.id.lvlView);
            exp = itemView.findViewById(R.id.expView);

        }
    }







    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_summary, parent, false);
        return new PersonajeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int pos) {
            PersonajeEntity current = itemsList.get(pos);
            holder.name.setText(current.getName());
            holder.race.setText(current.getRace());
            holder.dndclass.setText(current.getDndclass());
            holder.lvl.setText(current.getLvl());
            holder.exp.setText(current.getExp());
    }

    public void setItems(List<PersonajeEntity> userList){
        this.itemsList = userList;
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

