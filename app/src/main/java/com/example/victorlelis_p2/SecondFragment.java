package com.example.victorlelis_p2;

import android.content.Intent;
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

import com.example.victorlelis_p2.databinding.FragmentSecondBinding;

import java.util.ArrayList;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    DBHelper helper;
    Time t;
    ArrayList<Time> listTime;
    ArrayAdapter<Time> arrayAdapterTime;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    //chamado quando o fragmento está prestes a ser desenhado
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(binding.listTimes);
        preencheListViewTime();
        //atualiza o spinner com os novos times atualizados
        binding.btnCadastrotime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FormTime);
            }
        });

        binding.listTimes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, int
                    position, long id){
                t = arrayAdapterTime.getItem(position);
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, 1, 1,"Deletar Time");
        MenuItem mEdita = menu.add(Menu.NONE, 2, 2,"Editar Time");
        MenuItem mSair = menu.add(Menu.NONE, 3, 3,"Cancelar");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper = new DBHelper(getContext());
                retornoBD = helper.excluirTime(t);
                alert("valor do retorno" + Integer.toString((int)retornoBD));
                helper.close();
                if( retornoBD == -1){
                    alert("Erro de exclusão, time vinculado a jogador(es)!");
                }
                else{
                    alert("Registro excluído com sucesso!");
                }
                preencheListViewTime();
                return false; }
        });

        mEdita.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("time", t);
                Navigation.findNavController(v).navigate(R.id.FormTimeFragment, bundle);
                preencheListViewTime();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private void alert(String s){
        Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void preencheListViewTime(){
        helper = new DBHelper(getActivity());
        listTime = helper.listaTimes();
        helper.close();

       if(listTime!=null){
            arrayAdapterTime = new ArrayAdapter<Time>(getContext(),
                    android.R.layout.simple_list_item_1, listTime);
            binding.listTimes.setAdapter(arrayAdapterTime);
       }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}