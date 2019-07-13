package br.ufjf.dcc196.hunterapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc196.hunterapp.Model.*;

public class HunterAppDBHelper extends SQLiteOpenHelper {
    //region Padrao

    public static final int DATABASE_VERSION=5;
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

    //endregion

    //region Dados básicos

    private void addBaseData(SQLiteDatabase db){
        addCategoriaData(db);
        addCandidatoData(db);
        addProducaoData(db);
    }

    private void addCategoriaData(SQLiteDatabase db){

        Categoria c1 = new Categoria();
        c1.setTitulo("Categoria 1");
        Categoria c2 = new Categoria();
        c2.setTitulo("Categoria 2");
        Categoria c3 = new Categoria();
        c3.setTitulo("Categoria 3");

        ContentValues values = populateContentValueCategoria(c1);
        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);

        values = populateContentValueCategoria(c2);
        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);

        values = populateContentValueCategoria(c3);
        db.insert(HunterAppContract.Categoria.TABLE_NAME,null,values);
    }

    private void addCandidatoData(SQLiteDatabase db){

        Candidato c1 = new Candidato("Candidato 1", "30/03/1994","3215-0101","perfil 1","mail1@mail.com");
        ContentValues values = populateContentValueCandidato(c1);
        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);

        Candidato c2 = new Candidato("Candidato 2", "30/03/1996","3215-0202","perfil 2","mail2@mail.com");
        values = populateContentValueCandidato(c2);
        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);

        Candidato c3 = new Candidato("Candidato 3", "30/03/1998","3215-0303","perfil 3","mail3@mail.com");
        values = populateContentValueCandidato(c3);
        db.insert(HunterAppContract.Candidato.TABLE_NAME,null,values);

    }

    private void addProducaoData(SQLiteDatabase db){

        Long categoriaId = getPrimeiraCategoriaId(db);
        Long candidatoId = getPrimeiroCandidatoId(db);
        Producao p1 = new Producao("Producao 1", "Descrição 1", "20/04/2019", "10/05/2019",categoriaId,candidatoId);
        Producao p2 = new Producao("Producao 2", "Descrição 2", "20/05/2019", "10/06/2019",categoriaId,candidatoId);
        Producao p3 = new Producao("Producao 3", "Descrição 3", "20/06/2019", "10/07/2019",categoriaId,candidatoId);

        ContentValues values = populateContentValueProducao(p1);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p2);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p3);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);
    }

    private Long getPrimeiraCategoriaId(SQLiteDatabase db){
        Cursor c = getCursorTodasAsCategorias(db);

        int idxId = c.getColumnIndex(HunterAppContract.Categoria._ID);
        c.moveToFirst();
        if (c.getCount()>0) {
            Long idCategoria = c.getLong(idxId);
            return idCategoria;
        }
        return null;
    }
    private Long getPrimeiroCandidatoId(SQLiteDatabase db){
        Cursor c = getCursorTodosOsCandidatos(db);

        int idxId = c.getColumnIndex(HunterAppContract.Candidato._ID);
        c.moveToFirst();
        if (c.getCount()>0) {
            Long idCandidato = c.getLong(idxId);
            return idCandidato;
        }
        return null;
    }

    //endregion

    //region Categoria
    public Cursor getCursorTodasAsCategorias(){

        SQLiteDatabase db = this.getWritableDatabase();
        return getCursorTodasAsCategorias(db);
    }

    public Cursor getCursorTodasAsCategorias(SQLiteDatabase db){
        String sort = HunterAppContract.Categoria.COLUMN_TITULO + " ASC";
        Cursor c = db.query(HunterAppContract.Categoria.TABLE_NAME, camposCategoria, null, null, null, null, sort);
        return c;
    }

    public List<Categoria> getTodasAsCategoriasList(){
        Cursor c = getCursorTodasAsCategorias();
        List<Categoria> result = new ArrayList<>();

        c.move(-1);

        int idxTag = c.getColumnIndex(HunterAppContract.Categoria._ID);
        int idxTitulo = c.getColumnIndex(HunterAppContract.Categoria.COLUMN_TITULO);
        while(c.moveToNext()){
            Long id = c.getLong(idxTag);
            String titulo = c.getString(idxTitulo);
            result.add(new Categoria(id,titulo));
        }
        return result;
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
    public Cursor getCursorTodosOsCandidatos(){
        SQLiteDatabase db = this.getWritableDatabase();
        return getCursorTodosOsCandidatos(db);

    }
    public Cursor getCursorTodosOsCandidatos(SQLiteDatabase db){

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


    //region Producao
    public Cursor getCursorTodasAsProducoesByCandidatoId(Long candidatoId){
        return getCursorTodasAsProducoesByCandidatoId(""+candidatoId);
    }

    public Cursor getCursorTodasAsProducoesByCandidatoId(String candidatoId){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = HunterAppContract.Producao.COLUMN_TITULO + " ASC";
        String select = HunterAppContract.Producao.COLUMN_CANDIDATO+" = ?";

        String[] selectArgs = {candidatoId};

        Cursor c = db.query(HunterAppContract.Producao.TABLE_NAME, camposProducao, select, selectArgs, null, null, sort);
        return c;
    }

    public void deleteProducaoById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = HunterAppContract.Producao._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(HunterAppContract.Producao.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

    public Producao getProducaoById(Long id) {
        return getProducaoById(id+"");
    }

    public Producao getProducaoById(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = HunterAppContract.Producao._ID+ "= ?";
        String[] args = {id};
        Cursor c = db.query(HunterAppContract.Producao.TABLE_NAME,camposProducao,selecao,args,null,null,null);
        int idxId = c.getColumnIndex(HunterAppContract.Producao._ID);
        int idxTitulo = c.getColumnIndex(HunterAppContract.Producao.COLUMN_TITULO);
        int idxDescricao = c.getColumnIndex(HunterAppContract.Producao.COLUMN_DESCRICAO);
        int idxInicio = c.getColumnIndex(HunterAppContract.Producao.COLUMN_INICIO);
        int idxFim = c.getColumnIndex(HunterAppContract.Producao.COLUMN_FIM);
        int idxCategoria = c.getColumnIndex(HunterAppContract.Producao.COLUMN_CATEGORIA);
        int idxCandidato = c.getColumnIndex(HunterAppContract.Producao.COLUMN_CANDIDATO);

        c.moveToFirst();
        if (c.getCount()>0){

            Long idProducao = c.getLong(idxId);
            String titulo = c.getString(idxTitulo);
            String descricao = c.getString(idxDescricao);
            String inicio = c.getString(idxInicio);
            String fim = c.getString(idxFim);
            Long categoria = c.getLong(idxCategoria);
            Long candidato = c.getLong(idxCandidato);

            return new Producao(idProducao,titulo,descricao,inicio,fim,categoria,candidato);
        }
        return null;
    }

    public void atualizarProducao(Producao c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueProducao(c);

        String selecao = HunterAppContract.Producao._ID+ "= ?";
        String[] args = {c.getId()+""};

        db.update(HunterAppContract.Producao.TABLE_NAME,values,selecao, args);
    }

    public void inserirProducao(Producao c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueProducao(c);

        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);
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
    private ContentValues populateContentValueProducao(Producao p){
        ContentValues values = new ContentValues();
        values.put(HunterAppContract.Producao.COLUMN_TITULO,p.getTitulo());
        values.put(HunterAppContract.Producao.COLUMN_DESCRICAO,p.getDescricao());
        values.put(HunterAppContract.Producao.COLUMN_INICIO,p.getInicio());
        values.put(HunterAppContract.Producao.COLUMN_FIM,p.getFim());
        values.put(HunterAppContract.Producao.COLUMN_CATEGORIA,p.getCategoriaId());
        values.put(HunterAppContract.Producao.COLUMN_CANDIDATO,p.getCandidatoId());

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
    private final String[] camposProducao = {
            HunterAppContract.Producao._ID,
            HunterAppContract.Producao.COLUMN_TITULO,
            HunterAppContract.Producao.COLUMN_DESCRICAO,
            HunterAppContract.Producao.COLUMN_INICIO,
            HunterAppContract.Producao.COLUMN_FIM,
            HunterAppContract.Producao.COLUMN_CATEGORIA,
            HunterAppContract.Producao.COLUMN_CANDIDATO
    };

    //endregion

}
