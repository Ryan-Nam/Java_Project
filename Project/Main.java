
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);

    // Load drivers from file and return it as a list
    private static List<Driver> loadDrivers(String filename) throws Exception {
        // Load the drivers from file
        List<Driver> drivers = new ArrayList<>();
        Scanner inFile = new Scanner(new File(filename));

        while (inFile.hasNextLine()) {
            drivers.add(new Driver(inFile.nextLine()));
        }

        inFile.close();

        return drivers;
    }

    // Print out all the drivers in the console
    private static void displayDrivers(List<Driver> drivers) {
        System.out.print("Would you like to display the drivers sorted by demerit points in descending order? (Y/N) ");
        String sortDrivers = in.nextLine();

        if (sortDrivers.equalsIgnoreCase("y")) {
            for (int i = 0; i < drivers.size() - 1; i++) {
                for (int j = i + 1; j < drivers.size(); j++) {
                    if (drivers.get(i).getDemeritPoints() < drivers.get(j).getDemeritPoints()) {
                        Driver driver = drivers.get(i);
                        drivers.set(i, drivers.get(j));
                        drivers.set(j, driver);
                    }
                }
            }
        }

        System.out.printf("%-20s%-15s%-15s%-30s%-15s%-15s\n",
                "License Number",
                "First Name",
                "Last Name",
                "Suburb",
                "Demerit Points",
                "Licence Status");

        System.out.printf("%-20s%-15s%-15s%-30s%-15s%-15s\n",
                "--------------",
                "----------",
                "---------",
                "------",
                "--------------",
                "--------------");

        for (Driver driver : drivers) {
            System.out.printf("%-20s%-15s%-15s%-30s%-15s%-15s\n",
                    driver.getLicenseNumber(),
                    driver.getFirstName(),
                    driver.getLastName(),
                    driver.getSuburb(),
                    driver.getDemeritPoints(),
                    driver.getLicenseStatus());
        }
    }

    // Find the driver that has the given license number
    private static Driver findDriver(List<Driver> drivers, String licenseNumber) {
        for (Driver driver : drivers) {
            if (driver.getLicenseNumber().equals(licenseNumber)) {
                return driver;
            }
        }

        return null;
    }

    // Import the infringement file and apply the demerits to the 
    // affected drivers
    private static void importInfringementFile(List<Driver> drivers) throws Exception {
        System.out.print("Enter infringement filename: ");
        String filename = in.nextLine();

        Scanner inFile;

        try {
            inFile = new Scanner(new File(filename));
        } catch (Exception e) {
            System.out.println("Error: File not found.");
            return;
        }

        // Parse the file and apply penalty to drivers
        int numLicensesSuspended = 0;
        int numPenaltiesApplied = 0;
        float totalFinesCollected = 0;

        while (inFile.hasNextLine()) {
            String line = inFile.nextLine();
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(",");

            if (!scanner.hasNext()) {
                continue;
            }

            // Skip the infringement ID and the date, extract only license number and speed
            scanner.next();
            String licenseNumber = scanner.next();
            scanner.next();
            int excessSpeed = scanner.nextInt();

            // Find the driver to be penalized
            Driver driver = findDriver(drivers, licenseNumber);

            if (driver != null) {
                numPenaltiesApplied++;
                totalFinesCollected += SpeedingPenalty.getFine(excessSpeed);

                driver.setDemeritPoints(driver.getDemeritPoints() + SpeedingPenalty.getDemeritPoints(excessSpeed));

                // Driver gets suspended if the speed goes an invalid limit or if
                // the demerit points goes above 13
                if (SpeedingPenalty.causesLicenseSuspension(excessSpeed)
                        || driver.getDemeritPoints() > 13) {
                    numLicensesSuspended++;
                    driver.setLicenseStatus(Driver.SUSPENDED_LICENSE_STATUS);
                }
            }
        }

        inFile.close();

        // Print out statistical results
        System.out.println("Number of Penalties Applied : " + numPenaltiesApplied);
        System.out.println("Number of Licenses Suspended: " + numLicensesSuspended);
        System.out.println("Total Fines Collected       : $" + String.format("%.2f", totalFinesCollected));
    }

    // Print the drivers that are suspended
    private static void generateSuspensionReport(List<Driver> drivers) {
        System.out.printf("%-20s%-15s%-15s%-30s%-30s%-15s%-15s\n",
                "License Number",
                "First Name",
                "Last Name",
                "Address",
                "Suburb",
                "Demerit Points",
                "Licence Status");

        System.out.printf("%-20s%-15s%-15s%-30s%-30s%-15s%-15s\n",
                "--------------",
                "----------",
                "---------",
                "-------",
                "------",
                "--------------",
                "--------------");

        for (Driver driver : drivers) {
            if (driver.getLicenseStatus().equals(Driver.SUSPENDED_LICENSE_STATUS)) {
                System.out.printf("%-20s%-15s%-15s%-30s%-30s%-15s%-15s\n",
                        driver.getLicenseNumber(),
                        driver.getFirstName(),
                        driver.getLastName(),
                        driver.getAddress(),
                        driver.getSuburb(),
                        driver.getDemeritPoints(),
                        driver.getLicenseStatus());
            }
        }
    }

    // Write the results back to file
    private static void saveDriverRecords(List<Driver> drivers, String filename) throws Exception {
        PrintWriter outFile = new PrintWriter(new File(filename));

        for (Driver driver : drivers) {
            driver.write(outFile);
        }

        outFile.close();

        System.out.println("Driver records written to file.");
    }

    // Entry point of the program
    public static void main(String[] args) throws Exception {
        List<Driver> drivers = loadDrivers("Driver.txt");

        // Display the options to the user
        while (true) {
            System.out.println("Menu");
            System.out.println("1. Display Drivers");
            System.out.println("2. Import Infringement File");
            System.out.println("3. Generate Suspension Report");
            System.out.println("4. Save Driver Records");
            System.out.println("5. Exit Program");

            // Let the user decide what to do
            System.out.print("Option: ");
            String option = in.nextLine();

            if (option.equals("1")) {
                displayDrivers(drivers);
            } else if (option.equals("2")) {
                importInfringementFile(drivers);
            } else if (option.equals("3")) {
                generateSuspensionReport(drivers);
            } else if (option.equals("4")) {
                saveDriverRecords(drivers, "Driver.txt");
            } else if (option.equals("5")) {
                break;
            }
        }
    }
}
