
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {

    public static final String VALID_LICENSE_STATUS = "Valid";
    public static final String SUSPENDED_LICENSE_STATUS = "Suspended";

    private String licenseNumber;
    private String licenseClass;
    private String firstName;
    private String lastName;
    private String address;
    private String suburb;
    private int demeritPoints;
    private String licenseStatus;

    // Create a driver from a string for parsing
    public Driver(String data) {
        Scanner scanner = new Scanner(data);
        scanner.useDelimiter(",");

        licenseNumber = scanner.next().trim();
        licenseClass = scanner.next().trim();
        firstName = scanner.next().trim();
        lastName = scanner.next().trim();
        address = scanner.next().trim() + "," + scanner.next();
        suburb = scanner.next().trim();
        demeritPoints = scanner.nextInt();
        licenseStatus = scanner.next().trim();
    }

    // Create a new driver
    public Driver(String licenseNumber, String licenseClass, String firstName, String lastName, String address, String suburb, int demeritPoints, String licenseStatus) {
        this.licenseNumber = licenseNumber;
        this.licenseClass = licenseClass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.suburb = suburb;
        this.demeritPoints = demeritPoints;
        this.licenseStatus = licenseStatus;
    }

    // Set the license status whether it is valid or invalid
    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    // Initialize the demerit points
    public void setDemeritPoints(int demeritPoints) {
        this.demeritPoints = demeritPoints;
    }

    // Return the license number
    public String getLicenseNumber() {
        return licenseNumber;
    }

    // Return the license class
    public String getLicenseClass() {
        return licenseClass;
    }

    // Return the first name
    public String getFirstName() {
        return firstName;
    }

    // Return the last name
    public String getLastName() {
        return lastName;
    }

    // Return the address
    public String getAddress() {
        return address;
    }

    // Return the suburb
    public String getSuburb() {
        return suburb;
    }

    // Return the demerit points
    public int getDemeritPoints() {
        return demeritPoints;
    }

    // Return the license status
    public String getLicenseStatus() {
        return licenseStatus;
    }

    // Write driver details 
    public void write(PrintWriter outFile) {
        outFile.println(licenseNumber + ","
                + licenseClass + ","
                + firstName + ","
                + lastName + ","
                + address + ","
                + suburb + ","
                + demeritPoints + ","
                + licenseStatus);
    }
}
