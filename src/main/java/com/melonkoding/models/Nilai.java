package com.melonkoding.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Nilai {
    
    Matakuliah matakuliah;
    Mahasiswa mahasiswa;
    int nilai;

    public Nilai(Matakuliah matakuliah, Mahasiswa mahasiswa, int nilai) {
        this.matakuliah = matakuliah;
        this.mahasiswa = mahasiswa;
        this.nilai = nilai;
    }
}
