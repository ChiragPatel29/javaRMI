package Client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerClientHelper {

    public static boolean checkIfZero(String str) {
        if (str.equals("0")) return true;
        return false;
    }


    public static boolean checkManagerID(String managerID) {
        String location = managerID.substring(0, 3);
        if (!(location.equals("MTL") || (location.equals("LVL") || location.equals("DDO")))) {
            System.out.println("Please Enter Valid Manager ID with proper prefix (MTL,LVL,DDO)");
            return false;
        }
        try {
            String number = managerID.substring(3);
            if (number.length() != 4) {
                System.out.println("Please Enter Valid ID (Eg. MTL1111)");
                return false;
            }
            int numID = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("Please Enter Valid ID");
            return false;
        }
        return true;
    }


    public static boolean checkRecordID(String managerID) {
        String intials = managerID.substring(0, 2);
        if (!(intials.equals("TR") || (intials.equals("SR")))) {
            System.out.println("Please Enter Enter Valid Format Record ID");
            return false;
        }
        try {
            String number = managerID.substring(2);
            if (number.length() != 5) {
                System.out.println("Please Enter Valid Format Record ID");
                return false;
            }
            int numID = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("Please Enter Valid ID");
            return false;
        }
        return true;
    }

    public static boolean checkIfSizeOne(String str) {
        String[] strBuffer = str.split(" ");
        if (strBuffer.length == 1) return true;
        return false;
    }


    public static boolean checkPhone(String phoneNo) {
        String northAmericaRegex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern patternNorthAmerica = Pattern.compile(northAmericaRegex);
        Matcher matcherNorthAmerica = patternNorthAmerica.matcher(phoneNo);

        String internationalRegex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern patternInternational = Pattern.compile(internationalRegex);
        Matcher matcherInternational = patternInternational.matcher(phoneNo);
        if (matcherNorthAmerica.matches() || matcherInternational.matches())
            return true;
        else {
            System.out.println("Please Enter a Valid Number");
            return false;
        }
    }


    public static boolean checkStatus(String status) {
        String statusToCheck = status.toLowerCase();
        if (statusToCheck.equals("active") || statusToCheck.equals("inactive"))
            return true;
        else {
            System.out.println("Please enter a valid status");
            return false;
        }
    }

    public static boolean checkStatusDate(String date) {
        try {
            LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("d-M-uuuu")
                            .withResolverStyle(ResolverStyle.STRICT));

        } catch (DateTimeParseException e) {
            System.out.println("Please enter a valid date");
            return false;
        }
        return true;
    }


    public static boolean checkLocation(String location) {
        location = location.toUpperCase();
        if (!(location.equals("MTL") || (location.equals("LVL") || location.equals("DDO")))) {
            System.out.println("Please Enter location MTL/LVL/DDO");
            return false;
        }
        return true;
    }


    public static String getSingleInput(String displayMessage) throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String strBuffer = null;
        while (strBuffer == null || strBuffer.length() == 0) {
            System.out.println(displayMessage);
            strBuffer = obj.readLine();
            if (checkIfZero(strBuffer)) break;
            if (!checkIfSizeOne(strBuffer)) strBuffer = null;

        }
        return strBuffer;
    }


    public static String getMultipleInput(String displayMessage) throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String strBuffer = null;
        while (strBuffer == null || strBuffer.length() == 0) {
            System.out.println(displayMessage);
            strBuffer = obj.readLine();
            if (checkIfZero(strBuffer)) break;
        }
        return strBuffer;
    }

    public static int getCommand() throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String commandStr = null;
        int command = 0;
        while (commandStr == null || commandStr.length() == 0) {
            System.out.println("Please Enter a numeric command");
            commandStr = obj.readLine();
            if (commandStr.length() == 1) {
                try {
                    command = Integer.parseInt(commandStr);
                } catch (NumberFormatException e) {
                    System.out.println("Please Enter a numeric command");
                    continue;
                }
                return command;
            }
        }
        obj.close();
        return 0;

    }


    public static String getPhoneNo() throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String phoneNo = null;
        while (phoneNo == null || phoneNo.length() == 0) {
            System.out.println("Please Enter Phone No");
            phoneNo = obj.readLine();
            if (checkIfZero(phoneNo)) break;
            if (!(checkPhone(phoneNo) && checkIfSizeOne(phoneNo))) phoneNo = null;


        }
        return phoneNo;
    }

    public static String getLocation() throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String location = null;
        while (location == null) {
            System.out.println("Please Enter Location");
            location = obj.readLine();
            if (checkIfZero(location)) break;
            if (!(checkIfSizeOne(location) && checkLocation(location))) location = null;
        }
        return location;
    }


    public static String getStatus() throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String status = null;
        while (status == null || status.length() == 0) {
            System.out.println("Please Enter status: active/inactive");
            status = obj.readLine();
            if (checkIfZero(status)) break;
            if (!(checkIfSizeOne(status) && checkStatus(status))) status = null;

        }
        return status;
    }


    public static String getStatusDate() throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String statusDate = null;
        while (statusDate == null || statusDate.length() == 0) {
            System.out.println("Please Enter Status Date in DD-MM-YYYY");
            statusDate = obj.readLine();
            if (checkIfZero(statusDate)) break;
            if (!(checkIfSizeOne(statusDate) && checkStatusDate(statusDate))) statusDate = null;

        }
        return statusDate;
    }


    public static String getRecordID() throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String recordID = null;
        while (recordID == null || recordID.length() == 0) {
            System.out.println("Enter RecordID");
            recordID = obj.readLine();
            if (checkIfZero(recordID)) break;
            if (!(checkIfSizeOne(recordID) && checkRecordID(recordID))) recordID = null;

        }
        return recordID;
    }

    public static boolean checkIfFieldPermisible(String recordID, String fieldToCheck) {
        if (recordID.substring(0, 2).equals("TR")) {
            if (fieldToCheck.equals("address") || fieldToCheck.equals("phone") || fieldToCheck.equals("phone"))
                return true;
        } else {
            if (fieldToCheck.equals("coursesRegistered") || fieldToCheck.equals("status") || fieldToCheck.equals("statusDate"))
                return true;
        }
        System.out.println("Please Enter a valid Field to Change");
        return false;
    }


    public static String getFieldName(String recordID) throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String fieldName = null;
        while (fieldName == null || fieldName.length() == 0) {
            System.out.println("Enter Field Name");
            fieldName = obj.readLine();
            if (checkIfZero(fieldName)) break;
            if (!(checkIfSizeOne(fieldName) && checkIfFieldPermisible(recordID, fieldName))) fieldName = null;

        }
        return fieldName;

    }


    public static String getFieldValue(String fieldToCheck) throws IOException {
        String fieldValue;
            if (fieldToCheck.equals("address"))
                    fieldValue=getMultipleInput("Please Enter New Address");
            else if(fieldToCheck.equals("phone"))
                    fieldValue=getPhoneNo();
            else if (fieldToCheck.equals("location"))
                    fieldValue=getLocation();


            else if (fieldToCheck.equals("coursesRegistered"))
                    fieldValue=getMultipleInput("Please Enter New Courses to be Registered  Separated by Space");
            else if (fieldToCheck.equals("status"))
                    fieldValue=getStatus();
            else
                    fieldValue=getStatusDate();

                return fieldValue;
        }
}
