package br.ufjf.dcc196.hunterapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufjf.dcc196.hunterapp.DB.HunterAppContract;
import br.ufjf.dcc196.hunterapp.R;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder>  {
    private Cursor cursor;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public AtividadeAdapter(Cursor c, Context context){
        cursor = c;
        this.context = context;
    }

    public void setContext(Context c){
        context = c;
    }

    public void setCursor(Cursor c){
        cursor = c;
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

        View linha = inflater.inflate(R.layout.atividade_layout,parent,false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int idxId = cursor.getColumnIndexOrThrow(HunterAppContract.Atividade._ID);
        int idxDescricao= cursor.getColumnIndexOrThrow(HunterAppContract.Atividade.COLUMN_DESCRICAO);
        cursor.moveToPosition(i);
        viewHolder.txtId.setText(cursor.getLong(idxId)+"");

        viewHolder.txtDescricao.setText(cursor.getString(idxDescricao));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDescricao;
        public TextView txtId;

        public ViewHolder(final View itemView){
            super(itemView);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoAtividade);
            txtId = itemView.findViewById(R.id.txtIdAtividade);

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
