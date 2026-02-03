package com.example.first_activity.configuration;

public class transacciones {

    //DB NAME
    public static final String dbName = "DBPM01";
   //table
    public static final String tbPerons = "personas";
    //tb persons field
    public static final  String id = "id";
    public static final  String nombre = "nombre";
    public static final  String apellido = "apellido";
    public static final  String edad = "edad" ;
    public static final  String correo = "correo";
    public static  final  String foto = "foto";

    public static final String createTablePerson = "CREATE TABLE"+ tbPerons + " ( " +

            id + "INTEGER PRYMARY KEY AUTOINCREMENT, " +
            nombre + "TEXT, " +
            apellido + "TEXT, " +
            edad + "TEXT, " +
            correo + "TEXT" +
            foto +"TEXT)";
}
