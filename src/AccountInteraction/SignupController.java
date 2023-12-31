package AccountInteraction;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import BankAccount.BankAccount;
import BankAccount.CheckAccount;
import BankAccount.SavingAccount;
import Customer.Customer;
import Enum.AccountType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML
    private TextField email;

    @FXML
    private TextField fullName;

    @FXML
    private TextField password;

    @FXML
    private TextField ssn;

    @FXML
    private TextField username;

    @FXML
    private CheckBox btnChecking;

    @FXML
    private CheckBox btnSaving;

    @FXML
    void onClickChecking(ActionEvent event) {

    }

    @FXML
    void onClickSaving(ActionEvent event) {

    }

        @FXML
    void onClickCreate(ActionEvent event) {
        // ArrayList to store created bank accounts
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        
        // Check if the username is unique
        if(isUserUnique(username.getText(),"UserInfo.txt")){
            // Create instances of CheckAccount and SavingAccount
             BankAccount checkAccount = new CheckAccount(AccountType.CHECKINGACCOUNT, new Customer());
             BankAccount savingAccount = new SavingAccount();

            // Logic for handling selected checkboxes
            if(btnChecking.isSelected() && btnSaving.isSelected()){
                savingAccount.setAccountType(AccountType.SAVINGACCOUNT);
                savingAccount.generateAccountNumber();
                bankAccounts.add(checkAccount);
                bankAccounts.add(savingAccount);
                signUp("UserInfo.txt",username.getText(),password.getText(),fullName.getText(),email.getText(),ssn.getText(),bankAccounts);
            }else if(btnChecking.isSelected()){
                bankAccounts.add(checkAccount);
                bankAccounts.add(savingAccount);
                signUp("UserInfo.txt",username.getText(),password.getText(),fullName.getText(),email.getText(),ssn.getText(),bankAccounts);
            }else if(btnSaving.isSelected()){
                checkAccount.setAccountType(null);
                checkAccount.setAccountNumber(null);
                savingAccount.setAccountType(AccountType.SAVINGACCOUNT);
                savingAccount.generateAccountNumber();
                bankAccounts.add(checkAccount);
                bankAccounts.add(savingAccount);
                signUp("UserInfo.txt",username.getText(),password.getText(),fullName.getText(),email.getText(),ssn.getText(),bankAccounts);
            }

            
            
        }else{
            // Display an alert if the username is not unique
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Username already TAKEN! Please choose another one.");
            alert.showAndWait();
        }

    }

        @FXML
    void onClickCancel(ActionEvent event) {
        // Redirect to the main page when the user clicks the "Cancel"
        Main.setRoot("Main",400,200);
    }
    // Check if a username is unique by reading from a file
    public static boolean isUserUnique(String username, String filename){
        boolean unique = true;
        try(Scanner input = new Scanner(new File(filename))){
        
            while (input.hasNext()) {
                String[] userInfo = input.nextLine().split(",");
                if(userInfo[0].equals(username)){
                    unique = false;
                }
            }
        }catch(IOException e) {
            System.err.println("Error writing data: " + e.getMessage());
        }    
        return unique;
    }
     // Write user information and bank account details to a file
    public static void signUp(String filename,String username, String password,String fullName, String email, String ssn,ArrayList<BankAccount> bankaccounts){
        try(PrintWriter writer = new PrintWriter(new FileWriter(filename, true))){
            writer.print(username + "," + password + "," + fullName + "," + email + "," +ssn);
            for(BankAccount account : bankaccounts){
                writer.print(","+account.getAccountType()+"," + account.getAccountNumber()
                +"," + account.getBalance()+","+account.getTerm());
            }
            writer.println();

            // Display a success message using an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! Your Account Was Successfully Created!");
            alert.showAndWait();
        }catch(IOException e){
            System.err.println("Error writing data: " + e.getMessage());
        }
    }
}
