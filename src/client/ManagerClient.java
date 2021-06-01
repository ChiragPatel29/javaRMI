package client;

import server.CenterServer;
import server.CenterServerI;
import utilities.ManagerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;

public class ManagerClient {


    public static void main(String[] args) throws IOException, NotBoundException {

        ManagerLogger managerLogger;

        Registry registry = LocateRegistry.getRegistry(2965);
        Scanner scan = new Scanner(System.in);
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        CenterServer MTL = new CenterServer();
        String managerID = null;
        while (managerID == null) {
            System.out.println("Enter ManagerID: ");
            managerID = scan.next();
            if (!ManagerClientHelper.checkManagerID(managerID)) managerID = null;

        }
        CenterServerI centerServerI = (CenterServerI) registry.lookup(managerID.substring(0, 3));
        managerLogger=new ManagerLogger(managerID);
        while (true) {

            System.out.println("----------------------------Welcome to DCMS Please enter Command (1-5) to proceed ---------------------------------");
            System.out.println("1. Create Teacher Record");
            System.out.println("2 .Create Student Record");
            System.out.println("3. Edit Record");
            System.out.println("4. Get Counts");
            System.out.println("5. Log Out");
            int command = Integer.parseInt(scan.next());
            if (command == 1) {
                System.out.println("Please Enter First Name");
                String firstName = scan.next();

                System.out.println("Please Enter Last Name");
                String lastName = scan.next();


                String address = null;
                while (address == null || address.length() == 0) {
                    System.out.println("Please Enter Address");
                    address = obj.readLine();
                }
                System.out.println(address.length());


                String phoneNo = null;
                while (phoneNo == null) {

                    System.out.println("Please Enter Phone No");
                    phoneNo = scan.next();
                    if (ManagerClientHelper.checkPhone(phoneNo) == false) phoneNo = null;
                }
                System.out.println("Please Enter Specialization");
                String specialization = scan.next();

                System.out.println("Please Enter Location");
                String location = scan.next();


                System.out.println(centerServerI.createTRecord(firstName, lastName, address, phoneNo, specialization, location));
                //centerServerI.printRecords();

            } else if (command == 2) {
                System.out.println("Please Enter First Name");
                String firstName = scan.next();

                System.out.println("Please Enter Last Name");
                String lastName = scan.next();


                String line = null;
                while (line == null || line.length() == 0) {
                    System.out.println("Please Enter Course Registered");
                    line = obj.readLine();

                }
                String[] courses = line.split(" ");
                List<String> coursesRegistered = Arrays.asList(courses);


                String status = null;
                while (status == null) {
                    System.out.println("Please Enter status: active/inactive");
                    status = scan.next();
                    if (ManagerClientHelper.checkStatus(status) == false) status = null;
                }


                String statusDate = null;
                while (statusDate == null) {

                    System.out.println("Please Enter Status Date in DD-MM-YYYY");
                    statusDate = scan.next();
                    if (ManagerClientHelper.checkStatusDate(statusDate) == false) statusDate = null;
                }

                System.out.println(centerServerI.createSRecord(firstName, lastName, coursesRegistered, status, statusDate));

                //MTL.printRecords();

            } else if (command == 3) {
                System.out.println("Editing Record");
                System.out.println("Enter RecordID");
                String recordID = scan.next();

                System.out.println("Enter Field Name");
                String fieldName = scan.next();

                System.out.println("Enter Filed Value");
                String line = obj.readLine();
                String[] filedValue = line.split(" ");
                List<String> fieldData = Arrays.asList(filedValue);
                System.out.println(recordID + fieldName + line);
                System.out.println(centerServerI.editRecord(recordID, fieldName, fieldData));
            } else if (command == 4) {
                System.out.println("Getting Records");
//                System.out.println("MTL: 5");
//                System.out.println("LVL: 7");
//                System.out.println("DDO: 9");
                //centerServerI.printRecords();
            } else if (command == 5) {
                System.out.println("-----------------------Logging out------------------");
                break;
            } else {

                System.out.println("Please Enter Valid Command");
            }
        }
    }
}
