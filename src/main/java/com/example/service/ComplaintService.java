package com.example.service;
import com.example.entity.Complaint;
import java.util.List;

public interface ComplaintService {

    void addComplaint(Complaint c);

    List<Complaint> getAllComplaints();

    void updateComplaint(
            int id,
            String status)
            throws Exception;

    void deleteComplaint(int id)
            throws Exception;

    Complaint findById(int id)
            throws Exception;

    void updateStatusUsingProcedure(
            int id,
            String status);
}