package br.ufjf.dcc196.hunterapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufjf.dcc196.hunterapp.Model.Candidato;
import br.ufjf.dcc196.hunterapp.R;

public class CandidatoPorCategoriaAdapter extends RecyclerView.Adapter<CandidatoPorCategoriaAdapter.ViewHolder>  {
    private List<Candidato> listCandidatos;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public CandidatoPorCategoriaAdapter(List<Candidato> list, Context context){
        listCandidatos = list;
        this.context = context;
    }

    public void setContext(Context c){
        context = c;
    }

    public void setListCandidatos(List<Candidato> list){
        listCandidatos = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View linha = inflater.inflate(R.layout.candidato_por_categoria_layout,parent,false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Candidato candidato = listCandidatos.get(i);
        viewHolder.txtId.setText(candidato.getId().toString());

        viewHolder.txtNome.setText(candidato.getNome());
        viewHolder.txtHoras.setText(candidato.getSumHorasAtividades().toString());
    }

    @Override
    public int getItemCount() {
        return listCandidatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNome;
        public TextView txtId;
        public TextView txtHoras;

        public ViewHolder(final View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeCandidatoPorCategoria);
            txtId = itemView.findViewById(R.id.txtIdCandidatoPorCategoria);
            txtHoras = itemView.findViewById(R.id.txtHorasCandidatoPorCategoria);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView,position);
                        }
                    }
                }
            });
        }
    }

}
