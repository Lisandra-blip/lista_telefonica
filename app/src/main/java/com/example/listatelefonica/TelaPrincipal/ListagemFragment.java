package com.example.listatelefonica.TelaPrincipal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listatelefonica.R;
import com.example.listatelefonica.Database.DatabaseHelper;

public class ListagemFragment extends Fragment {

    public ListagemFragment() {
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
        View v = inflater.inflate(R.layout.fragment_listagem, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ListView lv = v.findViewById(R.id.listViewContatos);
        databaseHelper.getAllContatos(getActivity(), lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvId = view.findViewById(R.id.textViewIdListContato);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));

                VisualizaFragment editarFragment = new VisualizaFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameMain, editarFragment).commit();
            }
        });

        return v;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_listagem, container, false);
    }
}