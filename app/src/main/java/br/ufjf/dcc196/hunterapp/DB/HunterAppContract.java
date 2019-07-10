package br.ufjf.dcc196.hunterapp.DB;

import android.provider.BaseColumns;

public final class HunterAppContract {

    public static class Candidato implements BaseColumns{

        public static final String TABLE_NAME = "Candidato";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_NASCIMENTO = "nascimento";
        public static final String COLUMN_TELEFONE = "telefone";
        public static final String COLUMN_PERFIL = "perfil";
        public static final String COLUMN_EMAIL = "email";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",TABLE_NAME, _ID, COLUMN_NOME, COLUMN_NASCIMENTO,
            COLUMN_TELEFONE,COLUMN_PERFIL,COLUMN_EMAIL);
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }
    public static class Producao implements BaseColumns{

        public static final String TABLE_NAME = "Producao";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_INICIO = "inicio";
        public static final String COLUMN_FIM = "fim";
        public static final String COLUMN_CATEGORIA = "categoriaId";
        public static final String COLUMN_CANDIDATO = "candidatoId";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER)",TABLE_NAME, _ID, COLUMN_TITULO, COLUMN_DESCRICAO,
                COLUMN_INICIO,COLUMN_FIM,COLUMN_CATEGORIA,COLUMN_CANDIDATO);

        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }
    public static class Categoria implements BaseColumns{

        public static final String TABLE_NAME = "Categoria";
        public static final String COLUMN_TITULO = "titulo";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT)",TABLE_NAME, _ID, COLUMN_TITULO);
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }

    public static class Atividade implements BaseColumns{

        public static final String TABLE_NAME = "Atividade";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_HORAS = "horas";
        public static final String COLUMN_PRODUCAO = "producaoId";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT, %s TEXT, %s INTEGER)",TABLE_NAME, _ID, COLUMN_DESCRICAO,
                COLUMN_DATA,COLUMN_HORAS,COLUMN_PRODUCAO);
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }


}
