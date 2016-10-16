package com.example.abraham.iNotify;

/**
 * Created by abraham on 2/3/2015.
 */
public class Utilities  {



    public static  String splitStringNewLine(String s) {
        StringBuilder st = new StringBuilder(s);

        int count =0;
        for(int i =0; i < st.length(); i++) {
            if(count == 20) {
                st.insert(i,">");
                count =0;
            } else {
                count++;
            }
        }

        System.out.println(st.length());
        return String.valueOf(st);
    }




    public static String convertEltoEn(String el) {

        char[] tmp;
        tmp = el.toCharArray();

        for(int i =0; i < tmp.length; i++) {

            if(tmp[i] == 'α' || tmp[i] == 'Α') {
                tmp[i] = 'a';
            }
            if(tmp[i] == 'β' || tmp[i] == 'Β') {
                tmp[i] = 'v';
            }
            if(tmp[i] == 'γ' || tmp[i] == 'Γ') {
                tmp[i] = 'g';
            }
            if(tmp[i] == 'δ' || tmp[i] == 'Δ') {
                tmp[i] = 'd';
            }
            if(tmp[i] == 'ε' || tmp[i] == 'Ε') {
                tmp[i] = 'e';
            }
            if(tmp[i] == 'ζ' || tmp[i] == 'Ζ') {
                tmp[i] = 'z';
            }
            if(tmp[i] == 'η' || tmp[i] == 'Η') {
                tmp[i] = 'h';
            }
            if(tmp[i] == 'θ' || tmp[i] == 'Θ') {
                tmp[i] = 'H';
            }
            if(tmp[i] == 'ι' || tmp[i] == 'Ι') {
                tmp[i] = 'i';
            }
            if(tmp[i] == 'κ' || tmp[i] == 'Κ') {
                tmp[i] = 'k';
            }
            if(tmp[i] == 'λ' || tmp[i] == 'Λ') {
                tmp[i] = 'L';
            }
            if(tmp[i] == 'μ' || tmp[i] == 'Μ') {
                tmp[i] = 'm';
            }
            if(tmp[i] == 'ν' || tmp[i] == 'Ν') {
                tmp[i] = 'n';
            }
            if(tmp[i] == 'ξ' || tmp[i] == 'Ξ') {
                tmp[i] = 'x';
            }
            if(tmp[i] == 'ο' || tmp[i] == 'Ο') {
                tmp[i] = 'o';
            }
            if(tmp[i] == 'π' || tmp[i] == 'Π') {
                tmp[i] = 'p';
            }
            if(tmp[i] == 'ρ' || tmp[i] == 'Ρ') {
                tmp[i] = 'R';
            }
            if(tmp[i] == 'σ' || tmp[i] == 'Σ') {
                tmp[i] = 's';
            }
            if(tmp[i] == 'ς') {
                tmp[i] = 'S';
            }
            if(tmp[i] == 'τ' || tmp[i] == 'Τ') {
                tmp[i] = 't';
            }
            if(tmp[i] == 'υ' || tmp[i] == 'Υ') {
                tmp[i] = 'y';
            }
            if(tmp[i] == 'φ' || tmp[i] == 'Φ') {
                tmp[i] = 'f';
            }
            if(tmp[i] == 'χ' || tmp[i] == 'Χ') {
                tmp[i] = 'x';
            }
            if(tmp[i] == 'ψ' || tmp[i] == 'Ψ') {
                tmp[i] = 'u';
            }
            if(tmp[i] == 'ω' || tmp[i] == 'Ω') {
                tmp[i] = 'w';
            }
        }

        String fnl = new String(tmp);
        return fnl;
    }



}
