package Client;


import Server.CenterServerI;
import Utilities.ManagerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;


import static Client.ManagerClientHelper.checkIfZero;
import static Utilities.Configurations.getRegistryPort;

public class ManagerClient {


    public static void main(String[] args) throws IOException, NotBoundException {

        ManagerLogger managerLogger;
        Registry registry;

        Scanner scan = new Scanner(System.in);
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String managerID = null;
        while (managerID == null) {
            System.out.println("Enter ManagerID: ");
            managerID = scan.next();
            if (!ManagerClientHelper.checkManagerID(managerID)) managerID = null;

        }
        registry = LocateRegistry.getRegistry(getRegistryPort(managerID.substring(0, 3)));

        CenterServerI centerServerI = (CenterServerI) registry.lookup(managerID.substring(0, 3));
        managerLogger = new ManagerLogger(managerID);
        System.out.println("----------------------------Welcome to DCMS---------------------------------");
        while (true) {
            System.out.println("---------Please enter command between 1-5 to proceed-----------");
            System.out.println("1. Create Teacher Record");
            System.out.println("2 .Create Student Record");
            System.out.println("3. Edit Record");
            System.out.println("4. Get Counts");
            System.out.println("5. Log Out");
            int command;
            try {
                command = Integer.parseInt(scan.next());
            } catch (NumberFormatException e) {
                System.out.println("Please Enter a numeric command");
                continue;
            }

            if (command == 1) {
                System.out.println("Creating Teacher Record");
                System.out.println("Please Enter First Name OR Enter 0 to go to Main Menu");
                String firstName = scan.next();
                if (checkIfZero(firstName)) continue;

                System.out.println("Please Enter Last Name OR Enter 0 to go to Main Menu");
                String lastName = scan.next();
                if (checkIfZero(lastName)) continue;

                String address = null;
                while (address == null || address.length() == 0) {
                    System.out.println("Please Enter Address OR Enter 0 to go to Main Menu");
                    address = obj.readLine();
                }
                if (checkIfZero(address)) continue;

                String phoneNo = null;
                while (phoneNo == null) {

                    System.out.println("Please Enter Phone No OR Enter 0 to go to Main Menu");
                    phoneNo = scan.next();
                    if (checkIfZero(phoneNo)) break;
                    if (!ManagerClientHelper.checkPhone(phoneNo)) phoneNo = null;
                }
                if (checkIfZero(phoneNo)) continue;
                System.out.println("Please Enter Specialization OR Enter 0 to go to Main Menu");
                String specialization = scan.next();
                if (checkIfZero(specialization)) continue;

                System.out.println("Please Enter Location OR Enter 0 to go to Main Menu");
                String location = scan.next();
                if (checkIfZero(location)) continue;

                String teacherRecord = centerServerI.createTRecord(firstName, lastName, address, phoneNo, specialization, location);
                System.out.println(teacherRecord);
                managerLogger.addLog(teacherRecord);

            } else if (command == 2) {
                System.out.println("Creating Student Record");
                System.out.println("Please Enter First Name OR Enter 0 to go to Main Menu");
                String firstName = scan.next();
                if (checkIfZero(firstName)) continue;

                System.out.println("Please Enter Last Name OR Enter 0 to go to Main Menu");
                String lastName = scan.next();
                if (checkIfZero(lastName)) continue;

                String line = null;
                while (line == null || line.length() == 0) {
                    System.out.println("Please Enter Course Registered OR Enter 0 to go to Main Menu");
                    line = obj.readLine();

                }
                if (checkIfZero(line)) continue;

                String[] courses = line.split(" ");
                List<String> coursesRegistered = Arrays.asList(courses);


                String status = null;
                while (status == null) {
                    System.out.println("Please Enter status: active/inactive OR Enter 0 to go to Main Menu");
                    status = scan.next();

                    if (!ManagerClientHelper.checkStatus(status)) status = null;
                }


                String statusDate = null;
                while (statusDate == null) {

                    System.out.println("Please Enter Status Date in DD-MM-YYYY OR Enter 0 to go to Main Menu");
                    statusDate = scan.next();
                    if (checkIfZero(statusDate)) break;
                    if (!ManagerClientHelper.checkStatusDate(statusDate)) statusDate = null;
                }
                if (checkIfZero(statusDate)) continue;

                String studentRecord = centerServerI.createSRecord(firstName, lastName, coursesRegistered, status, statusDate);
                System.out.println(studentRecord);
                managerLogger.addLog(studentRecord);

            } else if (command == 3) {
                System.out.println("Editing Record");
                String recordID = null;
                while (recordID == null) {

                    System.out.println("Enter RecordID OR Enter 0 to go to Main Menu");
                    recordID = scan.next();
                    if (checkIfZero(recordID)) break;
                    if (!ManagerClientHelper.checkRecordID(recordID)) recordID = null;
                }
                if (checkIfZero(recordID)) continue;


                System.out.println("Enter Field Name OR Enter 0 to go to Main Menu");
                String fieldName = scan.next();
                if (checkIfZero(fieldName)) continue;

                System.out.println("Enter Filed Value OR Enter 0 to go to Main Menu");
                String line = obj.readLine();
                if (checkIfZero(line)) continue;

                String[] filedValue = line.split(" ");
                List<String> fieldData = Arrays.asList(filedValue);
                String editedRecord = centerServerI.editRecord(recordID, fieldName, fieldData);
                System.out.println(editedRecord);
            } else if (command == 4) {
                System.out.println("Getting Records");
                String recordCounts = centerServerI.getRecordCounts();
                System.out.println(recordCounts);
                managerLogger.addLog(recordCounts);
            } else if (command == 5) {
                System.out.println("-----------------------Logging out------------------");
                managerLogger.addLog("Manager with " + managerID + " logged out of the system");
                break;
            } else {

                System.out.println("Please Enter Valid Command");
            }
        }
    }
}
