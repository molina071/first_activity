package com.example.first_activity.configuration;

public class transacciones {

    //DB NAME
    public static final String dbName = "DBPM01";

    public static final int version = 1;
   //table
    public static final String tbPersons = "tbPersons";
    //tb persons field
    public static final  String id = "id";
    public static final  String nombre = "nombre";
    public static final  String apellido = "apellido";
    public static final  String edad = "edad" ;
    public static final  String correo = "correo";
    public static  final  String foto = "foto";

    //DDL es una sintaxis utilizada para crear, modificar y eliminar objetos.

    //DDL CREATE
    public static final String createTablePerson = "CREATE TABLE " + tbPersons + " ( " +

            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            nombre + " TEXT, " +
            apellido + " TEXT, " +
            edad + " TEXT, " +
            correo + " TEXT " +
            foto +" TEXT);";

    //DDL DROP
    public static final String dropTablePerson = "DROP TABLE IF Exist   " + tbPersons;

    public static final String selectTablePerson = " SELECT * FROM " + tbPersons;

}
