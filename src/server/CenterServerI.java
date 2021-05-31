package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface CenterServerI extends Remote {

    public String createTRecord(String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException;

    public String createSRecord(String firstName, String lastName, List<String> courseRegistered, String status, String statusDate) throws RemoteException;

    public String getRecordCounts() throws RemoteException;

    public String editRecord(String recordID, String fieldName, List<String> newValue) throws RemoteException;

}
