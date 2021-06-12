package Client;


import Server.CenterServerI;
import Utilities.ManagerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Client.ManagerClientHelper.*;
import static Utilities.Configurations.getRegistryPort;

public class ManagerClient {


    public static void main(String[] args) throws IOException, NotBoundException {

        ManagerLogger managerLogger;
        Registry registry;

        Scanner scan = new Scanner(System.in);
        String[] strBuffer;
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String managerID = null;
        while (managerID == null) {
            System.out.println("Enter ManagerID: ");
            managerID = obj.readLine();
            if (!(ManagerClientHelper.checkManagerID(managerID) && checkIfSizeOne(managerID))) managerID = null;

        }
        registry = LocateRegistry.getRegistry(getRegistryPort(managerID.substring(0, 3)));

        CenterServerI centerServerI = (CenterServerI) registry.lookup(managerID.substring(0, 3));
        managerLogger = new ManagerLogger(managerID);
        managerLogger.addLog("Connection Established successfully with server: " + managerID.substring(0, 3));
        System.out.println("-------------------------------Welcome to DCMS------------------------------------");
        while (true) {
            System.out.println("---------Please enter command between 1-5 to proceed-----------");
            System.out.println("---Press 0 Anywhere in the system to return to main Menu---");
            System.out.println("1. Create Teacher Record");
            System.out.println("2. Create Student Record");
            System.out.println("3. Edit Record");
            System.out.println("4. Get Counts");
            System.out.println("5. Log Out");

            int command = getCommand();
            if (command == 0) continue;

            if (command == 1) {
                System.out.println("Creating Teacher Record");

                String firstName = getFirstName("Please Enter First Name:");
                if (checkIfZero(firstName)) continue;


                String lastName = getLastName("Please Enter Last Name:");
                if (checkIfZero(lastName)) continue;


                String address = getMultipleInput("Please Enter Address:");
                if (checkIfZero(address)) continue;


                String phone = getPhoneNo();
                if (checkIfZero(phone)) continue;

                String specialization = getSingleInput("Please Enter Specialization:");
                if (checkIfZero(specialization)) continue;


                String location = getLocation();
                if (checkIfZero(location)) continue;
                System.out.println("Sending data to server awaiting response:");
                String teacherRecord = centerServerI.createTRecord(firstName, lastName, address, phone, specialization, location);
                System.out.println("Response received:" + teacherRecord);
                managerLogger.addLog(teacherRecord);

            } else if (command == 2) {
                System.out.println("Creating Student Record");

                String firstName = getFirstName(("Please Enter First Name:"));
                if (checkIfZero(firstName)) continue;


                String lastName = getLastName("Please Enter Last Name:");
                if (checkIfZero(lastName)) continue;

                String line = getMultipleInput("Please Enter Course Registered  Separated by Space:");
                if (checkIfZero(line)) continue;

                String[] courses = line.split(" ");
                List<String> coursesRegistered = Arrays.asList(courses);


                String status = getStatus().toLowerCase();
                if (checkIfZero(status)) continue;


                String statusDate = getStatusDate();
                if (checkIfZero(statusDate)) continue;
                System.out.println("Sending data to server awaiting response:");
                String studentRecord = centerServerI.createSRecord(firstName, lastName, coursesRegistered, status, statusDate);
                System.out.println("Response received:" + studentRecord);
                managerLogger.addLog(studentRecord);

            } else if (command == 3) {
                System.out.println("Editing Record");
                System.out.println("Field names allowed to change for Teacher Record: address, phone & location");
                System.out.println("Field names allowed to change for Student Record: coursesRegistered, status and statusDate");

                String recordID = getRecordID();
                if (checkIfZero(recordID)) continue;


                String fieldName = getFieldName(recordID);
                if (checkIfZero(fieldName)) continue;

                String line = getFieldValue(fieldName);
                if (checkIfZero(line)) continue;

                String[] filedValue = line.split(" ");
                List<String> fieldData = Arrays.asList(filedValue);
                System.out.println("Sending data to server awaiting response:");
                String editedRecord = centerServerI.editRecord(recordID, fieldName, fieldData);
                System.out.println("Response received:" + editedRecord);
            } else if (command == 4) {
                System.out.println("Getting Records");
                String recordCounts = centerServerI.getRecordCounts();
                System.out.println(recordCounts);
                managerLogger.addLog("Record counts: " + recordCounts);
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
