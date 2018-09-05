package HouseIt.security;

public class ResetPasswordHelper {

    private String oldPassword;
    private String newPassword;
    private boolean passwordsAreDifferent;

    public ResetPasswordHelper() {

    }

    public ResetPasswordHelper(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean checkIfPasswordsAreDifferent() {
        return !this.oldPassword.equals(this.newPassword);
    }

}
