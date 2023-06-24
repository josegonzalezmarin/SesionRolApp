package es.upm.sesionrol.clasesAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class ApiElementAdapter extends ArrayAdapter {
    private Context context;
    private List<ObjectResult> elementos;

    public ApiElementAdapter(Context context, List<ObjectResult> elementos) {
        super(context, android.R.layout.simple_spinner_item, elementos);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        ObjectResult elemento = elementos.get(position);

        TextView classtextView = itemView.findViewById(android.R.id.text1);
        classtextView.setText(elemento.getName());

        return itemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View dropDownView = convertView;
        if (dropDownView == null) {
            dropDownView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        ObjectResult elemento = elementos.get(position);

        TextView textView = dropDownView.findViewById(android.R.id.text1);
        textView.setText(elemento.getName());

        return dropDownView;
    }

}
