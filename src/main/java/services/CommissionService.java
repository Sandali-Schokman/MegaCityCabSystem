package services;

import dao.CommissionDAO;

/**
 * Service class for handling commission updates
 */
public class CommissionService {
    private final CommissionDAO commissionDAO;

    public CommissionService() {
        this.commissionDAO = new CommissionDAO();
    }

    public double getCommission() {
        return commissionDAO.getCommissionPercentage();
    }

    public boolean updateCommission(double newCommission) {
        return commissionDAO.updateCommissionPercentage(newCommission);
    }
}
