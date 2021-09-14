package edu.bo.uagrm.ficct.inf513.business;

import edu.bo.uagrm.ficct.inf513.business_data.Encrypt;
import edu.bo.uagrm.ficct.inf513.data.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @project email-system-tecnoweb
 * @autor ruddy
 * @date 13/9/21 11:58
 */
public class PartnerBusiness {
    private UserData userData;

    public PartnerBusiness() {
        this.userData = new UserData();
    }

    public String createPartner(List<String> parameters) {
        if (parameters.size() != 6) return "data partner incomplete";
        try {
            ResultSet response = this.userData.findBy("ci", parameters.get(0));
            if (response == null) return "Error to search ci partner";
            if (!response.next()) {
                boolean isCreated = this.userData.create(
                        Integer.parseInt(parameters.get(0)),
                        parameters.get(1),
                        parameters.get(2),
                        parameters.get(3),
                        Encrypt.encrypt(parameters.get(4)),
                        parameters.get(5),
                        "P"
                );
                return isCreated ? "saved partner successfully" : "I have an error to create partner";
            } else {
                return "Does exists partner user, don't have repeat";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to create partner";
        }
    }

    /**
     * Update user partner
     * int ci, String name, String phone, String email, String password, boolean status, String address
     *
     * @param parameters list info partner
     * @return String message if updated or has an error
     */
    public String updatePartner(List<String> parameters) {
        if (parameters.size() != 7) return "data partner incomplete";
        try {
            ResultSet response = this.userData.findBy("ci", parameters.get(0));
            if (response == null) return "Error to search ci partner";
            if (response.next()) {
                boolean isUpdated = this.userData.update(
                        Integer.parseInt(parameters.get(0)),
                        parameters.get(1),
                        parameters.get(2),
                        parameters.get(3),
                        Encrypt.encrypt(parameters.get(4)),
                        Boolean.valueOf(parameters.get(5)),
                        parameters.get(6),
                        "P"
                );
                return isUpdated ? "updated partner successfully" : "I have an error to update partner";
            } else {
                return "Doesn't exists partner user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to update partner";
        }
    }

    /**
     * remove partner user
     *
     * @param parameters: list data info partner (ci) only
     * @return true if removed success else return false
     */
    public String removePartner(List<String> parameters) {
        if (parameters.size() != 1) return "data partner incomplete";
        try {
            ResultSet response = this.userData.findBy("ci", parameters.get(0));
            if (response == null) return "Error to search ci partner";
            if (response.next()) {
                boolean isRemoved = this.userData.remove(Integer.parseInt(parameters.get(0)));
                return isRemoved ? "removed partner successfully" : "I have an error to remove partner";
            } else {
                System.out.println("removide");
                return "Doesn't exists partner user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to remove partner";
        }
    }

    public static void main(String[] args) {
        PartnerBusiness partner = new PartnerBusiness();
        // String response = partner.createPartner(Arrays.asList(
        //        "6868162",
        //        "ruddy quispe",
        //        "75395373",
        //        "ruddy@yahoo.com",
        //        "11235813",
        //        "av la barranca #560"));
        //String response = partner.updatePartner(Arrays.asList(
        //        "6868161",
        //        "ruddy mamani",
        //        "75395371",
        //        "ruddy@gmx.com",
        //        "11235813",
        //        "false",
        //        "av la barranca #560"));
        String response = partner.removePartner(Arrays.asList("6868162"));
        System.out.println(response);
    }
}
