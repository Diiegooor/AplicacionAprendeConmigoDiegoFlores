package com.example.aprendeconmigo1;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PersonaAdapter extends ArrayAdapter<Persona> {

    public PersonaAdapter(@NonNull Context context, ArrayList<Persona> personas) {
        super(context, 0, personas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_usuario, parent, false);
        }

        Persona persona = getItem(position);

        if (persona != null) {
            TextView tvNombre = convertView.findViewById(R.id.tv_nombre);
            TextView tvApellido = convertView.findViewById(R.id.tv_apellido);
            TextView tvEdad = convertView.findViewById(R.id.tv_edad);
            TextView tvContrasena = convertView.findViewById(R.id.tv_contrasena);

            tvNombre.setText("Nombre: " + persona.getNombre());
            tvApellido.setText("Apellido: " + persona.getApellido());
            tvEdad.setText("Edad: " + persona.getEdad());
            tvContrasena.setText("Contraseña: " + persona.getContrasena());
        }

        return convertView;
    }
}
