package com.melonkoding.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Matakuliah {
    
    String kode_mk;
    String nama_mk;
    int sks;
    int semester;

    public Matakuliah(String kode_mk, String nama_mk, int sks, int semester) {
        this.kode_mk = kode_mk;
        this.nama_mk = nama_mk;
        this.sks = sks;
        this.semester = semester;
    }
}
