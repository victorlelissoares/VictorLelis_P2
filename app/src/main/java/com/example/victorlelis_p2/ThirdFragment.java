package com.example.victorlelis_p2;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.example.victorlelis_p2.databinding.FragmentThirdBinding;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {
    DBHelper helper;
    private FragmentThirdBinding binding;
    ArrayList<Jogador> listJogador;
    ArrayAdapter<Jogador> arrayAdapterTime;
    Jogador j;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.listJogadores);
        preencheListViewJogador();


        binding.btnCadastrarjogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FormJogador);
            }
        });

        binding.listJogadores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, int
                    position, long id){
                j = arrayAdapterTime.getItem(position);
                return false;
            }
        });

    }

    private void preencheListViewJogador() {
        helper = new DBHelper(getActivity());
        listJogador = helper.listJogador();
        helper.close();

        if(listJogador!=null){
            arrayAdapterTime = new ArrayAdapter<Jogador>(getContext(),
                    android.R.layout.simple_list_item_1, listJogador);

            binding.listJogadores.setAdapter(arrayAdapterTime);
        }
    }

    private void alert(String s){
        Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, 1, 1,"Deletar Jogador");
        MenuItem mEdita = menu.add(Menu.NONE, 2, 2,"Editar Jogador");
        MenuItem mSair = menu.add(Menu.NONE, 3, 3,"Cancelar");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper=new DBHelper(getContext());
                retornoBD = helper.excluirJogador(j);
                helper.close();
                if( retornoBD == -1){
                    alert("Erro de exclusão!");
                }
                else{
                    alert("Registro excluído com sucesso!");
                }
                preencheListViewJogador();
                return false; }
        });

        mEdita.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("jogador", j);
                Navigation.findNavController(v).navigate(R.id.FormJogadorFragment, bundle);
                preencheListViewJogador();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}