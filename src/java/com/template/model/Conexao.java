package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//essa classe estabelece a conexão com o banco de dados
public class Conexao {
    static String conexao = "jdbc:postgresql://localhost:5432/postgres";
    static String usuario = "postgres";
    static String senha = "postgres";


    public Connection conectaBD(){
        try{
            return DriverManager.getConnection(conexao,usuario,senha);
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
