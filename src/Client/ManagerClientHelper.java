package Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
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
            System.out.println("Please Enter Valid Manager ID");
            return false;
        }
        try {
            String number = managerID.substring(3);
            if (number.length() != 4) {
                System.out.println("Please Enter Valid ID");
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
        location=location.toUpperCase();
        if (!(location.equals("MTL") || (location.equals("LVL") || location.equals("DDO")))) {
            System.out.println("Please Enter location MTL/LVL/DDO");
            return false;
        }
        return true;
    }

}