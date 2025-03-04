package services;

import dao.ComplaintDAO;
import dto.ComplaintDTO;
import java.util.List;

//  Facade Pattern: Simplifies interactions between controllers and the DAO
public class ComplaintService {
    private final ComplaintDAO complaintDAO = new ComplaintDAO();

    // Register a new complaint
    public boolean registerComplaint(ComplaintDTO complaint) {
        return complaintDAO.registerComplaint(complaint);
    }

    // Get all complaints
    public List<ComplaintDTO> getAllComplaints() {
        return complaintDAO.getAllComplaints();
    }

    // Get complaints by status (Pending, Resolved, etc.)
    public List<ComplaintDTO> getComplaintsByStatus(String status) {
        return complaintDAO.getComplaintsByStatus(status);
    }

    // Update complaint status (Handled by the Manager/Admin)
    public boolean updateComplaintStatus(int complaintId, String newStatus) {
        return complaintDAO.updateComplaintStatus(complaintId, newStatus);
    }

    public boolean resolveComplaint(int complaintId) {
        return complaintDAO.updateComplaintStatus(complaintId, "RESOLVED");
    }

}
