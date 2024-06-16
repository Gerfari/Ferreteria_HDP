/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.utils;

/**
 *
 * @author herna
 */
public class CrearPassword {
    public static void main(String[] args) {
        String cadena = Encriptar.getStringMessageDigest("123", Encriptar.SHA256);
        System.out.println(cadena);
    }
}
