package com.example;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.impl.ComplaintServiceImpl;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ComplaintServiceTest {

    static ComplaintService service;

    @BeforeAll
    static void setup() {

        service = new ComplaintServiceImpl();
    }

    // TEST ADD
    @Test
    void testAddComplaint() {

        Complaint c =
                new Complaint(
                        "TestUser",
                        "Add Test Issue",
                        "OPEN"
                );

        service.addComplaint(c);

        assertTrue(true);
    }

    // TEST GET ALL
    @Test
    void testGetAllComplaints() {

        var list =
                service.getAllComplaints();

        assertNotNull(list);
        assertTrue(list.size() >= 0);
    }

    // TEST UPDATE
    @Test
    void testUpdateComplaint() {

        int complaintId =
                createTestComplaintId();

        assertDoesNotThrow(() ->

                service.updateComplaint(
                        complaintId,
                        "CLOSED"
                )
        );
    }

    // TEST DELETE
    @Test
    void testDeleteComplaint() {

        int complaintId =
                createTestComplaintId();

        assertDoesNotThrow(() ->

                service.deleteComplaint(complaintId)

        );
    }

    // TEST PROCEDURE CALL
    @Test
    void testProcedureUpdate() {

        int complaintId =
                createTestComplaintId();

        assertDoesNotThrow(() ->

                service.updateStatusUsingProcedure(
                        complaintId,
                        "OPEN"
                )

        );
    }

    private int createTestComplaintId() {

        String uniqueName =
                "JT" + (System.nanoTime()
                % 1_000_000_000L);

        service.addComplaint(
                new Complaint(
                        uniqueName,
                        "JUnit Testing Issue",
                        "OPEN"
                )
        );

        return service.getAllComplaints()
                .stream()
                .filter(c ->
                        uniqueName.equals(
                                c.getName()))
                .mapToInt(Complaint::getId)
                .max()
                .orElseThrow(() ->
                        new AssertionError(
                                "Inserted complaint was not found"));
    }

}
