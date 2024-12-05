package Property;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Property_FX extends Application{
	//Objects representing users
	Tenant tenant = new Tenant();
	Landlord landlord = new Landlord();
	Apartment apartment = new Apartment();
	Lease lease = new Lease();
	Requests requests = new Requests();
	
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
		
		Text errorMsg = new Text();
		errorMsg.setFill(Color.RED);
		errorMsg.setX(60);
		errorMsg.setY(230);
		errorMsg.setVisible(false);
		
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

		RadioButton tenantRBtn = new RadioButton("Tenant");
		tenantRBtn.setToggleGroup(group);
		tenantRBtn.setTranslateX(120);
		tenantRBtn.setTranslateY(150);
				
		RadioButton landlordRBtn = new RadioButton("Landlord");
		landlordRBtn.setToggleGroup(group);
		landlordRBtn.setTranslateX(210);
		landlordRBtn.setTranslateY(150);
		
		//Login button
		Button loginBtn = new Button("Login");
		loginBtn.setTranslateX(170);
		loginBtn.setTranslateY(180);
		
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String inputedEmail = emailTxtF.getText();
				String inputedPassword = passTxtF.getText();
				
				try {
					//When a tenant logs in
					if(tenantRBtn.isSelected()) { 
						if(tenant.authenticateTenantUser(inputedEmail, inputedPassword) == true) { //When login information for tenant is correct
							//Adding existing tenant info to tenant object
							tenant.setEmail(inputedEmail);
							tenant.setPassword(inputedPassword);
							
							String name = tenant.getTenantNameFromDB();
							tenant.setName(name);
							
							String phone = tenant.getTenantPhoneFromDB();
							tenant.setPhone(phone);
							
							tenant.updateNumOfTenantsForLandandlord(lease, tenant);//Updates amount of tenants a landlord has when a tenant user logs in
							
							//Takes tenant user to tenant home page
							t.setRoot(tenantUI(t));
							
						}else if(tenant.authenticateTenantUser(inputedEmail, inputedPassword) == false){ //When login information for tenant is incorrect
							errorMsg.setText("Incorrect email or password. Please try again");
							errorMsg.setVisible(true);
						}
					}
					//When a landlord logs in
					else if(landlordRBtn.isSelected()) {
						if(landlord.authenticateLandlordUser(inputedEmail, inputedPassword) == true) { //When login information for landlord is correct
							//Adding existing landlord info to landlord object
							landlord.setEmail(inputedEmail);
							landlord.setPassword(inputedPassword);
							
							String name = landlord.getLandlordNameFromDB();
							landlord.setName(name);
							
							String phone = landlord.getLandlordPhoneFromDB();
							landlord.setPhone(phone);
							
							//Updating the number of apartments and tenants a landlord has
							landlord.updateNumofApartments();
							landlord.updateNumOfTenants();
							
							//Takes landlord user to landlord home page
							t.setRoot(landlordUI(t));
						}
						else if(landlord.authenticateLandlordUser(inputedEmail, inputedPassword) == false){//When login information for landlord is incorrect
							errorMsg.setText("Incorrect email or password. Please try again");
							errorMsg.setVisible(true);
						}
					}
					//What happens when neither button is selected
					else if(!landlordRBtn.isSelected() && !landlordRBtn.isSelected()) {
						errorMsg.setText("Please select tenant or landlord button");
						errorMsg.setVisible(true);
					}
				}catch (Exception e) {
					errorMsg.setText("Login Failed. Please check login information and try again");
					errorMsg.setVisible(true);
					e.printStackTrace();
				}
			}
		});
		
		//New Tenant button
		Button newTenantBtn = new Button("New Tenant");
		newTenantBtn.setTranslateX(100);
		newTenantBtn.setTranslateY(250);
		
		newTenantBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(newTenantAcc(t));//Takes user to tenant account creation page
			}
		});
		
		//New Landlord button
		Button newLandlordBtn = new Button("New Landlord");
		newLandlordBtn.setTranslateX(210);
		newLandlordBtn.setTranslateY(250);
		
		newLandlordBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(newLandlordAcc(t));//Takes user to landlord account creation page
			}
		});
		
		Pane loginPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		loginPane.setBackground(background);
		
		loginPane.getChildren().addAll(title,line,errorMsg,loginLbl,emailLbl,emailTxtF,passLbl,passTxtF,tenantRBtn,landlordRBtn,loginBtn,newTenantBtn,newLandlordBtn);
		return loginPane;
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
		
		Text errorMsg = new Text("");
		errorMsg.setVisible(false);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(240);
		
		//Name field
		Label nameLbl = new Label("Name: ");
		nameLbl.setTranslateX(3);
		nameLbl.setTranslateY(38);
		nameLbl.setFont(btnFont);
		
		TextField nameTxtF = new TextField();
		nameTxtF.setTranslateX(65);
		nameTxtF.setTranslateY(38);
		
		//Email field
		Label emailLbl = new Label("Email: ");
		emailLbl.setTranslateX(3);
		emailLbl.setTranslateY(71);
		emailLbl.setFont(btnFont);
		
		TextField emailTxtF = new TextField();
		emailTxtF.setTranslateX(70);
		emailTxtF.setTranslateY(71);
		
		//Password field
		Label passwordLbl = new Label("Password:");
		passwordLbl.setTranslateX(3);
		passwordLbl.setTranslateY(104);
		passwordLbl.setFont(btnFont);
		
		TextField passwordTxtF = new TextField();
		passwordTxtF.setTranslateX(100);
		passwordTxtF.setTranslateY(104);
		
		//Phone field
		Label phoneLbl = new Label("Phone:");
		phoneLbl.setTranslateX(3);
		phoneLbl.setTranslateY(137);
		phoneLbl.setFont(btnFont);
		
		TextField phoneTxtF = new TextField();
		phoneTxtF.setTranslateX(70);
		phoneTxtF.setTranslateY(137);
		
		
		//Button that will create new landlord account
		Button createLandlordAccBtn = new Button("Create Account");
		createLandlordAccBtn.setTranslateX(90);
		createLandlordAccBtn.setTranslateY(190);
		
		createLandlordAccBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				if(nameTxtF.getText().isEmpty() || emailTxtF.getText().isEmpty() || passwordTxtF.getText().isEmpty() || phoneTxtF.getText().isEmpty()) {
					errorMsg.setVisible(true);
					errorMsg.setText("Please input all information");
				}else {
					String inputedName = nameTxtF.getText();
					String inputedEmail = emailTxtF.getText();
					String inputedPassword = passwordTxtF.getText();
					String inputedPhone = phoneTxtF.getText();
					
					//Checking in user inputed information is written correctly
					if(inputedName.matches("^[A-Z][a-z]+ [A-Z][a-z]+$") && inputedEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
							&& inputedPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{7,}$") && inputedPhone.matches("^(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$")) {
						landlord.insertLandlordToDB(inputedName, inputedEmail, inputedPassword, inputedPhone); //Adds new landlord user to database
						
						//Setting landlord object values to inputed information
						landlord.setName(inputedName);
						landlord.setEmail(inputedEmail);
						landlord.setPassword(inputedPassword);
						landlord.setPhone(inputedPhone);
						t.setRoot(landlordRegApart(t));
					//Display error message if format is wrong	
					}else {
						errorMsg.setVisible(true);
						errorMsg.setText("Please make sure that all information is correctly formatted");
					}
				}
			}
		});
		
		//Back button
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(250);
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(login(t));//Takes user back to login page 
			}
		});
		
		Pane newLandlordAccPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		newLandlordAccPane.setBackground(background);
		
		newLandlordAccPane.getChildren().addAll(titleLbl,line,nameLbl,nameTxtF,emailLbl,emailTxtF,passwordLbl,passwordTxtF,phoneLbl,phoneTxtF,createLandlordAccBtn,backBtn,errorMsg);
		
		return newLandlordAccPane;
	}//register
	
	public Pane landlordRegApart(Scene t){
		t.getWindow().setHeight(320);
		
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
		
		//Apartment name field
		Label apartNameLbl = new Label("Apartment Name: ");
		apartNameLbl.setTranslateX(3);
		apartNameLbl.setTranslateY(38);
		apartNameLbl.setFont(btnFont);
		
		TextField apartNameTxtF = new TextField();
		apartNameTxtF.setTranslateX(170);
		apartNameTxtF.setTranslateY(38);
		
		//Apartment location field
		Label locationLbl = new Label("Location: ");
		locationLbl.setTranslateX(3);
		locationLbl.setTranslateY(71);
		locationLbl.setFont(btnFont);
		
		TextField locationTxtF = new TextField();
		locationTxtF.setTranslateX(90);
		locationTxtF.setTranslateY(71);
		
		//Max amounts of tenants field
		Label maxAmtLbl = new Label("Max amount of tenants:");
		maxAmtLbl.setTranslateX(3);
		maxAmtLbl.setTranslateY(104);
		maxAmtLbl.setFont(btnFont);
		
		TextField maxAmtTxtF = new TextField();
		maxAmtTxtF.setTranslateX(215);
		maxAmtTxtF.setTranslateY(104);
		
		//Apartment type choice box
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
		
		//Number of Stars choice box
		Label starsLbl = new Label("Stars:");
		starsLbl.setTranslateX(3);
		starsLbl.setTranslateY(170);
		starsLbl.setFont(btnFont);
		
		Text errorMsg =new Text();
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(210);
		errorMsg.setVisible(false);
		
		ChoiceBox<Integer> starCB = new ChoiceBox<Integer>();//choice box for status
		starCB.getItems().add(1);
		starCB.getItems().add(2);
		starCB.getItems().add(3);
		starCB.getItems().add(4);
		starCB.getItems().add(5);
		starCB.setTranslateX(60);
		starCB.setTranslateY(170);
		
		//Button that will add new apartments to database
		Button regApartBtn = new Button("Register Apartment");
		regApartBtn.setTranslateX(130);
		regApartBtn.setTranslateY(220);
		
		regApartBtn.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent arg0)  {
				//Inputed apartment information by user
				String inputedApartName = apartNameTxtF.getText();
				
				if(locationTxtF.getText() == null || maxAmtTxtF.getText() == null || apartTypeCB.getValue() == null || starCB.getValue() == null) {
					errorMsg.setVisible(true);
					errorMsg.setText("Please answer every question");
				}else {
					String inputedLocation = locationTxtF.getText();
					int inputedMaxAmtOfTenants = Integer.parseInt(maxAmtTxtF.getText());
					String inputedApartType = apartTypeCB.getValue();
					int inputedStars = starCB.getValue();
					
					//Adding apartment to DB
					int landlordId = landlord.getLandlordIdFromDB();
					
					if(apartment.insertApartToDB(inputedApartName, inputedLocation, inputedMaxAmtOfTenants, inputedApartType, inputedStars, landlordId) == 1) {
						apartment.setName(inputedApartName);
						landlord.updateNumofApartments(); //Updating number of apartments a landlord has
						apartNameTxtF.setText(null);
						locationTxtF.setText(null);
						maxAmtTxtF.setText(null);
						apartTypeCB.setValue(null);
						starCB.setValue(null);
						errorMsg.setVisible(true);
						errorMsg.setText("Apartment has been added");
					}else {
						errorMsg.setVisible(true);
						errorMsg.setText("Apartment could not be added. Please try to input again");
					}
				}
				
			}
		});
		
		//Back button
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(250);
				
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(landlordUI(t)); //Takes user back to login page
			}
		});
		
		Pane landlordRegApartPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		landlordRegApartPane.setBackground(background);
		
		landlordRegApartPane.getChildren().addAll(titleLbl,line,apartNameLbl,apartNameTxtF,locationLbl,locationTxtF,maxAmtLbl,maxAmtTxtF,apartTypeLbl,apartTypeCB,starsLbl,starCB,regApartBtn,backBtn,errorMsg);
		return landlordRegApartPane;
	}
	
	//Home page for landlord users
	public Pane landlordUI(Scene t) {
		t.getWindow().setWidth(410);
		t.getWindow().setHeight(390);
			
		Label titleLbl = new Label("Landlord Home Page");
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
			
		//Search database
		Button searchBtn = new Button("Search");
		searchBtn.setTranslateX(115);
		searchBtn.setTranslateY(40);
		searchBtn.setPrefHeight(50);
		searchBtn.setPrefWidth(170);
		searchBtn.setFont(btnFont);
		
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(landlordSearch(t));
			}
		});
			
		//Existing landlords can add apartments to database
		Button addApartBtn = new Button("Add Apartment");
		addApartBtn.setTranslateX(115);
		addApartBtn.setTranslateY(100);
		addApartBtn.setPrefHeight(50);
		addApartBtn.setPrefWidth(170);
		addApartBtn.setFont(btnFont);
			
		addApartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(landlordRegApart(t)); //Takes landlord user to register apartment page
			}
		});
			
		Button requestsBtn = new Button("View Requests");
		requestsBtn.setTranslateX(115);
		requestsBtn.setTranslateY(160);
		requestsBtn.setPrefHeight(50);
		requestsBtn.setPrefWidth(170);
		requestsBtn.setFont(btnFont);
			
		requestsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(viewRequests(t)); //Takes landlord user to register apartment page
			}
		});
				
		Button accBtn = new Button("Account");
		accBtn.setTranslateX(115);
		accBtn.setTranslateY(220);
		accBtn.setPrefHeight(50);
		accBtn.setPrefWidth(170);
		accBtn.setFont(btnFont);
		

		accBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(viewLandlordAcc(t)); 
			}
		});
		
		Button chargeTentBtn = new Button("Charge Tenants");
		chargeTentBtn.setTranslateX(115);
		chargeTentBtn.setTranslateY(280);
		chargeTentBtn.setPrefHeight(50);
		chargeTentBtn.setPrefWidth(170);
		chargeTentBtn.setFont(btnFont);
		
		
		chargeTentBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(charges(t)); 
			}
		});
		
		
		Button logOutBtn = new Button("Logout");
		logOutBtn.setTranslateX(3);
		logOutBtn.setTranslateY(320);
			
		logOutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				t.setRoot(login(t));
				
				landlord.setName(null);
				landlord.setEmail(null);
				landlord.setPassword(null);
				landlord.setPhone(null);
			}
		});
			
			
		Pane landlordUiPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		landlordUiPane.setBackground(background);
			
		landlordUiPane.getChildren().addAll(titleLbl,line,searchBtn,addApartBtn,logOutBtn,requestsBtn,accBtn,chargeTentBtn);
		return landlordUiPane;
	}
	
	//Landlords can search for tenant information
	public Pane landlordSearch(Scene t) {
		t.getWindow().setWidth(700);
		t.getWindow().setHeight(230);
		
		Label titleLbl = new Label("Search");
		titleLbl.setFont(titleFont);
		titleLbl.setTranslateX(3);
			
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(700); 
		line.setStartY(30);
		line.setEndY(30);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		
		ImageView searchImg  = new ImageView("https://cdn-icons-png.flaticon.com/512/783/783889.png");
		searchImg.setTranslateX(3);
		searchImg.setTranslateY(38);
		searchImg.setFitWidth(40);
		searchImg.setFitHeight(40);
		
		Label searchLbl = new Label("Enter Tenant or Apartment");
		searchLbl.setTranslateX(50);
		searchLbl.setTranslateY(40);
		searchLbl.setFont(btnFont);
		
		TextField searchTxtF = new TextField();
		searchTxtF.setTranslateX(300);
		searchTxtF.setTranslateY(40);
		
		//User choosing if they are a tenant or a landlord
		ToggleGroup group = new ToggleGroup();

		RadioButton tenantRBtn = new RadioButton("Tenants");
		tenantRBtn.setToggleGroup(group);
		tenantRBtn.setTranslateX(460);
		tenantRBtn.setTranslateY(43);
						
		RadioButton apartRBtn = new RadioButton("Apartments");
		apartRBtn.setToggleGroup(group);
		apartRBtn.setTranslateX(540);
		apartRBtn.setTranslateY(43);
		
		Button searchBtn = new Button("Search");
		searchBtn.setTranslateX(3);
		searchBtn.setTranslateY(83);
		
		Text resultTxt = new Text("");
		resultTxt.setTranslateX(3);
		resultTxt.setTranslateY(130);
		
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Landlords search tenants in database
				if(tenantRBtn.isSelected() ) {
					if(searchTxtF.getText().matches("")) { //What happens when search box is empty 
						resultTxt.setText("Nothing in search box");
					}else { //When search query is successful
						String searchTxt = searchTxtF.getText();
						List<String> tenantList = landlord.searchTenants(searchTxt);
						
						for (String i : tenantList) {
							resultTxt.setText(i);
						}
						
						if(tenantList.isEmpty()) {// What happens when there are no matches from search query
							resultTxt.setText("No matches ");
						}
					}
				//Landlords search apartments in database	
				}else if(apartRBtn.isSelected()) {
					if(searchTxtF.getText().matches("")) {//What happens when search box is empty
						resultTxt.setText("Nothing in search box");
					}else {//When search query is successful
						String searchTxt = searchTxtF.getText();
						List<String> apartmentList = landlord.searchApartments(searchTxt);
						for (String a : apartmentList) {
							resultTxt.setText(a);
						}
					
						if(apartmentList.isEmpty()) {// What happens when there are no matches from search query
							resultTxt.setText("No matches");
						}
					}
				//User needs to either select tenant or apartment button
				}else if(!tenantRBtn.isSelected() && !apartRBtn.isSelected()) {
					resultTxt.setText("Please choose either the tenant or apartment button");
				}
			}
		});
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(163);
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(landlordUI(t));
			}
		});
		
		Pane landlordSearchPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		landlordSearchPane.setBackground(background);
		
		landlordSearchPane.getChildren().addAll(titleLbl,line,searchImg,searchLbl,searchTxtF,tenantRBtn,apartRBtn,searchBtn,resultTxt,backBtn);
		return landlordSearchPane;
	}
	
	//Landlords can view requests sent by tenants
	public Pane viewRequests(Scene t) {
		t.getWindow().setHeight(120);
		
		Label titleLbl = new Label("View Requests");
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
		
		Label requestLbl = new Label("Choose Request:");
		requestLbl.setTranslateX(3);
		requestLbl.setTranslateY(38);
		requestLbl.setFont(btnFont);
		
		List<String> requestIdList = new ArrayList<String>();
		requestIdList = landlord.getAllRequestID();
		
		
		ChoiceBox<String> requestIdCB = new ChoiceBox<String>();
		requestIdCB.setTranslateX(157);
		requestIdCB.setTranslateY(38);
		
		//Adds all requestId's from array list to choice box
		int i = 0;
		while(i < requestIdList.size()) {
			requestIdCB.getItems().add(requestIdList.get(i));
			i++;
		}
		
		Text errorMsg = new Text("");
		errorMsg.setVisible(false);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(75);
		
		//Name of tenant who made the request
		Label tenantNameLbl = new Label();
		tenantNameLbl.setTranslateX(3);
		tenantNameLbl.setTranslateY(77);
		tenantNameLbl.setFont(btnFont);
		tenantNameLbl.setVisible(false);
		
		//Name of the apartment for the request
		Label apartNameLbl = new Label();
		apartNameLbl.setTranslateX(3);
		apartNameLbl.setTranslateY(110);
		apartNameLbl.setFont(btnFont);
		apartNameLbl.setVisible(false);
		
		//Location of request in apartment
		Label requestLocLbl = new Label();
		requestLocLbl.setTranslateX(3);
		requestLocLbl.setTranslateY(143);
		requestLocLbl.setFont(btnFont);
		requestLocLbl.setVisible(false);
		
		//Description of request
		Label requestDesLbl = new Label();
		requestDesLbl.setTranslateX(3);
		requestDesLbl.setTranslateY(176);
		requestDesLbl.setFont(btnFont);
		requestDesLbl.setVisible(false);
		
		//Date Request was created
		Label requestDateLbl = new Label();
		requestDateLbl.setTranslateX(3);
		requestDateLbl.setTranslateY(209);
		requestDateLbl.setFont(btnFont);
		requestDateLbl.setVisible(false);
		
		//Status of request
		Label requestStatLbl = new Label();
		requestStatLbl.setTranslateX(3);
		requestStatLbl.setTranslateY(242);
		requestStatLbl.setFont(btnFont);
		requestStatLbl.setVisible(false);
		
		ChoiceBox<String> statusCB = new ChoiceBox<String>();
		statusCB.getItems().add("Incomplete");
		statusCB.getItems().add("In Progress");
		statusCB.getItems().add("Complete");
		statusCB.setTranslateX(180);
		statusCB.setTranslateY(242);
		
		Button setStatBtn = new Button("Set Status");
		setStatBtn.setTranslateX(285);
		setStatBtn.setTranslateY(242);
		
		Text statusErrorMsg = new Text();
		statusErrorMsg.setTranslateX(3);
		statusErrorMsg.setTranslateY(278);
		statusErrorMsg.setVisible(false);
		
		//Button to view the info of specific request
		Button viewRequestBtn = new Button("View Request");
		viewRequestBtn.setTranslateX(235);
		viewRequestBtn.setTranslateY(38);	
		
		viewRequestBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (requestIdCB.getValue() == null) {
					errorMsg.setVisible(true);
					errorMsg.setText("Please select an Request ID");
				}else {
					errorMsg.setVisible(false);
					t.getWindow().setHeight(320);
					
					String requestId = requestIdCB.getValue();
					
					//Getting the name of the tenant who made the request
					String tenantName = requests.getTenantNameFromRequests(requestId);
					tenantNameLbl.setText("Tenant Name: " + tenantName);
					tenantNameLbl.setVisible(true);
					
					//Getting the name of the apartment for the request
					String apartName = requests.getApartNameFromRequests(requestId);
					apartNameLbl.setText("Apartment Name: " + apartName);
					apartNameLbl.setVisible(true);
					
					//Getting request location 
					String requestLoc = requests.getLocationFromRequests(requestId);
					requestLocLbl.setText("Location: " + requestLoc);
					requestLocLbl.setVisible(true);
					
					//Getting request description
					String requestDescript = requests.getDescriptionFromRequests(requestId);
					requestDesLbl.setText("Description: " + requestDescript);
					requestDesLbl.setVisible(true);
					
					//Getting request status
					String requestStatus = requests.getStatusFromRequests(requestId);
					requestStatLbl.setText("Status: " + requestStatus);
					requestStatLbl.setVisible(true);
					
					//Getting request start date
					String requestStartDate = requests.getStartDateForRequest(requestId);
					requestDateLbl.setText("Date Created: " + requestStartDate);
					requestDateLbl.setVisible(true);
				}
				
			}
		});
		
		setStatBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String requestId = requestIdCB.getValue();
				String status = statusCB.getValue();
				
				if(statusCB.getValue() == null) {
					statusErrorMsg.setText("Please select a status from the choicebox");
					statusErrorMsg.setVisible(true);
				}else {
					if(status.matches(requests.getStatusFromRequests(requestId))) {
						statusErrorMsg.setText("Please choose a different status");
						statusErrorMsg.setVisible(true);
					}else {
						statusErrorMsg.setVisible(false);
						requests.updateStatusForRequest(requestId, status);
						requestStatLbl.setText("Status: " + status);
					}
				}
			}
		});
		
		Button backtBtn = new Button("Back");
		backtBtn.setTranslateX(350);
		backtBtn.setTranslateY(38);	
		
		backtBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(landlordUI(t));
			}
		});
		
		Pane viewRequestsPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		viewRequestsPane.setBackground(background);
		
		viewRequestsPane.getChildren().addAll(titleLbl,line,requestLbl,requestIdCB,viewRequestBtn,errorMsg,backtBtn,tenantNameLbl,apartNameLbl,requestLocLbl,requestDesLbl,requestDateLbl,requestStatLbl,statusCB,setStatBtn,statusErrorMsg);
		return viewRequestsPane;
	}
	
	//Landlords can view and update account info
	public Pane viewLandlordAcc(Scene t) {
		t.getWindow().setHeight(400);
		t.getWindow().setWidth(412);
		
		Label titleLbl = new Label("Account Details");
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
		
		//Shows landlord id
		int landlordId = landlord.getLandlordIdFromDB();
		Label landlordIdLbl = new Label("Landlord ID: " + landlordId);
		landlordIdLbl.setTranslateX(3);
		landlordIdLbl.setTranslateY(38);
		landlordIdLbl.setFont(btnFont);
		
		String name = landlord.getName();
		Label nameLbl = new Label("Full Name: " + name);
		nameLbl.setTranslateX(3);
		nameLbl.setTranslateY(71);
		nameLbl.setFont(btnFont);
		
		//Email field
		String email = landlord.getEmail();
		Label emailLbl = new Label("Email: " + email);
		emailLbl.setTranslateX(3);
		emailLbl.setTranslateY(104);
		emailLbl.setFont(btnFont);
		
		Button chgEmailBtn = new Button("Change Email");
		chgEmailBtn.setTranslateX(310);
		chgEmailBtn.setTranslateY(104);
		
		chgEmailBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(chgUserEmail(t)); //Takes user landlord user to change email
			}
		} );
		
		//Password field
		String password = landlord.getPassword();
		Label passwordLbl = new Label("Password: " + password);
		passwordLbl.setTranslateX(3);
		passwordLbl.setTranslateY(137);
		passwordLbl.setFont(btnFont);
		
		Button chgPasswrdBtn = new Button("Change Password");
		chgPasswrdBtn.setTranslateX(288);
		chgPasswrdBtn.setTranslateY(137);
		
		chgPasswrdBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(chgUserPassword(t));
			}
		});
	
		//Phone field
		String phone = landlord.getPhone();
		Label phoneLbl = new Label("Phone: " + phone);
		phoneLbl.setTranslateX(3);
		phoneLbl.setTranslateY(170);
		phoneLbl.setFont(btnFont);
		
		Button chgPhoneBtn = new Button("Change Phone");
		chgPhoneBtn.setTranslateX(305);
		chgPhoneBtn.setTranslateY(170);
		
		chgPhoneBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(chgUserPhone(t));
			}
		});
		
		int numOfAparts = landlord.getNumOfApartmentsForLandlord();
		Label numOfApartmentsLbl = new Label("Number of Apartments: " + numOfAparts);
		numOfApartmentsLbl.setTranslateX(3);
		numOfApartmentsLbl.setTranslateY(203);
		numOfApartmentsLbl.setFont(btnFont);
		
		int numOfTenants = landlord.getNumOfTenantsForLandlord();
		Label numOfTenantsLbl = new Label("Number of Tenants: " + numOfTenants);
		numOfTenantsLbl.setTranslateX(3);
		numOfTenantsLbl.setTranslateY(236);
		numOfTenantsLbl.setFont(btnFont);
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(330);
			
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				t.setRoot(landlordUI(t));
			}
		});
					
		Pane viewLandlordAccPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		viewLandlordAccPane.setBackground(background);
		
		viewLandlordAccPane.getChildren().addAll(titleLbl,line,landlordIdLbl,nameLbl,emailLbl,chgEmailBtn,passwordLbl,chgPasswrdBtn,phoneLbl,chgPhoneBtn,numOfApartmentsLbl,numOfTenantsLbl,backBtn);
		return viewLandlordAccPane;
	}
	
	public Pane charges(Scene t) {
		t.getWindow().setHeight(120);
		
		Label titleLbl = new Label("Charge Tenants");
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
		
		Label tenantLbl = new Label("Choose Tenant:");
		tenantLbl.setTranslateX(3);
		tenantLbl.setTranslateY(38);
		tenantLbl.setFont(btnFont);
		
		List<String> tenantIdList = new ArrayList<String>();
		tenantIdList = landlord.getAllTenantIdsForLandlord();
		
		
		ChoiceBox<String> tenantIdCB = new ChoiceBox<String>();
		tenantIdCB.setTranslateX(147);
		tenantIdCB.setTranslateY(38);
		
		//Adds all requestId's from array list to choice box
		int i = 0;
		while(i < tenantIdList.size()) {
			tenantIdCB.getItems().add(tenantIdList.get(i));
			i++;
		}
		
		Text errorMsg1 = new Text("");
		errorMsg1.setVisible(false);
		errorMsg1.setTranslateX(3);
		errorMsg1.setTranslateY(75);
		
		Text errorMsg2 = new Text("");
		errorMsg2.setVisible(false);
		errorMsg2.setTranslateX(3);
		errorMsg2.setTranslateY(150);
		
		//Name of the apartment for the request
		Label tenantNameLbl = new Label();
		tenantNameLbl.setTranslateX(3);
		tenantNameLbl.setTranslateY(77);
		tenantNameLbl.setFont(btnFont);
		tenantNameLbl.setVisible(false);
		
		Label amtToChargeLbl = new Label();
		amtToChargeLbl.setTranslateX(3);
		amtToChargeLbl.setTranslateY(110);
		amtToChargeLbl.setFont(btnFont);
		amtToChargeLbl.setVisible(false);
		
		TextField amtToChargeTxtF = new TextField();
		amtToChargeTxtF.setTranslateX(185);
		amtToChargeTxtF.setTranslateY(110);
		amtToChargeTxtF.setVisible(false);
		
		Button chargeBtn = new Button("Charge");
		chargeBtn.setTranslateX(330);
		chargeBtn.setTranslateY(160);
		chargeBtn.setVisible(false);
		
		chargeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (amtToChargeTxtF.getText().isEmpty()) {
					errorMsg2.setText("Please enter an amount");
					errorMsg2.setVisible(true);
				}else if (!amtToChargeTxtF.getText().matches("^(\\d+)(\\.\\d+)?$")) {
					errorMsg2.setText("Please enter a decimal or whole number.");
					errorMsg2.setVisible(true);
				}else {
					double bal = Double.parseDouble(amtToChargeTxtF.getText());
					String tenantId = tenantIdCB.getValue();
					
					landlord.updateBalance(tenantId, bal);
					errorMsg2.setVisible(true);
					errorMsg2.setText("Tenant's balance has been updated to $" + landlord.getBalance(tenantId));
				}
			}
		});
		
		
		
		//Button to view the info of specific request
		Button chooseTenantBtn = new Button("Select");
		chooseTenantBtn.setTranslateX(255);
		chooseTenantBtn.setTranslateY(38);
		
		chooseTenantBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (tenantIdCB.getValue() == null) {
					errorMsg1.setVisible(true);
					errorMsg1.setText("Please select a tenant");
				}else {
					errorMsg1.setVisible(false);
					t.getWindow().setHeight(230);
					
					String tenantName = landlord.getTenantNameFromId(tenantIdCB.getValue());
					tenantNameLbl.setText("Tenant Name: " + tenantName);
					tenantNameLbl.setVisible(true);
					
					amtToChargeLbl.setText("Amount to Charge:$");
					amtToChargeLbl.setVisible(true);
					amtToChargeTxtF.setVisible(true);
					
					chargeBtn.setVisible(true);
				}
			}
		});
		
		
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(350);
		backBtn.setTranslateY(38);	
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(landlordUI(t));
			}
		});
		
		Pane chargesPane = new Pane();
		
		chargesPane.getChildren().addAll(titleLbl,line,tenantLbl,tenantIdCB,errorMsg1,errorMsg2,chooseTenantBtn,tenantNameLbl,amtToChargeLbl,amtToChargeTxtF,chargeBtn,backBtn);
		return chargesPane;
	}
	
	//Where new tenants can create a new tenant account
	public Pane newTenantAcc (Scene t) {
			
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
			
		//Name field
		Label nameLbl = new Label("Name: ");
		nameLbl.setTranslateX(3);
		nameLbl.setTranslateY(38);
		nameLbl.setFont(btnFont);
			
		TextField nameTxtF = new TextField();
		nameTxtF.setTranslateX(65);
		nameTxtF.setTranslateY(38);
			
		//Email field
		Label emailLbl = new Label("Email: ");
		emailLbl.setTranslateX(3);
		emailLbl.setTranslateY(71);
		emailLbl.setFont(btnFont);
			
		TextField emailTxtF = new TextField();
		emailTxtF.setTranslateX(70);
		emailTxtF.setTranslateY(71);
			
		//Password field
		Label passwordLbl = new Label("Password:");
		passwordLbl.setTranslateX(3);
		passwordLbl.setTranslateY(104);
		passwordLbl.setFont(btnFont);
			
		TextField passwordTxtF = new TextField();
		passwordTxtF.setTranslateX(100);
		passwordTxtF.setTranslateY(104);
			
		//Phone field
		Label phoneLbl = new Label("Phone:");
		phoneLbl.setTranslateX(3);
		phoneLbl.setTranslateY(137);
		phoneLbl.setFont(btnFont);
			
		TextField phoneTxtF = new TextField();
		phoneTxtF.setTranslateX(70);
		phoneTxtF.setTranslateY(137);
		
		Text errorMsg = new Text("");
		errorMsg.setVisible(false);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(240);
			
		//adds new tenant user to database;
		Button createTenantAccBtn = new Button("Create Account");
		createTenantAccBtn.setTranslateX(90);
		createTenantAccBtn.setTranslateY(190);
			
		createTenantAccBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(nameTxtF.getText().isEmpty() || emailTxtF.getText().isEmpty() || passwordTxtF.getText().isEmpty() || phoneTxtF.getText().isEmpty()) {
					errorMsg.setVisible(true);
					errorMsg.setText("Please input all information");
				}else {
					String inputedName = nameTxtF.getText();
					String inputedEmail = emailTxtF.getText();
					String inputedPassword = passwordTxtF.getText();
					String inputedPhone = phoneTxtF.getText();
					
					//Checking in user inputed information is written correctly
					if(inputedName.matches("^[A-Z][a-z]+ [A-Z][a-z]+$") && inputedEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
							&& inputedPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{7,}$") && inputedPhone.matches("^(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$")) {
						tenant.insertTenantToDB(inputedName, inputedEmail, inputedPassword, inputedPhone); //Adds new landlord user to database
						
						//Setting tenant object values to inputed information
						tenant.setName(inputedName);
						tenant.setEmail(inputedEmail);
						tenant.setPassword(inputedPassword);
						tenant.setPhone(inputedPhone);
						t.setRoot(tenantMakeLease(t));
					//Display error message if format is wrong	
					}else {
						errorMsg.setVisible(true);
						errorMsg.setText("Please make sure that all information is correctly formatted");
					}
				}
			 }
		 });
			
		//Back button
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(250);
			
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(login(t)); //Takes user back to login page
			}
		});
			
			
		Pane newTenantAccPane = new Pane();
			
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		newTenantAccPane.setBackground(background);
			
		newTenantAccPane.getChildren().addAll(titleLbl,line,nameLbl,nameTxtF,emailLbl,emailTxtF,passwordLbl,passwordTxtF,phoneLbl,phoneTxtF,createTenantAccBtn,backBtn,errorMsg);
		return newTenantAccPane;
	}
	
	public Pane tenantMakeLease(Scene t) {
		t.getWindow().setHeight(300);
		
		Label titleLbl = new Label("Create New Lease");
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
		
		Label apartComplexLbl = new Label("Choose Apartment:");
		apartComplexLbl.setTranslateX(3);
		apartComplexLbl.setTranslateY(38);
		apartComplexLbl.setFont(btnFont);
		
		//Gets all apartment names from database
		List<String> apartNameList = new ArrayList<String>();
		apartNameList = apartment.getAllApartmentNames();
		
		//Choice box for apartment complexes
		ChoiceBox<String> apartNameCB = new ChoiceBox<String>();
		apartNameCB.setTranslateX(177);
		apartNameCB.setTranslateY(38);
		
		//Adds all apartment names from array list to choice box
		int i = 0;
		while(i < apartNameList.size()) {
			apartNameCB.getItems().add(apartNameList.get(i));
			i++;
		}
		
		//Lease length field
		Label leaseLengthLbl = new Label("Lease Length: ");
		leaseLengthLbl.setTranslateX(3);
		leaseLengthLbl.setTranslateY(71);
		leaseLengthLbl.setFont(btnFont);
				
		ChoiceBox<Integer> leaseLengthCB = new ChoiceBox<Integer>();
		leaseLengthCB.getItems().add(1);
		leaseLengthCB.getItems().add(2);
		leaseLengthCB.getItems().add(3);
		leaseLengthCB.getItems().add(4);
		leaseLengthCB.getItems().add(5);
		leaseLengthCB.setTranslateX(133);
		leaseLengthCB.setTranslateY(71);
		
		//Rent Field
		Label rentLbl = new Label("Rent:");
		rentLbl.setTranslateX(3);
		rentLbl.setTranslateY(104);
		rentLbl.setFont(btnFont);
		
		ChoiceBox<Integer> rentCB = new ChoiceBox<Integer>();
		rentCB.setTranslateX(55);
		rentCB.setTranslateY(104);
		
		//Button will get rent amount based on apartment_name
		Button rentBtn = new Button("Get Rent");
		rentBtn.setTranslateX(125);
		rentBtn.setTranslateY(104);
		
		rentBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				apartment.setName(apartNameCB.getValue());
				int rent = apartment.getRentFromDB(apartment.getName());
				rentCB.getItems().clear();
				rentCB.getItems().add(rent);
			}
		});
		
		//Start date field
		Label dateLbl = new Label("Start Date:");
		dateLbl.setTranslateX(3);
		dateLbl.setTranslateY(137);
		dateLbl.setFont(btnFont);
		
		DatePicker dateTxtF = new DatePicker();
		dateTxtF.setTranslateX(107);
		dateTxtF.setTranslateY(137);
		
		//Error message
		Text errorMsg = new Text("");
		errorMsg.setX(3);
		errorMsg.setY(190);
		errorMsg.setVisible(false);
		
		//Button will add new lease to database
		Button createLeaseBtn = new Button("Create Lease");
		createLeaseBtn.setTranslateX(150);
		createLeaseBtn.setTranslateY(200);
		
		createLeaseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(rentCB.getValue() == 0 || rentCB.getValue() == null) { //If rent value is null it will display error message
					errorMsg.setText("Please press the get rent button");
					errorMsg.setVisible(true);
				}
				
				else {
					if(apartment.numOfTenantsFromDB(apartNameCB.getValue()) >= apartment.maxNumOfTenantsFromDB(apartNameCB.getValue())) {
						errorMsg.setText("This apartment is at full capacity. Please choose another one");
						errorMsg.setVisible(true);
					}else {
						String apartmentName = apartNameCB.getValue();//Storing apartment name choice in string variable and stores it in apartment object
						apartment.setName(apartmentName);
						
						int leaseLength = leaseLengthCB.getValue();//Storing lease length choice in integer variable
						int rent = rentCB.getValue();
						
						LocalDate starDateVal = dateTxtF.getValue();//Storing start date choice in String variable
						String startDate = String.valueOf(starDateVal);
						
						LocalDate endDateVal = dateTxtF.getValue().plusYears(leaseLength);//Storing end date choice in String variable
						String endDate = String.valueOf(endDateVal);
						
						lease.createLease(tenant, apartment, apartmentName, leaseLength, rent, startDate, endDate);//Adds new lease to database
						
						tenant.updateLandlordIdForTenant(lease, tenant);//Updates tenants's landlord
						tenant.updateNumOfTenantsForLandandlord(lease, tenant);//Updates amount of tenants a landlord has
						
						lease.updateNumOfTenantsForApartments(tenant);//Update numbers of tenants for apartments
						
						t.setRoot(tenantUI(t));//Takes tenant user to tenant home page
					}
					
				}
			}
		});
		
		Pane tenantMakeLeasePane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		tenantMakeLeasePane.setBackground(background);
		
		tenantMakeLeasePane.getChildren().addAll(titleLbl,line,apartComplexLbl,apartNameCB,leaseLengthLbl,leaseLengthCB,rentLbl,rentCB,rentBtn,dateLbl,dateTxtF,createLeaseBtn,errorMsg);
		
		return tenantMakeLeasePane;
	}
	
	//Home page for tenant users
	public Pane tenantUI(Scene t) {	
		t.getWindow().setHeight(410);
		t.getWindow().setWidth(410);
		
		Label titleLbl = new Label("Tenant Home Page");
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
		
		Button searchBtn = new Button("Search");
		searchBtn.setTranslateX(115);
		searchBtn.setTranslateY(40);
		searchBtn.setPrefHeight(50);
		searchBtn.setPrefWidth(170);
		searchBtn.setFont(btnFont);
				
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(tenantSearch(t));
			}
		});
					
		
		Button activeRequestsBtn = new Button("Active Requests");
		activeRequestsBtn.setTranslateX(115);
		activeRequestsBtn.setTranslateY(100);
		activeRequestsBtn.setPrefHeight(50);
		activeRequestsBtn.setPrefWidth(170);
		activeRequestsBtn.setFont(new Font("Elephant",14));
					
		activeRequestsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(activeRequests(t)); 
			}
		});
					
		Button requestsBtn = new Button("Make Request");
		requestsBtn.setTranslateX(115);
		requestsBtn.setTranslateY(160);
		requestsBtn.setPrefHeight(50);
		requestsBtn.setPrefWidth(170);
		requestsBtn.setFont(btnFont);
					
		requestsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(makeRequest(t)); 
			}
		});
						
		Button accBtn = new Button("Account");
		accBtn.setTranslateX(115);
		accBtn.setTranslateY(220);
		accBtn.setPrefHeight(50);
		accBtn.setPrefWidth(170);
		accBtn.setFont(btnFont);
				
		accBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(teanantAccInfo(t)); 
			}
		});
		
		Button leaseBtn = new Button("View Lease");
		leaseBtn.setTranslateX(115);
		leaseBtn.setTranslateY(280);
		leaseBtn.setPrefHeight(50);
		leaseBtn.setPrefWidth(170);
		leaseBtn.setFont(btnFont);
		
		leaseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(viewLease(t));
			}
		});
				
		Button logOutBtn = new Button("Logout");
		logOutBtn.setTranslateX(3);
		logOutBtn.setTranslateY(344);
		
		logOutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				tenant.setName(null);
				tenant.setEmail(null);
				tenant.setPassword(null);
				tenant.setPhone(null);
				t.setRoot(login(t));
			}
		});
		
		Pane tenantUiPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		tenantUiPane.setBackground(background);
		
		tenantUiPane.getChildren().addAll(titleLbl,line,searchBtn,activeRequestsBtn,requestsBtn,accBtn,leaseBtn,logOutBtn);
		return tenantUiPane;
	}
	
	public Pane tenantSearch(Scene t) {
		t.getWindow().setWidth(700);
		t.getWindow().setHeight(230);
		
		Label titleLbl = new Label("Search");
		titleLbl.setFont(titleFont);
		titleLbl.setTranslateX(3);
			
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(700); 
		line.setStartY(30);
		line.setEndY(30);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		
		ImageView searchImg  = new ImageView("https://cdn-icons-png.flaticon.com/512/783/783889.png");
		searchImg.setTranslateX(3);
		searchImg.setTranslateY(38);
		searchImg.setFitWidth(40);
		searchImg.setFitHeight(40);
		
		Label searchLbl = new Label("Enter Landlord or Apartments");
		searchLbl.setTranslateX(50);
		searchLbl.setTranslateY(40);
		searchLbl.setFont(btnFont);
		
		TextField searchTxtF = new TextField();
		searchTxtF.setTranslateX(330);
		searchTxtF.setTranslateY(40);
		
		
		ToggleGroup group = new ToggleGroup();

		RadioButton landlordRBtn = new RadioButton("Landlords");
		landlordRBtn.setToggleGroup(group);
		landlordRBtn.setTranslateX(490);
		landlordRBtn.setTranslateY(43);
						
		RadioButton apartRBtn = new RadioButton("Apartments");
		apartRBtn.setToggleGroup(group);
		apartRBtn.setTranslateX(570);
		apartRBtn.setTranslateY(43);
		
		Text resultTxt = new Text("");
		resultTxt.setTranslateX(3);
		resultTxt.setTranslateY(130);
		
		Button searchBtn = new Button("Search");
		searchBtn.setTranslateX(3);
		searchBtn.setTranslateY(83);
		
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Tenants search landlords in database
				if(landlordRBtn.isSelected() ) {
					if(searchTxtF.getText().matches("")) { //What happens when search box is empty 
						resultTxt.setText("Nothing in search box");
					}else { //When search query is successful
						String searchTxt = searchTxtF.getText();
						List<String> landlordList = tenant.searchLandlords(searchTxt);
						
						for (String i : landlordList) {
							resultTxt.setText(i);
						}
						
						if(landlordList.isEmpty()) {// What happens when there are no matches from search query
							resultTxt.setText("No matches ");
						}
					}
				//Tenants search apartments in database	
				}else if(apartRBtn.isSelected()) {
					if(searchTxtF.getText().matches("")) {//What happens when search box is empty
						resultTxt.setText("Nothing in search box");
					}else {//When search query is successful
						String searchTxt = searchTxtF.getText();
						List<String> apartmentList = tenant.searchApartments(searchTxt);
						for (String a : apartmentList) {
							resultTxt.setText(a);
						}
					
						if(apartmentList.isEmpty()) {// What happens when there are no matches from search query
							resultTxt.setText("No matches");
							
							
						}
					}
				//User needs to either select landlord or apartment button
				}else if(!landlordRBtn.isSelected() && !apartRBtn.isSelected()) {
					resultTxt.setText("Please choose either the landlord or apartment button");
				}
			}
		});
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(163);
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(tenantUI(t));
			}
		});
		
		
		Pane tenantSearchPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		tenantSearchPane.setBackground(background);
		
		tenantSearchPane.getChildren().addAll(titleLbl,line,searchImg,searchLbl,searchTxtF,landlordRBtn,apartRBtn,searchBtn,resultTxt,backBtn);
		return tenantSearchPane;
	}
	
	public Pane activeRequests(Scene t) {
		t.getWindow().setHeight(120);
		
		Label titleLbl = new Label("Active Requests");
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
		
		Label requestLbl = new Label("Choose Request:");
		requestLbl.setTranslateX(3);
		requestLbl.setTranslateY(38);
		requestLbl.setFont(btnFont);
		
		Text errorMsg = new Text("");
		errorMsg.setVisible(false);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(75);
		
		List<String> requestIdList = new ArrayList<String>();
		requestIdList = tenant.getAllRequestIDForOneTenant();
		
		ChoiceBox<String> requestIdCB = new ChoiceBox<String>();
		requestIdCB.setTranslateX(157);
		requestIdCB.setTranslateY(38);
		
		//Adds all requestId's from array list to choice box
		int i = 0;
		while(i < requestIdList.size()) {
			requestIdCB.getItems().add(requestIdList.get(i));
			i++;
		}
		
		//Location of request in apartment
		Label requestLocLbl = new Label();
		requestLocLbl.setTranslateX(3);
		requestLocLbl.setTranslateY(77);
		requestLocLbl.setFont(btnFont);
		requestLocLbl.setVisible(false);
		
		//Category of request
		Label requestCatLbl = new Label();
		requestCatLbl.setTranslateX(3);
		requestCatLbl.setTranslateY(110);
		requestCatLbl.setFont(btnFont);
		requestCatLbl.setVisible(false);
		
		//Description of request
		Label requestDesLbl = new Label();
		requestDesLbl.setTranslateX(3);
		requestDesLbl.setTranslateY(143);
		requestDesLbl.setFont(btnFont);
		requestDesLbl.setVisible(false);
				
		//Date Request was created
		Label requestDateLbl = new Label();
		requestDateLbl.setTranslateX(3);
		requestDateLbl.setTranslateY(176);
		requestDateLbl.setFont(btnFont);
		requestDateLbl.setVisible(false);
					
		//Status of request
		Label requestStatLbl = new Label();
		requestStatLbl.setTranslateX(3);
		requestStatLbl.setTranslateY(209);
		requestStatLbl.setFont(btnFont);
		requestStatLbl.setVisible(false);
		
		Button viewRequestBtn = new Button("View Request");
		viewRequestBtn.setTranslateX(235);
		viewRequestBtn.setTranslateY(38);	
		
		viewRequestBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (requestIdCB.getValue() == null) {
					errorMsg.setVisible(true);
					errorMsg.setText("Please select an Request ID");
				}else {
					errorMsg.setVisible(false);
					t.getWindow().setHeight(320);
					
					String requestId = requestIdCB.getValue();
					
					//Getting request location 
					String requestLoc = requests.getLocationFromRequests(requestId);
					requestLocLbl.setText("Location: " + requestLoc);
					requestLocLbl.setVisible(true);
					
					//Getting request location 
					String requestCat = requests.getCategoryFromRequests(requestId);
					requestCatLbl.setText("Category: " + requestCat);
					requestCatLbl.setVisible(true);
					
					//Getting request description
					String requestDescript = requests.getDescriptionFromRequests(requestId);
					requestDesLbl.setText("Description: " + requestDescript);
					requestDesLbl.setVisible(true);
					
					//Getting request start date
					String requestStartDate = requests.getStartDateForRequest(requestId);
					requestDateLbl.setText("Date Created: " + requestStartDate);
					requestDateLbl.setVisible(true);
					
					//Getting request status
					String requestStatus = requests.getStatusFromRequests(requestId);
					requestStatLbl.setText("Status: " + requestStatus);
					requestStatLbl.setVisible(true);
					
					
				}
			}
		});
		
		Button backtBtn = new Button("Back");
		backtBtn.setTranslateX(350);
		backtBtn.setTranslateY(38);	
		
		backtBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(tenantUI(t));
			}
		});
		
		Button deleteBtn = new Button("Delete Request");
		deleteBtn.setTranslateX(3);
		deleteBtn.setTranslateY(250);
		
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				t.getWindow().setHeight(120);
				
				//Deletes request from database
				String requestId = requestIdCB.getValue();
				requests.deleteRequestFromDB(requestId);
				
				errorMsg.setVisible(true);
				errorMsg.setText("Request has been deleted");
				
				requestIdCB.getItems().remove(requestId);
				requestIdCB.setValue(null);
			}
		});
		
		
		
		Pane activeRequestsPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		activeRequestsPane.setBackground(background);
		
		activeRequestsPane.getChildren().addAll(titleLbl,line,requestLbl,requestIdCB,errorMsg,viewRequestBtn,requestLocLbl,requestCatLbl,requestDesLbl,requestDateLbl,requestStatLbl,backtBtn,deleteBtn);
		return activeRequestsPane;
	}
	
	public Pane makeRequest(Scene t) {
		t.getWindow().setHeight(300);
		
		Label titleLbl = new Label("Make Request");
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
		
		Label requestLocLbl = new Label("Request Location:");
		requestLocLbl.setTranslateX(3);
		requestLocLbl.setTranslateY(38);
		requestLocLbl.setFont(btnFont);
		
		ChoiceBox<String> requestLocCB = new ChoiceBox<String>();
		requestLocCB.getItems().add("Bedroom");
		requestLocCB.getItems().add("Bathroom");
		requestLocCB.getItems().add("Kitchen");
		requestLocCB.getItems().add("Laundry Room");
		requestLocCB.getItems().add("Common Area");
		requestLocCB.getItems().add("Unit Wide");
		requestLocCB.setTranslateX(170);
		requestLocCB.setTranslateY(38);
		
		Label requestCatLbl = new Label("Request Category:");
		requestCatLbl.setTranslateX(3);
		requestCatLbl.setTranslateY(71);
		requestCatLbl.setFont(btnFont);
		
		ChoiceBox<String> requestCatCB = new ChoiceBox<String>();
		requestCatCB.getItems().add("Air Conditioning/Heating");
		requestCatCB.getItems().add("Appliances");
		requestCatCB.getItems().add("Plumbing");
		requestCatCB.getItems().add("Electrical");
		requestCatCB.getItems().add("Flooring");
		requestCatCB.getItems().add("Furniture");
		requestCatCB.getItems().add("Pest Control");
		requestCatCB.getItems().add("General");
		requestCatCB.setTranslateX(170);
		requestCatCB.setTranslateY(71);
		
		Label descripLbl = new Label("Description:");
		descripLbl.setTranslateX(3);
		descripLbl.setTranslateY(104);
		descripLbl.setFont(btnFont);
		
		TextArea descripTxtF = new TextArea();
		descripTxtF.setPrefWidth(200);
		descripTxtF.setPrefHeight(50);
		descripTxtF.setTranslateX(125);
		descripTxtF.setTranslateY(104);
		
		Label dateCreatedLbl = new Label("Today's Date:");
		dateCreatedLbl.setTranslateX(3);
		dateCreatedLbl.setTranslateY(163);
		dateCreatedLbl.setFont(btnFont);
		
		DatePicker dateTxtF = new DatePicker();
		dateTxtF.setTranslateX(127);
		dateTxtF.setTranslateY(163);
		
		Text conformationTxt = new Text();
		conformationTxt.setTranslateX(3);
		conformationTxt.setTranslateY(220);
		conformationTxt.setVisible(false);
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(233);
			
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				t.setRoot(tenantUI(t));
			}
		});
		
		Button sendBtn = new Button("Send");
		sendBtn.setTranslateX(350);
		sendBtn.setTranslateY(231);
		
		sendBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(requestLocCB.getValue() == null || requestCatCB.getValue() == null || descripTxtF.getText() == null || dateTxtF.getValue() == null) {
					conformationTxt.setVisible(true);
					conformationTxt.setText("Please input all information");
				}else {
					String location = requestLocCB.getValue();
					String category = requestCatCB.getValue();
					String description = descripTxtF.getText();
					
					LocalDate starDateVal = dateTxtF.getValue();//Storing start date choice in String variable
					String startDate = String.valueOf(starDateVal);
					
					int result = requests.addRequestToDB(tenant, lease, location, description, category, startDate);
					
					if (result == 1) {
						conformationTxt.setVisible(true);
						conformationTxt.setText("Your request has been sent");
					}else if(result == 0) {
						conformationTxt.setVisible(true);
						conformationTxt.setText("Your request has not been sent");
					}
				}
				
				
			}
		});
		
		Pane makeRequestPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		makeRequestPane.setBackground(background);
		
		makeRequestPane.getChildren().addAll(titleLbl,line,requestLocLbl,requestLocCB,requestCatLbl,requestCatCB,descripLbl,descripTxtF,dateCreatedLbl,dateTxtF,backBtn,sendBtn,conformationTxt);
		return makeRequestPane;
	}
	
	public Pane teanantAccInfo (Scene t) {
		t.getWindow().setHeight(300);
		t.getWindow().setWidth(412);
		
		Label titleLbl = new Label("Account Details");
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
		
		//Shows tenant id
		int tenantId = tenant.getTenantIdFromDB();
		Label tenantIdLbl = new Label("Tenant ID: " + tenantId);
		tenantIdLbl.setTranslateX(3);
		tenantIdLbl.setTranslateY(38);
		tenantIdLbl.setFont(btnFont);
		
		String name = tenant.getName();
		Label nameLbl = new Label("Full Name: " + name);
		nameLbl.setTranslateX(3);
		nameLbl.setTranslateY(71);
		nameLbl.setFont(btnFont);
		
		//Email field
		String email = tenant.getEmail();
		Label emailLbl = new Label("Email: " + email);
		emailLbl.setTranslateX(3);
		emailLbl.setTranslateY(104);
		emailLbl.setFont(btnFont);
		
		Button chgEmailBtn = new Button("Change Email");
		chgEmailBtn.setTranslateX(305);
		chgEmailBtn.setTranslateY(104);
		
		chgEmailBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(chgUserEmail(t));
			}
		});
		
		//Password field
		String password = tenant.getPassword();
		Label passwordLbl = new Label("Password: " + password);
		passwordLbl.setTranslateX(3);
		passwordLbl.setTranslateY(137);
		passwordLbl.setFont(btnFont);
		
		Button chgPasswrdBtn = new Button("Change Password");
		chgPasswrdBtn.setTranslateX(283);
		chgPasswrdBtn.setTranslateY(137);
		
		chgPasswrdBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(chgUserPassword(t));
			}
		});
	
		//Phone field
		String phone = tenant.getPhone();
		Label phoneLbl = new Label("Phone: " + phone);
		phoneLbl.setTranslateX(3);
		phoneLbl.setTranslateY(170);
		phoneLbl.setFont(btnFont);
		
		Button chgPhoneBtn = new Button("Change Phone");
		chgPhoneBtn.setTranslateX(300);
		chgPhoneBtn.setTranslateY(170);
		
		chgPhoneBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(chgUserPhone(t));
			}
		});
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(230);
			
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				t.setRoot(tenantUI(t));
			}
		});
		
		
		
		Text deleteAccTxt = new Text("Are you sure you want to delete your account:");
		deleteAccTxt.setTranslateX(3);
		deleteAccTxt.setTranslateY(300);
		deleteAccTxt.setVisible(false);
		
		Button deleteAccBtn = new Button("Delete Account");
		deleteAccBtn.setTranslateX(290);
		deleteAccBtn.setTranslateY(230);
		
		Button yesBtn = new Button("Yes");
		yesBtn.setTranslateX(250);
		yesBtn.setTranslateY(283);
		yesBtn.setVisible(false);
		
		Button noBtn = new Button("No");
		noBtn.setTranslateX(295);
		noBtn.setTranslateY(283);
		noBtn.setVisible(false);
		
		deleteAccBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.getWindow().setHeight(400);
				deleteAccTxt.setVisible(true);
				yesBtn.setVisible(true);
				noBtn.setVisible(true);
			}
		});
		
		yesBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				requests.deleteAllRequestForTenantFromDB(tenant);
				lease.deleteLeaseFromDB(tenant);
				tenant.deleteTenantFromDB();
				t.setRoot(login(t));
			}
		});
		
		noBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				t.getWindow().setHeight(300);
			}
		});
		
		Pane teanantAccInfoPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		teanantAccInfoPane.setBackground(background);
		
		teanantAccInfoPane.getChildren().addAll(titleLbl,line,tenantIdLbl,nameLbl,emailLbl,chgEmailBtn,passwordLbl,chgPasswrdBtn,phoneLbl,chgPhoneBtn,backBtn,deleteAccBtn,deleteAccTxt,yesBtn,noBtn);
		return teanantAccInfoPane;
	}
	
	public Pane viewLease(Scene t) {
		Label titleLbl = new Label("Lease Details");
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
		
		String leaseId = lease.getLeaseIdFromLease(tenant);
		Label leaseIdLbl = new Label("Lease ID: " + leaseId);
		leaseIdLbl.setTranslateX(3);
		leaseIdLbl.setTranslateY(38);
		leaseIdLbl.setFont(btnFont);
		
		String apartName = lease.getApartNameFromLease(tenant);
		Label apartNameLbl = new Label("Apartment Name: " + apartName);
		apartNameLbl.setTranslateX(3);
		apartNameLbl.setTranslateY(71);
		apartNameLbl.setFont(btnFont);
		
		int leaseLength = lease.getLeaseLengthFromLease(tenant);
		Label leaseLengthLbl = new Label("Lease Length(Years): " + leaseLength);
		leaseLengthLbl.setTranslateX(3);
		leaseLengthLbl.setTranslateY(104);
		leaseLengthLbl.setFont(btnFont);
		
		int rent = lease.getRentFromLease(tenant);
		Label rentLbl = new Label("Rent: " + rent);
		rentLbl.setTranslateX(3);
		rentLbl.setTranslateY(137);
		rentLbl.setFont(btnFont);
		
		String leaseStartDate = lease.getStartDateFromLease(tenant);
		Label leaseStartDateLbl = new Label("Lease Start Date: " + leaseStartDate);
		leaseStartDateLbl.setTranslateX(3);
		leaseStartDateLbl.setTranslateY(170);
		leaseStartDateLbl.setFont(btnFont);
		
		String leaseEndDate = lease.getEndDateFromLease(tenant);
		Label leaseEndDateLbl = new Label("Lease End Date: " + leaseEndDate);
		leaseEndDateLbl.setTranslateX(3);
		leaseEndDateLbl.setTranslateY(203);
		leaseEndDateLbl.setFont(btnFont);
		
		int landlordId = lease.getLandlordIDFromLease(tenant);
		String landlordName = lease.getLandlordNameFromLease(tenant);
		Label landlordLbl = new Label("Landlord Info) \n   Landlord ID: " + landlordId  +" \n   Landlord Name: " + landlordName);
		landlordLbl.setTranslateX(3);
		landlordLbl.setTranslateY(236);
		landlordLbl.setFont(btnFont);
		
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(335);
		
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				t.setRoot(tenantUI(t));
			}
		});
		
		Button newLeaseBtn = new Button("New Lease");
		newLeaseBtn.setTranslateX(317);
		newLeaseBtn.setTranslateY(335);
		
		newLeaseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				requests.deleteAllRequestForTenantFromDB(tenant);
				lease.deleteLeaseFromDB(tenant);
				t.setRoot(tenantMakeLease(t));
			}
		});
		
		Pane viewLeasePane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		viewLeasePane.setBackground(background);
		
		viewLeasePane.getChildren().addAll(titleLbl,line,leaseIdLbl,apartNameLbl,leaseLengthLbl,rentLbl,leaseStartDateLbl,leaseEndDateLbl,landlordLbl,backBtn,newLeaseBtn);
		return viewLeasePane;
	}
	
	public Pane chgUserEmail(Scene t) {
		t.getWindow().setHeight(200);
		
		Label titleLbl = new Label("Change Email");
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
		
		Label newLLEmailLbl = new Label("Enter New Email:");
		newLLEmailLbl.setTranslateX(3);
		newLLEmailLbl.setTranslateY(38);
		newLLEmailLbl.setFont(btnFont);
		
		TextField newLLEmailTxtF = new TextField();
		newLLEmailTxtF.setTranslateX(170);
		newLLEmailTxtF.setTranslateY(38);
		
		Label reEnterEmailLbl = new Label("Re-Enter Email: ");
		reEnterEmailLbl.setTranslateX(3);
		reEnterEmailLbl.setTranslateY(71);
		reEnterEmailLbl.setFont(btnFont);
		
		TextField reEnterEmailTxtF = new TextField();
		reEnterEmailTxtF.setTranslateX(155);
		reEnterEmailTxtF.setTranslateY(71);
		
		Text errorMsg = new Text("You Suck");
		errorMsg.setFill(Color.RED);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(115);
		errorMsg.setVisible(false);
		
		//Back button
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(134);
					
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(landlord.getEmail() != null && tenant.getEmail() == null) {
					t.setRoot(viewLandlordAcc(t));
				}else if(landlord.getEmail() == null && tenant.getEmail()!= null) {
					t.setRoot(teanantAccInfo(t));
				}
			}
		});
		
		Button enterBtn = new Button("Enter");
		enterBtn.setTranslateX(353);
		enterBtn.setTranslateY(134);
		
		enterBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String newEmail = newLLEmailTxtF.getText();
				String reEnterdNewEmail = reEnterEmailTxtF.getText();
				
				if (tenant.getEmail() == null && landlord.getEmail() != null){
					if(newEmail.matches(reEnterdNewEmail) && newEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") ) {
						landlord.updateLandlordEmail(newEmail);
						landlord.setEmail(newEmail);
						errorMsg.setText("Email has been updated.");
						errorMsg.setVisible(true);
					}else{
						errorMsg.setText("The email is invalid or does not match. Please Try Again.");
						errorMsg.setVisible(true);
					}
					
				}else if(tenant.getEmail() != null && landlord.getEmail() == null) {
					if(newEmail.matches(reEnterdNewEmail) && newEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") ) {
						tenant.updateTenantEmail(newEmail);
						tenant.setEmail(newEmail);
						errorMsg.setText("Email has been updated.");
						errorMsg.setVisible(true);
					}else{
						errorMsg.setText("The email is invalid or does not match. Please Try Again.");
						errorMsg.setVisible(true);
					}
				}
			}
		});
		
		Pane chgLandlordEmailPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		chgLandlordEmailPane.setBackground(background);
		
		chgLandlordEmailPane.getChildren().addAll(titleLbl,line,newLLEmailLbl,newLLEmailTxtF,reEnterEmailLbl,reEnterEmailTxtF,enterBtn,backBtn,errorMsg);
		return chgLandlordEmailPane;
		
	}
	
	public Pane chgUserPassword(Scene t) {
		t.getWindow().setHeight(200);
		
		Label titleLbl = new Label("Change Password");
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
		
		Label newLLPasswrdLbl = new Label("Enter New Pasword:");
		newLLPasswrdLbl.setTranslateX(3);
		newLLPasswrdLbl.setTranslateY(38);
		newLLPasswrdLbl.setFont(btnFont);
		
		TextField newLLPasswrdTxtF = new TextField();
		newLLPasswrdTxtF.setTranslateX(192);
		newLLPasswrdTxtF.setTranslateY(38);
		
		Label reEnterPasswrdLbl = new Label("Re-Enter Password: ");
		reEnterPasswrdLbl.setTranslateX(3);
		reEnterPasswrdLbl.setTranslateY(71);
		reEnterPasswrdLbl.setFont(btnFont);
		
		TextField reEnterPasswrdTxtF = new TextField();
		reEnterPasswrdTxtF.setTranslateX(185);
		reEnterPasswrdTxtF.setTranslateY(71);
		
		Text errorMsg = new Text("You Suck");
		errorMsg.setFill(Color.RED);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(115);
		errorMsg.setVisible(false);
		
		//Back button
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(134);
					
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(landlord.getPassword() != null && tenant.getPassword() == null) {
					t.setRoot(viewLandlordAcc(t));
				}else if(landlord.getPassword() == null && tenant.getPassword()!= null) {
					t.setRoot(teanantAccInfo(t));
				}
			}
		});
		
		Button enterBtn = new Button("Enter");
		enterBtn.setTranslateX(353);
		enterBtn.setTranslateY(134);
		
		enterBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String newPasswrd = newLLPasswrdTxtF.getText();
				String reEnterdNewPasswrd = reEnterPasswrdTxtF.getText();
				
				try {
					if(landlord.getPassword() != null && tenant.getPassword() == null) {
						if(newPasswrd.matches(reEnterdNewPasswrd) && newPasswrd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{7,}$")) {
							landlord.updateLandlordPasswrd(newPasswrd);
							landlord.setPassword(newPasswrd);
							errorMsg.setText("Pasword has been updated.");
							errorMsg.setVisible(true);
						}else{
							errorMsg.setText("The password is invalid or does not match. Please Try Again.");
							errorMsg.setVisible(true);
						}
						
					}else if(landlord.getPassword() == null && tenant.getPassword()!= null) {
						if(newPasswrd.matches(reEnterdNewPasswrd) && newPasswrd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{7,}$")) {
							tenant.updateTenantPasswrd(newPasswrd);
							tenant.setPassword(newPasswrd);
							errorMsg.setText("Pasword has been updated.");
							errorMsg.setVisible(true);
						}else{
							errorMsg.setText("The password is invalid or does not match. Please Try Again.");
							errorMsg.setVisible(true);
						}
						
					}
				}catch(Exception e) {
					errorMsg.setText("Password must include at least one uppercase, lowercase and special character");
					errorMsg.setVisible(true);
				}
			}
		});
		
		Pane chgUserPasswordPane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		chgUserPasswordPane.setBackground(background);
		
		chgUserPasswordPane.getChildren().addAll(titleLbl,line,newLLPasswrdLbl,newLLPasswrdTxtF,reEnterPasswrdLbl,reEnterPasswrdTxtF,enterBtn,backBtn,errorMsg);
		return chgUserPasswordPane;
		
	}
	
	public Pane chgUserPhone(Scene t) {
		t.getWindow().setHeight(200);
		
		Label titleLbl = new Label("Change Phone Number");
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
		
		Label newLLPhoneLbl = new Label("Enter New Number:");
		newLLPhoneLbl.setTranslateX(3);
		newLLPhoneLbl.setTranslateY(38);
		newLLPhoneLbl.setFont(btnFont);
		
		TextField newLLPhoneTxtF = new TextField();
		newLLPhoneTxtF.setTranslateX(192);
		newLLPhoneTxtF.setTranslateY(38);
		
		Label reEnterPhoneLbl = new Label("Re-Enter Number: ");
		reEnterPhoneLbl.setTranslateX(3);
		reEnterPhoneLbl.setTranslateY(71);
		reEnterPhoneLbl.setFont(btnFont);
		
		TextField reEnterPhoneTxtF = new TextField();
		reEnterPhoneTxtF.setTranslateX(185);
		reEnterPhoneTxtF.setTranslateY(71);
		
		Text errorMsg = new Text("You Suck");
		errorMsg.setFill(Color.RED);
		errorMsg.setTranslateX(3);
		errorMsg.setTranslateY(115);
		errorMsg.setVisible(false);
		
		//Back button
		Button backBtn = new Button("Back");
		backBtn.setTranslateX(3);
		backBtn.setTranslateY(134);
					
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(landlord.getPassword() != null && tenant.getPassword() == null) {
					t.setRoot(viewLandlordAcc(t));
				}else if(landlord.getPassword() == null && tenant.getPassword()!= null) {
					t.setRoot(teanantAccInfo(t));
				}
			}
		});
		
		Button enterBtn = new Button("Enter");
		enterBtn.setTranslateX(353);
		enterBtn.setTranslateY(134);
		
		enterBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String newPhone = newLLPhoneTxtF.getText();
				String reEnterdNewPhone = reEnterPhoneTxtF.getText();
				
				try {
					if(landlord.getPassword() != null && tenant.getPassword() == null) {
						if(newPhone.matches(reEnterdNewPhone) && newPhone.matches("^(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$")) {
							landlord.updateLandlordPhone(newPhone);
							landlord.setPhone(newPhone);
							errorMsg.setText("Phone number has been updated.");
							errorMsg.setVisible(true);
						}else{
							errorMsg.setText("The phone number is invalid or does not match. Please Try Again.");
							errorMsg.setVisible(true);
						}
					}else if(landlord.getPassword() == null && tenant.getPassword()!= null) {
						if(newPhone.matches(reEnterdNewPhone) && newPhone.matches("^(\\+?\\d{1,3}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$")) {
							tenant.updateTenantPhone(newPhone);
							tenant.setPhone(newPhone);
							errorMsg.setText("Phone number has been updated.");
							errorMsg.setVisible(true);
						}else{
							errorMsg.setText("The phone number is invalid or does not match. Please Try Again.");
							errorMsg.setVisible(true);
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Pane chgUserPhonePane = new Pane();
		
		BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		chgUserPhonePane.setBackground(background);
		
		chgUserPhonePane.getChildren().addAll(titleLbl,line,newLLPhoneLbl,newLLPhoneTxtF,reEnterPhoneLbl,reEnterPhoneTxtF,enterBtn,backBtn,errorMsg);
		return chgUserPhonePane;
		
	}
	
	//Lebron James lj23@aol.com TheGoat0623! 5051234897
	
	//Tiny Towers Chicago 
	

}
