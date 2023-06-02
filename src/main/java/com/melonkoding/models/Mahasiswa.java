package com.melonkoding.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Mahasiswa {
    
    String nim;
    String nama;

    public Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }
}
