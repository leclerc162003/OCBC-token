package sg.edu.np.onetokenocbc;

import java.io.Serializable;

public class BankAccount implements Serializable {
    private String AccountNo;
    private String CIFID;
    private String CardNo;
    private String CVV;
    private String Balance;
    private int Pending;


    public BankAccount() {
    }

    public BankAccount(String accountNo, String CIFID, String cardNo, String CVV, String balance, int pending) {
        AccountNo = accountNo;
        this.CIFID = CIFID;
        CardNo = cardNo;
        this.CVV = CVV;
        Balance = balance;
        Pending = pending;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getCIFID() {
        return CIFID;
    }

    public void setCIFID(String CIFID) {
        this.CIFID = CIFID;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public int getPending() {
        return Pending;
    }

    public void setPending(int pending) {
        Pending = pending;
    }
}
