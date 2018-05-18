package oliwiazarychta;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AddOrEditDialogController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumField;

    @FXML
    private TextField notesField;

    public void setInitialFieldsValues(Contact c){
        firstNameField.setText(c.getFirstName());
        lastNameField.setText(c.getLastName());
        phoneNumField.setText(c.getPhoneNumber());
        notesField.setText(c.getNotes());
    }

    public Contact processResults(boolean isEditProcess, Contact contact){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNum = phoneNumField.getText().trim();
        String notes = notesField.getText().trim();

        if(isEditProcess){
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setPhoneNumber(phoneNum);
            contact.setNotes(notes);
            return contact;
        }
        Contact c = new Contact(firstName, lastName, phoneNum, notes);
        Main.contactData.getContacts().add(c);
        return c;
    }
}
