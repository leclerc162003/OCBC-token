package sg.edu.np.onetokenocbc;

import java.io.Serializable;

public class AccountDetails implements Serializable {

    private String AccountNo;
    private String MainHolderID;
    private String SubHolderID;


    public AccountDetails() {
    }

    public AccountDetails(String accountNo, String mainHolderID, String subHolderID) {
        AccountNo = accountNo;
        MainHolderID = mainHolderID;
        SubHolderID = subHolderID;
    }


    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getMainHolderID() {
        return MainHolderID;
    }

    public void setMainHolderID(String mainHolderID) {
        MainHolderID = mainHolderID;
    }

    public String getSubHolderID() {
        return SubHolderID;
    }

    public void setSubHolderID(String subHolderID) {
        SubHolderID = subHolderID;
    }
}
