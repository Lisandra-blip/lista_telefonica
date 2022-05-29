package com.example.listatelefonica.TelaPrincipal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.listatelefonica.R;
import com.example.listatelefonica.Database.DatabaseHelper;

import java.util.ArrayList;

public class AdicionarFragment extends Fragment {

    private EditText etNome;
    private EditText etCelular;
    Spinner spTipo;
    ArrayList<Integer> listTipoId;
    ArrayList<String> listTipoDs;
    DatabaseHelper databaseHelper;


    public AdicionarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_adicionar, container, false);
        View v = inflater.inflate(R.layout.fragment_adicionar, container, false);

        spTipo = v.findViewById(R.id.spinnertipo);
        etNome = v.findViewById(R.id.editTextNome);
        etCelular = v.findViewById(R.id.editTextCelular);

        databaseHelper = new DatabaseHelper(getActivity());

        listTipoId = new ArrayList<>();
        listTipoDs = new ArrayList<>();
        databaseHelper.getAllTipoContato(listTipoId, listTipoDs);


        ArrayAdapter<String> spMaeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listTipoDs);
        spTipo.setAdapter(spMaeArrayAdapter);

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarContato);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (spTipo.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione um tipo de contato!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o numero para contato", Toast.LENGTH_LONG).show();
        }  else {
            Contato c = new Contato();
            String tipo = spTipo.getSelectedItem().toString();
            c.setId_tipo(listTipoId.get(listTipoDs.indexOf(tipo)));
            c.setNome(etNome.getText().toString());
            c.setCelular(etCelular.getText().toString());
            databaseHelper.createContato(c);
            Toast.makeText(getActivity(), "Novo Contato", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new ListagemFragment()).commit();
        }
    }
}