package com.melonkoding.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melonkoding.models.Mahasiswa;

public class MahasiswaRepository {
    
    private Connection connection;

    public MahasiswaRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Mahasiswa> getAllMahasiswa() throws SQLException {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();

        String sql = "SELECT * FROM mahasiswa";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Mahasiswa mahasiswa = new Mahasiswa(rs.getString("nim"), rs.getString("nama"));
            mahasiswaList.add(mahasiswa);
        }
        return mahasiswaList;
    }

    public Mahasiswa getMahasiswaByNim(String nim) throws SQLException {
        Mahasiswa mahasiswa = null;

        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, nim);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            mahasiswa = new Mahasiswa(rs.getString("nim"), rs.getString("nama"));
        }
        return mahasiswa;
    }

    public boolean addMahasiswa(Mahasiswa mahasiswa) throws SQLException {
        String sql = "INSERT INTO mahasiswa VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, mahasiswa.getNim());
        ps.setString(2, mahasiswa.getNama());

        return ps.execute();
    }
}
