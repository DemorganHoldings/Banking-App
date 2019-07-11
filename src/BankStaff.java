import java.util.Random;

public class BankStaff {

    //fields
    private String staffId;
    private String staffName;
    private String staffUsername;
    private String staffPassword;
    private int isManager;

    /**
     * Constructor for new Bank Staff
     * @param name Staff's Name
     * @param username Staff's Username
     * @param password Staff's password
     * @param manager If they are a manager or not
     */
    public BankStaff(String name, String username, String password, int manager) {
        this.staffName = name;
        this.staffUsername = username;
        this.staffPassword = password;
        this.isManager = manager;
        generateStaffId();
    }

    /**
     * Constructor for existing Bank Staff
     * @param id Staff's ID
     * @param name Staff's Name
     * @param username Staff's Username
     * @param password Staff's password
     * @param manager If they are a manager or not
     */
    public BankStaff(String id, String name, String username, String password, int manager) {
        this.staffId = id;
        this.staffName = name;
        this.staffUsername = username;
        this.staffPassword = password;
        this.isManager = manager;
    }

    //Getters and Setters
    public String getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffUsername() {
        return staffUsername;
    }

    public void setStaffUsername(String staffUsername) {
        this.staffUsername = staffUsername;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int manager) {
        isManager = manager;
    }

    /**
     * Generates a random Staff ID and assigns it
     */
    public void generateStaffId() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(999999) + 1;
        String randomNumberString = String.format("%06d", randomNumber);
        this.staffId = "SPS-" + randomNumberString;
    }
}
