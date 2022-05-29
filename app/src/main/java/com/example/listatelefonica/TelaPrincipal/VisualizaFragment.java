package com.example.listatelefonica.TelaPrincipal;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.listatelefonica.Database.DatabaseHelper;
import com.example.listatelefonica.R;

import java.util.ArrayList;


public class VisualizaFragment extends Fragment {

    EditText etNome;
    EditText etCelular;
    Spinner spTipo;
    ArrayList<Integer> listTipoId;
    ArrayList<String> listTipoDs;
    DatabaseHelper databaseHelper;
    Contato c;

    public VisualizaFragment() {
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
        //return inflater.inflate(R.layout.fragment_visualiza, container, false);
        View v = inflater.inflate(R.layout.fragment_visualiza, container, false);
        Bundle bundle = getArguments();
        int id_contato = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdContato(id_contato);

        spTipo = v.findViewById(R.id.spinnertipo);
        etNome = v.findViewById(R.id.editTextNome);
        etCelular = v.findViewById(R.id.editTextCelular);


        listTipoId = new ArrayList<>();
        listTipoDs = new ArrayList<>();
        databaseHelper.getAllTipoContato(listTipoId, listTipoDs);

        ArrayAdapter<String> spMaeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listTipoDs);
        spTipo.setAdapter(spMaeArrayAdapter);


        etNome.setText(String.valueOf(c.getNome()));
        etCelular.setText(String.valueOf(c.getCelular()));

        Button btnEditar = v.findViewById(R.id.buttonEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_contato);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_contato);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }
    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número do celular!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Contato c = new Contato();
            c.setId(id);
            String tipo = spTipo.getSelectedItem().toString();
            c.setId_tipo(listTipoId.get(listTipoDs.indexOf(tipo)));
            c.setNome(etNome.getText().toString());
            c.setCelular(etCelular.getText().toString());
            databaseHelper.updateContato(c);
            Toast.makeText(getActivity(), "Editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new ListagemFragment()).commit();
        }
    }

    private void excluir(int id) {
        c = new Contato();
        c.setId(id);
        databaseHelper.deleteContato(c);
        Toast.makeText(getActivity(), "Mãe excluída!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new ListagemFragment()).commit();
    }

}