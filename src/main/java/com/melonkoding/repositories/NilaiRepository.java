package com.melonkoding.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melonkoding.models.Mahasiswa;
import com.melonkoding.models.Matakuliah;
import com.melonkoding.models.Nilai;

public class NilaiRepository {
    
    private Connection connection;

    public NilaiRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Nilai> getAllNilai() throws SQLException {
        List<Nilai> nilaiList = new ArrayList<>();

        String sql = "SELECT * FROM mahasiswa mh JOIN nilai nl ON mh.nim = nl.nim JOIN matakuliah mt ON nl.kode_mk = mt.kode_mk ORDER BY mh.nama ASC";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Nilai nilai = new Nilai(
                new Matakuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), rs.getInt("sks"), rs.getInt("semester")), 
                new Mahasiswa(rs.getString("nim"), rs.getString("nama")), 
                rs.getInt("nilai")
            );
            nilaiList.add(nilai);
        }
        return nilaiList;
    }

    public List<Nilai> getNilaiByNim(String nim) throws SQLException {
        List<Nilai> nilaiList = new ArrayList<>();
        
        String sql = "SELECT * FROM mahasiswa mh JOIN nilai nl ON mh.nim = nl.nim JOIN matakuliah mt ON nl.kode_mk = mt.kode_mk AND mh.nim = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, nim);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            Nilai nilai = new Nilai(
                new Matakuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), rs.getInt("sks"), rs.getInt("semester")), 
                new Mahasiswa(rs.getString("nim"), rs.getString("nama")), 
                rs.getInt("nilai")
            );
            nilaiList.add(nilai);
        }
        return nilaiList;
    }

    public boolean addNilai(Mahasiswa mahasiswa, Matakuliah matakuliah, int nilai) throws SQLException {
        String sql = "INSERT INTO nilai VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, mahasiswa.getNim());
        ps.setString(2, matakuliah.getKode_mk());
        ps.setInt(3, nilai);

        return ps.execute();
    }
}
