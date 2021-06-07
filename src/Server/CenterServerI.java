package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CenterServerI extends Remote {

    String createTRecord(String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException;

    String createSRecord(String firstName, String lastName, List<String> courseRegistered, String status, String statusDate) throws RemoteException;

    String getRecordCounts() throws RemoteException;

    String editRecord(String recordID, String fieldName, List<String> newValue) throws RemoteException;

}
