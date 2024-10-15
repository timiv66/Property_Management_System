package Property;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Property_FX extends Application{
	//Objects representing users
	Tenant tenant = new Tenant();
	Landlord landlord = new Landlord();
	Apartment apartment = new Apartment();
	
	Font titleFont = new Font("Stencil",25);
	Font btnFont = new Font("Elephant",18);
	Font lblFont = new Font("Impact",25);
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		Pane p1 = new Pane();
		Scene t = new Scene(p1,400,280);
		t.setRoot(login(t));
		mainStage.setScene(t);
		mainStage.show();
		mainStage.setTitle("Property Management");
	}
	
	public Pane login(Scene t) {
		Label title = new Label("Property Management System");
		title.setFont(titleFont);
		title.setTranslateX(3);
		
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(400); 
		line.setStartY(30);
		line.setEndY(30);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		
		Label loginLbl = new Label("Login");
		loginLbl.setTranslateY(40);
		loginLbl.setTranslateX(170);
		loginLbl.setFont(btnFont);
		
		//Email field
		Label emailLbl = new Label("Email:");
		emailLbl.setTranslateX(85);
		emailLbl.setTranslateY(70);
		emailLbl.setFont(lblFont);
		
		TextField emailTxtF = new TextField();
		emailTxtF.setTranslateX(150);
		emailTxtF.setTranslateY(75);
		
		//Password field
		Label passLbl = new Label("Password:");
		passLbl.setTranslateX(85);
		passLbl.setTranslateY(110);
		passLbl.setFont(lblFont);
		
		TextField passTxtF = new TextField();
		passTxtF.setTranslateX(195);
		passTxtF.setTranslateY(115);
		
		
		//User choosing if they are a tenant or a landlord
		ToggleGroup group = new ToggleGroup();

		RadioButton tenantBtn = new RadioButton("Tenant");
		tenantBtn.setToggleGroup(group);
		tenantBtn.setTranslateX(120);
		tenantBtn.setTranslateY(150);
				
		RadioButton landlordBtn = new RadioButton("Landlord");
		landlordBtn.setToggleGroup(group);
		landlordBtn.setTranslateX(210);
		landlordBtn.setTranslateY(150);
		
		//Login button
		Button loginBtn = new Button("Login");
		loginBtn.setTranslateX(170);
		loginBtn.setTranslateY(180);
		
		//New Tenant button
		Button newTenantBtn = new Button("New Tenant");
		newTenantBtn.setTranslateX(100);
		newTenantBtn.setTranslateY(235);
		
		newTenantBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(newTenantAcc(t));
			}
		});
		
		//New Landlord button
		Button newLandlordBtn = new Button("New Landlord");
		newLandlordBtn.setTranslateX(210);
		newLandlordBtn.setTranslateY(235);
		
		newLandlordBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(newLandlordAcc(t));
			}
		});
		
		Pane loginPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		loginPane.setBackground(background);
		
		loginPane.getChildren().addAll(title,line,loginLbl,emailLbl,emailTxtF,passLbl,passTxtF,tenantBtn,landlordBtn,loginBtn,newTenantBtn,newLandlordBtn);
		return loginPane;
	}
	
	//Where new tenants can create a new tenant account
	public Pane newTenantAcc (Scene t) {
		t.getWindow().setWidth(290);
		
		Label titleLbl = new Label("New Tenant Account");
		titleLbl.setFont(titleFont);
		titleLbl.setTranslateX(3);
		
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(400); 
		line.setStartY(30);
		line.setEndY(30);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		
		Label nameLbl = new Label("Name: ");
		nameLbl.setTranslateX(3);
		nameLbl.setTranslateY(38);
		nameLbl.setFont(btnFont);
		
		TextField nameTxtF = new TextField();
		nameTxtF.setTranslateX(65);
		nameTxtF.setTranslateY(38);
		
		Label emailLbl = new Label("Email: ");
		emailLbl.setTranslateX(3);
		emailLbl.setTranslateY(71);
		emailLbl.setFont(btnFont);
		
		TextField emailTxtF = new TextField();
		emailTxtF.setTranslateX(70);
		emailTxtF.setTranslateY(71);
		
		Label passwordLbl = new Label("Password:");
		passwordLbl.setTranslateX(3);
		passwordLbl.setTranslateY(104);
		passwordLbl.setFont(btnFont);
		
		TextField passwordTxtF = new TextField();
		passwordTxtF.setTranslateX(100);
		passwordTxtF.setTranslateY(104);
		
		Label phoneLbl = new Label("Phone:");
		phoneLbl.setTranslateX(3);
		phoneLbl.setTranslateY(137);
		phoneLbl.setFont(btnFont);
		
		TextField phoneTxtF = new TextField();
		phoneTxtF.setTranslateX(70);
		phoneTxtF.setTranslateY(137);
		
		//adds new tenant user to database;
		Button createTenantAccBtn = new Button("Create Account");
		createTenantAccBtn.setTranslateX(90);
		createTenantAccBtn.setTranslateY(190);
		
		createTenantAccBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Inputed information from users
				String inputedName = nameTxtF.getText();
				String inputedEmail = emailTxtF.getText();
				String inputedPassword = passwordTxtF.getText();
				String inputedPhone = phoneTxtF.getText();
				
				//Checking in user inputed information is written correctly
				if(inputedName.matches("^[A-Z][a-z]+ [A-Z][a-z]+$") && inputedEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
						&& inputedPassword.matches("[\\w]{7,}"+"[!@#$%&*]{1}") && inputedPhone.matches("^(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$")) {
					tenant.insertTenantToDB(inputedName, inputedEmail, inputedPassword, inputedPhone);
					
					
				//Display error message if format is wrong	
				}else {
					
				}
			}
		});
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(250);
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(login(t));
			}
		});
		
		
		Pane newTenantAccPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		newTenantAccPane.setBackground(background);
		
		newTenantAccPane.getChildren().addAll(titleLbl,line,nameLbl,nameTxtF,emailLbl,emailTxtF,passwordLbl,passwordTxtF,phoneLbl,phoneTxtF,createTenantAccBtn,backBtn);
		return newTenantAccPane;
	}
	
	//Where new landlords can create a new landlord account
	public Pane newLandlordAcc(Scene t) {
		
		Label titleLbl = new Label("New Landlord Account");
		titleLbl.setFont(titleFont);
		titleLbl.setTranslateX(3);
		
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(400); 
		line.setStartY(30);
		line.setEndY(30);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		
		Label nameLbl = new Label("Name: ");
		nameLbl.setTranslateX(3);
		nameLbl.setTranslateY(38);
		nameLbl.setFont(btnFont);
		
		TextField nameTxtF = new TextField();
		nameTxtF.setTranslateX(65);
		nameTxtF.setTranslateY(38);
		
		Label emailLbl = new Label("Email: ");
		emailLbl.setTranslateX(3);
		emailLbl.setTranslateY(71);
		emailLbl.setFont(btnFont);
		
		TextField emailTxtF = new TextField();
		emailTxtF.setTranslateX(70);
		emailTxtF.setTranslateY(71);
		
		Label passwordLbl = new Label("Password:");
		passwordLbl.setTranslateX(3);
		passwordLbl.setTranslateY(104);
		passwordLbl.setFont(btnFont);
		
		TextField passwordTxtF = new TextField();
		passwordTxtF.setTranslateX(100);
		passwordTxtF.setTranslateY(104);
		
		Label phoneLbl = new Label("Phone:");
		phoneLbl.setTranslateX(3);
		phoneLbl.setTranslateY(137);
		phoneLbl.setFont(btnFont);
		
		TextField phoneTxtF = new TextField();
		phoneTxtF.setTranslateX(70);
		phoneTxtF.setTranslateY(137);
		
		Button createLandlordAccBtn = new Button("Create Account");
		createLandlordAccBtn.setTranslateX(90);
		createLandlordAccBtn.setTranslateY(190);
		
		createLandlordAccBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Inputed information from users
				String inputedName = nameTxtF.getText();
				String inputedEmail = emailTxtF.getText();
				String inputedPassword = passwordTxtF.getText();
				String inputedPhone = phoneTxtF.getText();
				
				//Checking in user inputed information is written correctly
				if(inputedName.matches("^[A-Z][a-z]+ [A-Z][a-z]+$") && inputedEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
						&& inputedPassword.matches("[\\w]{7,}"+"[!@#$%&*]{1}") && inputedPhone.matches("^(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$")) {
					landlord.insertLandlordToDB(inputedName, inputedEmail, inputedPassword, inputedPhone);
					
					landlord.setName(inputedName);
					landlord.setEmail(inputedEmail);
					landlord.setPassword(inputedPassword);
					landlord.setPhone(inputedPhone);
					
					t.setRoot(landlordRegApart(t));
				//Display error message if format is wrong	
				}else {
					
				}
			}
		});
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(250);
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(login(t));
			}
		});
		
		Pane newLandlordAccPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.DARKCYAN,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		newLandlordAccPane.setBackground(background);
		
		newLandlordAccPane.getChildren().addAll(titleLbl,line,nameLbl,nameTxtF,emailLbl,emailTxtF,passwordLbl,passwordTxtF,phoneLbl,phoneTxtF,createLandlordAccBtn,backBtn);
		
		return newLandlordAccPane;
	}//register
	
	public Pane landlordRegApart(Scene t) {
		Label titleLbl = new Label("Register Apartment Complex");
		titleLbl.setFont(titleFont);
		titleLbl.setTranslateX(3);
		
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(400); 
		line.setStartY(30);
		line.setEndY(30);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		
		Label apartNameLbl = new Label("Apartment Name: ");
		apartNameLbl.setTranslateX(3);
		apartNameLbl.setTranslateY(38);
		apartNameLbl.setFont(btnFont);
		
		TextField apartNameTxtF = new TextField();
		apartNameTxtF.setTranslateX(170);
		apartNameTxtF.setTranslateY(38);
		
		Label locationLbl = new Label("Location: ");
		locationLbl.setTranslateX(3);
		locationLbl.setTranslateY(71);
		locationLbl.setFont(btnFont);
		
		TextField locationTxtF = new TextField();
		locationTxtF.setTranslateX(90);
		locationTxtF.setTranslateY(71);
		
		Label maxAmtLbl = new Label("Max amount of tenants:");
		maxAmtLbl.setTranslateX(3);
		maxAmtLbl.setTranslateY(104);
		maxAmtLbl.setFont(btnFont);
		
		TextField maxAmtTxtF = new TextField();
		maxAmtTxtF.setTranslateX(215);
		maxAmtTxtF.setTranslateY(104);
		
		Label apartTypeLbl = new Label("Apartment Type:");
		apartTypeLbl.setTranslateX(3);
		apartTypeLbl.setTranslateY(137);
		apartTypeLbl.setFont(btnFont);
		
		ChoiceBox<String> apartTypeCB = new ChoiceBox<String>();//choice box for status
		apartTypeCB.getItems().add("High Rise");
		apartTypeCB.getItems().add("Garden");
		apartTypeCB.getItems().add("Walk-Up");
		apartTypeCB.getItems().add("Luxury");
		apartTypeCB.getItems().add("Micro");
		apartTypeCB.setTranslateX(160);
		apartTypeCB.setTranslateY(137);
		
		Label starsLbl = new Label("Stars:");
		starsLbl.setTranslateX(3);
		starsLbl.setTranslateY(170);
		starsLbl.setFont(btnFont);
		
		ChoiceBox<Integer> starCB = new ChoiceBox<Integer>();//choice box for status
		starCB.getItems().add(1);
		starCB.getItems().add(2);
		starCB.getItems().add(3);
		starCB.getItems().add(4);
		starCB.getItems().add(5);
		starCB.setTranslateX(60);
		starCB.setTranslateY(170);
		
		Button regApartBtn = new Button("Register Apartment");
		regApartBtn.setTranslateX(130);
		regApartBtn.setTranslateY(220);
		
		regApartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Inputed apartment information by user
				String inputedApartName = apartNameTxtF.getText();
				String inputedLocation = locationTxtF.getText();
				int inputedMaxAmtOfTenants = Integer.parseInt(maxAmtTxtF.getText());
				String inputedApartType = apartTypeCB.getValue();
				int inputedStars = starCB.getValue();
				
				//Adding apartment to DB
				int landlordId = landlord.getLandlordIdFromDB();
				apartment.insertApartToDB(inputedApartName, inputedLocation, inputedMaxAmtOfTenants, inputedApartType, inputedStars, landlordId);
			}
		});
		
		Pane landlordRegApartPane = new Pane();
		
		landlordRegApartPane.getChildren().addAll(titleLbl,line,apartNameLbl,apartNameTxtF,locationLbl,locationTxtF,maxAmtLbl,maxAmtTxtF,apartTypeLbl,apartTypeCB,starsLbl,starCB,regApartBtn);
		return landlordRegApartPane;
	}
	
	

}
