package br.ufjf.dcc196.hunterapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.ufjf.dcc196.hunterapp.Model.*;

public class HunterAppDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=3;
    public static final String DATABASE_NAME="ToDoList";

    public HunterAppDBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HunterAppContract.Candidato.CREATE_TABLE);
        db.execSQL(HunterAppContract.Producao.CREATE_TABLE);
        db.execSQL(HunterAppContract.Categoria.CREATE_TABLE);
        db.execSQL(HunterAppContract.Atividade.CREATE_TABLE);
        addBaseData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(HunterAppContract.Candidato.DROP_TABLE);
        db.execSQL(HunterAppContract.Producao.DROP_TABLE);
        db.execSQL(HunterAppContract.Categoria.DROP_TABLE);
        db.execSQL(HunterAppContract.Atividade.DROP_TABLE);
        onCreate(db);
    }

    //region Dados básicos

    private void addBaseData(SQLiteDatabase db){
        addCategoriaData(db);
        addCandidatoData(db);
    }

    private void addCategoriaData(SQLiteDatabase db){

        Categoria c1 = new Categoria();
        c1.setTitulo("Categoria 1");
        Categoria c2 = new Categoria();
        c2.setTitulo("Categoria 2");
        Categoria c3 = new Categoria();
        c3.setTitulo("Categoria 3");

        inserirCategoria(c1);
        inserirCategoria(c2);
        inserirCategoria(c3);
    }

    private void addCandidatoData(SQLiteDatabase db){

        Candidato c1 = new Candidato("Candidato 1", "30/03/1994","3215-0101","perfil 1","mail1@mail.com");
        Candidato c2 = new Candidato("Candidato 2", "30/03/1996","3215-0202","perfil 2","mail2@mail.com");
        Candidato c3 = new Candidato("Candidato 3", "30/03/1998","3215-0303","perfil 3","mail3@mail.com");

        inserirCandidato(c1);
        inserirCandidato(c2);
        inserirCandidato(c3);
        
    }

    //endregion

    //region Categoria
    public Cursor getCursorTodasAsCategorias(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = HunterAppContract.Categoria.COLUMN_TITULO + " ASC";
        Cursor c = db.query(HunterAppContract.Categoria.TABLE_NAME, camposCategoria, null, null, null, null, sort);
        return c;
    }

    public void deleteCategoriaById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = HunterAppContract.Categoria._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(HunterAppContract.Categoria.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

    public Categoria getCategoriaById(Long id) {
        return getCategoriaById(id+"");
    }

    public Categoria getCategoriaById(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = HunterAppContract.Categoria._ID+ "= ?";
        String[] args = {id};
        Cursor c = db.query(HunterAppContract.Categoria.TABLE_NAME,camposCategoria,selecao,args,null,null,null);
        int idxId = c.getColumnIndex(HunterAppContract.Categoria._ID);
        int idxTitulo = c.getColumnIndex(HunterAppContract.Categoria.COLUMN_TITULO);
        c.moveToFirst();
        if (c.getCount()>0){

            Long idCategoria = c.getLong(idxId);
            String titulo = c.getString(idxTitulo);

            return new Categoria(idCategoria,titulo);
        }
        return null;
    }

    public void atualizarCategoria(Categoria c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueCategoria(c);

        String selecao = HunterAppContract.Categoria._ID+ "= ?";
        String[] args = {c.getId()+""};

        db.update(HunterAppContract.Categoria.TABLE_NAME,values,selecao, args);
    }

    public void inserirCategoria(Categoria c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueCategoria(c);

        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);
    }

    //endregion

    //region Candidato
    public Cursor getCursorTodasAsCandidatos(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = HunterAppContract.Candidato.COLUMN_NOME + " ASC";
        Cursor c = db.query(HunterAppContract.Candidato.TABLE_NAME, camposCandidato, null, null, null, null, sort);
        return c;
    }

    public void deleteCandidatoById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = HunterAppContract.Candidato._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(HunterAppContract.Candidato.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

    public Candidato getCandidatoById(Long id) {
        return getCandidatoById(id+"");
    }

    public Candidato getCandidatoById(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = HunterAppContract.Candidato._ID+ "= ?";
        String[] args = {id};
        Cursor c = db.query(HunterAppContract.Candidato.TABLE_NAME,camposCandidato,selecao,args,null,null,null);
        int idxId = c.getColumnIndex(HunterAppContract.Candidato._ID);
        int idxNome = c.getColumnIndex(HunterAppContract.Candidato.COLUMN_NOME);
        int idxNascimento = c.getColumnIndex(HunterAppContract.Candidato.COLUMN_NASCIMENTO);
        int idxTelefone = c.getColumnIndex(HunterAppContract.Candidato.COLUMN_TELEFONE);
        int idxPerfil = c.getColumnIndex(HunterAppContract.Candidato.COLUMN_PERFIL);
        int idxEmail = c.getColumnIndex(HunterAppContract.Candidato.COLUMN_EMAIL);

        c.moveToFirst();
        if (c.getCount()>0){

            Long idCandidato = c.getLong(idxId);
            String nome = c.getString(idxNome);
            String nascimento = c.getString(idxNascimento);
            String telefone = c.getString(idxTelefone);
            String perfil = c.getString(idxPerfil);
            String email = c.getString(idxEmail);

            return new Candidato(idCandidato,nome,nascimento,telefone,perfil,email);
        }
        return null;
    }

    public void atualizarCandidato(Candidato c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueCandidato(c);

        String selecao = HunterAppContract.Candidato._ID+ "= ?";
        String[] args = {c.getId()+""};

        db.update(HunterAppContract.Candidato.TABLE_NAME,values,selecao, args);
    }

    public void inserirCandidato(Candidato c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueCandidato(c);

        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);
    }

    //endregion

    //region Content values

    private ContentValues populateContentValueCategoria(Categoria c){

        ContentValues values = new ContentValues();
        values.put(HunterAppContract.Categoria.COLUMN_TITULO,c.getTitulo());

        return values;

    }

    private ContentValues populateContentValueCandidato(Candidato c){
        ContentValues values = new ContentValues();
        values.put(HunterAppContract.Candidato.COLUMN_NOME,c.getNome());
        values.put(HunterAppContract.Candidato.COLUMN_NASCIMENTO,c.getNascimento());
        values.put(HunterAppContract.Candidato.COLUMN_TELEFONE,c.getTelefone());
        values.put(HunterAppContract.Candidato.COLUMN_PERFIL,c.getPerfil());
        values.put(HunterAppContract.Candidato.COLUMN_EMAIL,c.getEmail());

        return values;
    }

    //endregion

    //region Campos
    private final String[] camposCategoria = {
            HunterAppContract.Categoria._ID,
            HunterAppContract.Categoria.COLUMN_TITULO
    };

    private final String[] camposCandidato = {
            HunterAppContract.Candidato._ID,
            HunterAppContract.Candidato.COLUMN_NOME,
            HunterAppContract.Candidato.COLUMN_NASCIMENTO,
            HunterAppContract.Candidato.COLUMN_TELEFONE,
            HunterAppContract.Candidato.COLUMN_PERFIL,
            HunterAppContract.Candidato.COLUMN_EMAIL
    };

    //endregion

}
