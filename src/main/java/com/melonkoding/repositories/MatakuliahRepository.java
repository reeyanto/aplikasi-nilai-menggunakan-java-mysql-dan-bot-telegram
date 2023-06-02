package com.melonkoding.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.melonkoding.models.Matakuliah;

public class MatakuliahRepository {
    
    private Connection connection;

    public MatakuliahRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Matakuliah> getAllMatakuliah() throws SQLException {
        List<Matakuliah> matakuliahList = new ArrayList<>();

        String sql = "SELECT * FROM matakuliah";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Matakuliah matakuliah = new Matakuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), rs.getInt("sks"), rs.getInt("semester"));
            matakuliahList.add(matakuliah);
        }
        return matakuliahList;
    }

    public Matakuliah getMatakuliahByKode(String kode_mk) throws SQLException {
        Matakuliah matakuliah = null;

        String sql = "SELECT * FROM matakuliah WHERE kode_mk = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, kode_mk);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            matakuliah = new Matakuliah(rs.getString("kode_mk"), rs.getString("nama_mk"), rs.getInt("sks"), rs.getInt("semester"));
        }
        return matakuliah;
    }

    public boolean addMatakuliah(Matakuliah matakuliah) throws SQLException {
        String sql = "INSERT INTO matakuliah VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, matakuliah.getKode_mk());
        ps.setString(2, matakuliah.getNama_mk());
        ps.setInt(3, matakuliah.getSks());
        ps.setInt(4, matakuliah.getSemester());

        return ps.execute();
    }
}
