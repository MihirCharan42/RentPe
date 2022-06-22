package com.example.rentpe.model;

public class Transaction {
    String Time, TransactionID, UserIDofLandlord, UserIDofTenant, NameL, NameT;

    public String getNameL() {
        return NameL;
    }

    public void setNameL(String nameL) {
        NameL = nameL;
    }

    public String getNameT() {
        return NameT;
    }

    public void setNameT(String nameT) {
        NameT = nameT;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getUserIDofLandlord() {
        return UserIDofLandlord;
    }

    public void setUserIDofLandlord(String userIDofLandlord) {
        UserIDofLandlord = userIDofLandlord;
    }

    public String getUserIDofTenant() {
        return UserIDofTenant;
    }

    public void setUserIDofTenant(String userIDofTenant) {
        UserIDofTenant = userIDofTenant;
    }
}
