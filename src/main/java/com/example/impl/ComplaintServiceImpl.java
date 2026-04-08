package com.example.impl;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.config.DBUtil;
import com.example.exception.ComplaintNotFoundException;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ComplaintServiceImpl
        implements ComplaintService {

    @Override
    public void addComplaint(Complaint c) {

        try (Connection con =
                     DBUtil.getConnection()) {

            String sql =
                    "INSERT INTO complaints(name, description, status) VALUES (?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1,
                    DBUtil.handleNull(c.getName()));

            ps.setString(2,
                    DBUtil.handleNull(c.getDescription()));

            ps.setString(3,
                    DBUtil.handleNull(c.getStatus()));

            ps.executeUpdate();

            System.out.println("Complaint Added!");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Override
    public List<Complaint> getAllComplaints() {

        List<Complaint> list =
                new ArrayList<>();

        try (Connection con =
                     DBUtil.getConnection()) {

            ResultSet rs =
                    con.createStatement()
                            .executeQuery(
                                    "SELECT * FROM complaints");

            while (rs.next()) {

                list.add(new Complaint(

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status")

                ));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list.stream()
                .filter(c ->
                        c.getStatus()
                                .equalsIgnoreCase("OPEN"))
                .collect(Collectors.toList());
    }

    @Override
    public Complaint findById(int id)
            throws Exception {

        return getAllComplaints().stream()

                .filter(c ->
                        c.getId() == id)

                .findFirst()

                .orElseThrow(() ->
                        new ComplaintNotFoundException(
                                "Complaint not found"));
    }

    @Override
    public void updateComplaint(
            int id,
            String status)
            throws Exception {

        try (Connection con =
                     DBUtil.getConnection()) {

            String sql =
                    "UPDATE complaints SET status=? WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0) {

                throw new ComplaintNotFoundException(
                        "No complaint found");

            }

        }
    }

    @Override
    public void deleteComplaint(int id)
            throws Exception {

        try (Connection con =
                     DBUtil.getConnection()) {

            String sql =
                    "DELETE FROM complaints WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {

                throw new ComplaintNotFoundException(
                        "Delete failed");

            }

        }
    }

    @Override
    public void updateStatusUsingProcedure(
            int id,
            String status) {

        try (Connection con =
                     DBUtil.getConnection()) {

            CallableStatement cs =
                    con.prepareCall(
                            "{call update_status_proc(?, ?)}");

            cs.setInt(1, id);
            cs.setString(2, status);

            cs.execute();

            System.out.println(
                    "Updated via procedure!");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}