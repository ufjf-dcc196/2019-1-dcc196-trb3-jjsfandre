package br.ufjf.dcc196.hunterapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufjf.dcc196.hunterapp.Model.*;

public class HunterAppDBHelper extends SQLiteOpenHelper {
    //region Padrao

    public static final int DATABASE_VERSION=11;
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
        addAtividadeData(db);
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

        Long categoriaId = getCategoriaIdByOrder(db);
        Long categoriaId2 = getCategoriaIdByOrder(db,1);
        Long candidatoId = getCandidatoIdByOrder(db);
        Long candidatoId2 = getCandidatoIdByOrder(db,1);
        Long candidatoId3 = getCandidatoIdByOrder(db,2);
        Producao p1 = new Producao("Producao 1", "Descrição 1", "20/04/2019", "10/05/2019",categoriaId,candidatoId);
        Producao p2 = new Producao("Producao 2", "Descrição 2", "20/05/2019", "10/06/2019",categoriaId,candidatoId);
        Producao p3 = new Producao("Producao 3", "Descrição 3", "20/06/2019", "10/07/2019",categoriaId,candidatoId);
        Producao p4 = new Producao("Producao 4", "Descrição 4", "20/07/2019", "10/08/2019",categoriaId,candidatoId2);
        Producao p5 = new Producao("Producao 5", "Descrição 5", "20/08/2019", "10/09/2019",categoriaId2,candidatoId3);
        Producao p6 = new Producao("Producao 6", "Descrição 6", "20/09/2019", "10/10/2019",categoriaId2,candidatoId2);

