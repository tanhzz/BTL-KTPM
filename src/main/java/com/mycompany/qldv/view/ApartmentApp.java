package com.mycompany.qldv.view;

import com.mycompany.qldv.controller.ApartmentController;
import com.mycompany.qldv.controller.UserController;
import com.mycompany.qldv.model.Apartment;
import com.mycompany.qldv.model.ServiceFee;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ApartmentApp extends Application {
    private ApartmentController apartmentController = new ApartmentController();
    private UserController userController = new UserController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Đăng nhập");

        // Giao diện đăng nhập
        TextField usernameField = new TextField();
        usernameField.setPromptText("Tên người dùng");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mật khẩu");
        Button loginButton = new Button("Đăng nhập");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (userController.login(username, password)) {
                showMainApp(primaryStage);
            } else {
                showAlert("Tên người dùng hoặc mật khẩu không đúng!", Alert.AlertType.ERROR);
            }
        });

        GridPane loginLayout = new GridPane();
        loginLayout.setVgap(10);
        loginLayout.setHgap(10);
        loginLayout.add(new Label("Tên người dùng:"), 0, 0);
        loginLayout.add(usernameField, 1, 0);
        loginLayout.add(new Label("Mật khẩu:"), 0, 1);
        loginLayout.add(passwordField, 1, 1);
        loginLayout.add(loginButton, 1, 2);

        Scene loginScene = new Scene(loginLayout, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showMainApp(Stage primaryStage) {
        primaryStage.setTitle("Quản lý thu phí dịch vụ căn hộ");

        // Thêm căn hộ
        TextField nameField = new TextField();
        nameField.setPromptText("Tên căn hộ");
        TextField addressField = new TextField();
        addressField.setPromptText("Địa chỉ căn hộ");
        Button addApartmentButton = new Button("Thêm căn hộ");
        addApartmentButton.setOnAction(e -> {
            int id = apartmentController.getApartments().size() + 1;
            Apartment apartment = new Apartment(id, nameField.getText(), addressField.getText());
            apartmentController.addApartment(apartment);
            nameField.clear();
            addressField.clear();
            showAlert("Căn hộ đã được thêm!", Alert.AlertType.INFORMATION);
        });

        // Thêm phí dịch vụ
        TextField serviceTypeField = new TextField();
        serviceTypeField.setPromptText("Loại phí dịch vụ");
        TextField amountField = new TextField();
        amountField.setPromptText("Số tiền");
        Button addServiceFeeButton = new Button("Thêm phí dịch vụ");
        addServiceFeeButton.setOnAction(e -> {
            int id = apartmentController.getServiceFees().size() + 1;
            double amount = Double.parseDouble(amountField.getText());
            ServiceFee serviceFee = new ServiceFee(id, serviceTypeField.getText(), amount);
            apartmentController.addServiceFee(serviceFee);
            serviceTypeField.clear();
            amountField.clear();
            showAlert("Phí dịch vụ đã được thêm!", Alert.AlertType.INFORMATION);
        });

        // Tìm kiếm
        TextField searchField = new TextField();
        searchField.setPromptText("Tìm kiếm căn hộ");
        Button searchButton = new Button("Tìm kiếm");
        searchButton.setOnAction(e -> {
            String keyword = searchField.getText();
            List<Apartment> results = apartmentController.searchApartments(keyword);
            showAlert("Tìm thấy " + results.size() + " kết quả.", Alert.AlertType.INFORMATION);
            searchField.clear();
        });

        // Thống kê
        Button totalFeesButton = new Button("Tổng phí dịch vụ");
        totalFeesButton.setOnAction(e -> {
            double totalFees = apartmentController.getTotalFees();
            showAlert("Tổng phí dịch vụ: " + String.format("%,.2f", totalFees), Alert.AlertType.INFORMATION);
        });

        // Giao diện chính
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.add(new Label("Tên căn hộ:"), 0, 0);
        layout.add(nameField, 1, 0);
        layout.add(new Label("Địa chỉ căn hộ:"), 0, 1);
        layout.add(addressField, 1, 1);
        layout.add(addApartmentButton, 1, 2);

        layout.add(new Label("Loại phí dịch vụ:"), 0, 3);
        layout.add(serviceTypeField, 1, 3);
        layout.add(new Label("Số tiền:"), 0, 4);
        layout.add(amountField, 1, 4);
        layout.add(addServiceFeeButton, 1, 5);

        layout.add(new Label("Tìm kiếm:"), 0, 6);
        layout.add(searchField, 1, 6);
        layout.add(searchButton, 2, 6);
        layout.add(totalFeesButton, 1, 7);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
