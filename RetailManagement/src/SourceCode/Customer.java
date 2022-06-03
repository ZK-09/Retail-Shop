package SourceCode;

/*** Class customer is a sub-class of user ***/
public class Customer extends User{
    
    /*** Data Type ***/
    public String cUserName;
    public String cPhoneNum;
    public String cEmail;
    public String cMailingAdd;

    
    
    public Customer(String cUserName, String cPhoneNum, String cEmail, String cMailingAdd) {
        this.cUserName = cUserName;
        this.cPhoneNum = cPhoneNum;
        this.cEmail = cEmail;
        this.cMailingAdd = cMailingAdd;
    }

    @Override
    public String toString() {
        return "Customer{" + "cUserName=" + cUserName + ", cPhoneNum=" + cPhoneNum + ", cEmail=" + cEmail + ", cMailingAdd=" + cMailingAdd + '}';
    }
}