        ContentValues values = populateContentValueProducao(p1);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p2);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p3);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p4);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p5);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);

        values = populateContentValueProducao(p6);
        db.insert(HunterAppContract.Producao.TABLE_NAME,null,values);
    }

    private void addAtividadeData(SQLiteDatabase db){
        Long producaoId = getProducaoIdByOrder(db, getCandidatoIdByOrder(db));
        Long producaoId2 = getProducaoIdByOrder(db, getCandidatoIdByOrder(db,1));
        Long producaoId3 = getProducaoIdByOrder(db, getCandidatoIdByOrder(db,2));
        Long producaoId4 = getProducaoIdByOrder(db, getCandidatoIdByOrder(db,1),1);
        Atividade a1 = new Atividade("Atividade 1", "10/04/2019",100.0,producaoId);
        Atividade a2 = new Atividade("Atividade 2", "10/05/2019",200.0,producaoId);
        Atividade a3 = new Atividade("Atividade 3", "10/06/2019",250.0,producaoId);
        Atividade a4 = new Atividade("Atividade 4", "10/07/2019",50.0,producaoId2);
        Atividade a5 = new Atividade("Atividade 5", "10/08/2019",50.0,producaoId2);
        Atividade a6 = new Atividade("Atividade 6", "10/09/2019",2.0,producaoId3);
        Atividade a7 = new Atividade("Atividade 7", "10/10/2019",5.0,producaoId3);
        Atividade a8 = new Atividade("Atividade 8", "10/09/2019",1.0,producaoId4);
        Atividade a9 = new Atividade("Atividade 9", "10/10/2019",2.0,producaoId4);


        ContentValues values = populateContentValueAtividade(a1);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a2);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a3);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a4);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a5);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a6);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a7);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a8);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);

        values = populateContentValueAtividade(a9);
        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);
    }

    private Long getCategoriaIdByOrder(SQLiteDatabase db){
        return getCategoriaIdByOrder(db,0);
    }
    private Long getCategoriaIdByOrder(SQLiteDatabase db, int order){
        Cursor c = getCursorTodasAsCategorias(db);

        int idxId = c.getColumnIndex(HunterAppContract.Categoria._ID);
        c.moveToPosition(order);
        if (c.getCount()>0) {
            Long idCategoria = c.getLong(idxId);
            return idCategoria;
        }
        return null;
    }
    private Long getCandidatoIdByOrder(SQLiteDatabase db){
        return getCandidatoIdByOrder(db,0);
    }
    private Long getCandidatoIdByOrder(SQLiteDatabase db, int order){
        Cursor c = getCursorTodosOsCandidatos(db);

        int idxId = c.getColumnIndex(HunterAppContract.Candidato._ID);
        c.moveToPosition(order);
        if (c.getCount()>0) {
            Long idCandidato = c.getLong(idxId);
            return idCandidato;
        }
        return null;
    }

    private Long getProducaoIdByOrder(SQLiteDatabase db, Long candidatoId){
        return getProducaoIdByOrder(db,candidatoId,0);
    }

    private Long getProducaoIdByOrder(SQLiteDatabase db, Long candidatoId, int order){
        Cursor c = getCursorTodasAsProducoesByCandidatoId(db,candidatoId);

        int idxId = c.getColumnIndex(HunterAppContract.Producao._ID);
        c.moveToPosition(order);
        if (c.getCount()>0) {
            Long idProducao = c.getLong(idxId);
            return idProducao;
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

    public List<Candidato> getListCandidatosPorCategoria(Long idCategoria){
        List<Candidato> result = new ArrayList<Candidato>();
        List<Producao> producoes = getProducaoByCategoriaId(idCategoria);
        List<Long> listaCandidatosIds = new ArrayList<>();

        Map<Long, Double> candidatosComSomaDeHoras = new HashMap<Long, Double>();

        for (Producao producao : producoes) {
            Long key =producao.getCandidatoId();
            Double sum = 0.0;
            if (candidatosComSomaDeHoras.containsKey(key))
                sum = candidatosComSomaDeHoras.get(key);
            else
                listaCandidatosIds.add(key);
            candidatosComSomaDeHoras.put(key,sum+producao.getSumHorasAtividades());
        }

        for (Long idCandidato :
                listaCandidatosIds) {
            Candidato candidato = getCandidatoById(idCandidato);
            candidato.setSumHorasAtividades(candidatosComSomaDeHoras.get(idCandidato));
            result.add(candidato);
        }

        Collections.sort(result, (d1, d2) -> {
            if (d1.getSumHorasAtividades()<d2.getSumHorasAtividades())
                return 1;
            else
                return -1;
        });
        
        return result;
    }

    //endregion

    //region Producao


    public Cursor getCursorTodasAsProducoesByCandidatoId(Long candidatoId){
        SQLiteDatabase db = this.getWritableDatabase();
        return getCursorTodasAsProducoesByCandidatoId(db,candidatoId);
    }
    public Cursor getCursorTodasAsProducoesByCandidatoId(String candidatoId){
        SQLiteDatabase db = this.getWritableDatabase();
        return getCursorTodasAsProducoesByCandidatoId(db,candidatoId);
    }

    public Cursor getCursorTodasAsProducoesByCandidatoId(SQLiteDatabase db,Long candidatoId){
        return getCursorTodasAsProducoesByCandidatoId(db,""+candidatoId);
    }

    public Cursor getCursorTodasAsProducoesByCandidatoId(SQLiteDatabase db,String candidatoId){
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

    public List<Producao> getProducaoByCategoriaId(Long idCategoria){
        List<Producao> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = HunterAppContract.Producao.COLUMN_CATEGORIA+ "= ?";
        String[] args = {idCategoria.toString()};
        Cursor c = db.query(HunterAppContract.Producao.TABLE_NAME,camposProducao,selecao,args,null,null,null);

        c.move(-1);

        int idxId = c.getColumnIndex(HunterAppContract.Producao._ID);
        int idxTitulo = c.getColumnIndex(HunterAppContract.Producao.COLUMN_TITULO);
        int idxDescricao = c.getColumnIndex(HunterAppContract.Producao.COLUMN_DESCRICAO);
        int idxInicio = c.getColumnIndex(HunterAppContract.Producao.COLUMN_INICIO);
        int idxFim = c.getColumnIndex(HunterAppContract.Producao.COLUMN_FIM);
        int idxCategoria = c.getColumnIndex(HunterAppContract.Producao.COLUMN_CATEGORIA);
        int idxCandidato = c.getColumnIndex(HunterAppContract.Producao.COLUMN_CANDIDATO);

        while(c.moveToNext()){
            Long id = c.getLong(idxId);
            String titulo = c.getString(idxTitulo);
            String descricao = c.getString(idxDescricao);
            String inicio = c.getString(idxInicio);
            String fim = c.getString(idxFim);
            Long categoriaId = c.getLong(idxCategoria);
            Long candidatoId = c.getLong(idxCandidato);
            Double sumHoras = getSumHorasByProducaoId(db,id);
            Producao producao = new Producao(id, titulo, descricao, inicio, fim, categoriaId, candidatoId);
            producao.setSumHorasAtividades(sumHoras);

            result.add(producao);
        }
        return result;
    }

    private Double getSumHorasByProducaoId(SQLiteDatabase db, Long idProducao){
        String selecao = HunterAppContract.Atividade.COLUMN_PRODUCAO+ "= ?";
        String[] args = {idProducao.toString()};
        Cursor c = db.query(HunterAppContract.Atividade.TABLE_NAME,camposAtividade,selecao,args,null,null,null);


        c.move(-1);
        Double result = 0.0;
        int idxHoras = c.getColumnIndex(HunterAppContract.Atividade.COLUMN_HORAS);
        while(c.moveToNext()){
            result += c.getDouble(idxHoras);
        }
        return result;
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

    //region Atividade
    public Cursor getCursorTodasAsAtividadesByProducaoId(Long producaoId){
        return getCursorTodasAsAtividadesByProducaoId(""+producaoId);
    }

    public Cursor getCursorTodasAsAtividadesByProducaoId(String producaoId){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = HunterAppContract.Atividade.COLUMN_DESCRICAO + " ASC";
        String select = HunterAppContract.Atividade.COLUMN_PRODUCAO+" = ?";

        String[] selectArgs = {producaoId};

        Cursor c = db.query(HunterAppContract.Atividade.TABLE_NAME, camposAtividade, select, selectArgs, null, null, sort);
        return c;
    }

    public void deleteAtividadeById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = HunterAppContract.Atividade._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(HunterAppContract.Atividade.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

    public Atividade getAtividadeById(Long id) {
        return getAtividadeById(id+"");
    }

    public Atividade getAtividadeById(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = HunterAppContract.Atividade._ID+ "= ?";
        String[] args = {id};
        Cursor c = db.query(HunterAppContract.Atividade.TABLE_NAME,camposAtividade,selecao,args,null,null,null);
        int idxId = c.getColumnIndex(HunterAppContract.Atividade._ID);
        int idxDescricao = c.getColumnIndex(HunterAppContract.Atividade.COLUMN_DESCRICAO);
        int idxData = c.getColumnIndex(HunterAppContract.Atividade.COLUMN_DATA);
        int idxHoras = c.getColumnIndex(HunterAppContract.Atividade.COLUMN_HORAS);
        int idxProducao = c.getColumnIndex(HunterAppContract.Atividade.COLUMN_PRODUCAO);


        c.moveToFirst();
        if (c.getCount()>0){

            Long idAtividade = c.getLong(idxId);
            String descricao = c.getString(idxDescricao);
            String data = c.getString(idxData);
            Double horas = c.getDouble(idxHoras);
            Long producao = c.getLong(idxProducao);

            return new Atividade(idAtividade,descricao,data,horas,producao);
        }
        return null;
    }

    public void atualizarAtividade(Atividade c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueAtividade(c);

        String selecao = HunterAppContract.Atividade._ID+ "= ?";
        String[] args = {c.getId()+""};

        db.update(HunterAppContract.Atividade.TABLE_NAME,values,selecao, args);
    }

    public void inserirAtividade(Atividade c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueAtividade(c);

        db.insert(HunterAppContract.Atividade.TABLE_NAME,null,values);
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
    private ContentValues populateContentValueAtividade(Atividade a){
        ContentValues values = new ContentValues();
        values.put(HunterAppContract.Atividade.COLUMN_DESCRICAO,a.getDescricao());
        values.put(HunterAppContract.Atividade.COLUMN_DATA,a.getData());
        values.put(HunterAppContract.Atividade.COLUMN_HORAS,a.getHoras());
        values.put(HunterAppContract.Atividade.COLUMN_PRODUCAO,a.getProducaoId());

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

    private final String[] camposAtividade = {
            HunterAppContract.Atividade._ID,
            HunterAppContract.Atividade.COLUMN_DESCRICAO,
            HunterAppContract.Atividade.COLUMN_DATA,
            HunterAppContract.Atividade.COLUMN_HORAS,
            HunterAppContract.Atividade.COLUMN_PRODUCAO
    };

    //endregion

}
