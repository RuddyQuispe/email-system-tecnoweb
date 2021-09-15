package edu.bo.uagrm.ficct.inf513.business;

import edu.bo.uagrm.ficct.inf513.utils.Encrypt;
import edu.bo.uagrm.ficct.inf513.data.PartnerData;
import edu.bo.uagrm.ficct.inf513.data.UserData;

import java.sql.Date;
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
    private PartnerData partnerData;

    public PartnerBusiness() {
        this.userData = new UserData();
        this.partnerData = new PartnerData();
    }

    /**
     * create new User Partner
     * int ci, String name, String phone, String email, String password, String address
     * Date affiliationDate, int storeNo, String typePartner
     *
     * @param parameters list parameters data partner user
     * @return message to returns
     */
    public String createPartner(List<String> parameters) {
        if (parameters.size() != 9) return "data partner incomplete";
        try {
            ResultSet response = this.userData.findBy("ci", parameters.get(0));
            if (response == null) return "Error to search ci partner";
            if (!response.next()) {
                boolean isCreatedUser = this.userData.create(
                        Integer.parseInt(parameters.get(0)),
                        parameters.get(1),
                        parameters.get(2),
                        parameters.get(3),
                        Encrypt.encrypt(parameters.get(4)),
                        parameters.get(5),
                        "P"
                );
                boolean isCreatedPartner = this.partnerData.create(
                        Integer.parseInt(parameters.get(0)),
                        Date.valueOf(parameters.get(6)),
                        Integer.parseInt(parameters.get(7)),
                        parameters.get(8)
                );
                return isCreatedUser && isCreatedPartner ? "saved partner successfully" : "I have an error to create partner";
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
     * Date affiliationDate, int storeNo, String typePartner (1 active or 2 passive)
     *
     * @param parameters list info partner
     * @return String message if updated or has an error
     */
    public String updatePartner(List<String> parameters) {
        if (parameters.size() != 10) return "data partner incomplete";
        try {
            ResultSet response = this.userData.findBy("ci", parameters.get(0));
            if (response == null) return "Error to search ci partner";
            if (response.next()) {
                boolean isUpdatedUser = this.userData.update(
                        Integer.parseInt(parameters.get(0)),
                        parameters.get(1),
                        parameters.get(2),
                        parameters.get(3),
                        Encrypt.encrypt(parameters.get(4)),
                        Boolean.valueOf(parameters.get(5)),
                        parameters.get(6),
                        "P"
                );
                boolean isUpdatedPartner = this.partnerData.update(
                        Integer.parseInt(parameters.get(0)),
                        Date.valueOf(parameters.get(7)),
                        Integer.parseInt(parameters.get(8)),
                        parameters.get(9)
                );
                return isUpdatedUser && isUpdatedPartner ? "updated partner successfully" : "I have an error to update partner";
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
                boolean isRemovedPartner = this.partnerData.remove(Integer.parseInt(parameters.get(0)));
                boolean isRemovedUser = this.userData.remove(Integer.parseInt(parameters.get(0)));
                return isRemovedPartner && isRemovedUser ? "removed partner successfully" : "I have an error to remove partner";
            } else {
                return "Doesn't exists partner user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "I have an error to remove partner";
        }
    }

    public static void main(String[] args) {
        PartnerBusiness partner = new PartnerBusiness();
        String response = partner.createPartner(Arrays.asList(
                "6868170",
                "ruddy quispe mamni",
                "75395373",
                "ruddy@yahoo.com",
                "11235813",
                "av la barranca #560",
                "2021-09-14",
                "168",
                "1"));
        //String response = partner.updatePartner(Arrays.asList(
        //        "6868161",
        //        "ruddy mamani",
        //        "75395371",
        //        "ruddy@gmx.com",
        //        "11235813",
        //        "false",
        //        "av la barranca #560"));
        //String response = partner.removePartner(Arrays.asList("6868162"));
        System.out.println(response);
    }
}
