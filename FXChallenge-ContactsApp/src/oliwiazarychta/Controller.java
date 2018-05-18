package oliwiazarychta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> tableView;

    @FXML
    private TableColumn<Contact, String> firstNameCol;

    @FXML
    private TableColumn<Contact, String> lastNameCol;

    @FXML
    private TableColumn<Contact, String> phoneNumCol;

    @FXML
    private TableColumn<Contact, String> notesCol;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));

        tableView.setItems(Main.contactData.getContacts());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().selectFirst();

    }
    private void showNewOrEditDialog(boolean isEditDialog, Contact editedContact){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adddialog.fxml"));

        try{
            dialog.getDialogPane().setContent(loader.load());
        } catch(IOException e){
            System.out.println("Couldn't load dialog");
            e.printStackTrace();
            return;
        }

        AddOrEditDialogController addOrEditDialogController = loader.getController();
        if(isEditDialog){
            if(editedContact==null){
                System.out.println("No Contact selected");
                return;
            }
            dialog.setTitle("Edit Contact!");
            addOrEditDialogController.setInitialFieldsValues(editedContact);
        } else {
            dialog.setTitle("Add new Contact!");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)){
            Contact c = null;
            c = addOrEditDialogController.processResults(isEditDialog, editedContact);
            tableView.getSelectionModel().select(c);
        }
    }

    public void showNewItemDialog(){
        showNewOrEditDialog(false, null);
    }

    public void showEditItemDialog(){
        showNewOrEditDialog(true, tableView.getSelectionModel().getSelectedItem());
    }

    public void showDeleteDialog(){
        Contact c = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: " + c.getFirstName() +", "+ c.getLastName() +".");
        alert.setContentText("Are you sure you want to delete that contact?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Main.contactData.deleteContact(c);
            tableView.getSelectionModel().selectFirst();
        }
    }

}
