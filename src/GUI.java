import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;


import java.util.ArrayList;






public class GUI extends Application {
@Override
	public void start(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		pane.setTop(new CustomPane("Top"));
		pane.setRight(new CustomPane("Right"));
		pane.setBottom(new CustomPane("Bottom"));
		pane.setLeft(new CustomPane("Left"));
		
		pane.setCenter(new CheckerBoard());
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene); primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}


class CheckerBoard extends GridPane{
	DataStructure ds;
	public ArrayList<Button> list = new ArrayList<Button>();
	Image  blackOnBlack = new Image("/bOnB.png");
	Image  blackOnWhite = new Image("/bOnW.png");
	Image  whiteOnBlack = new Image("/wOnB.png");
	Image  whiteOnWhite = new Image("/wOnW.png");
	ImageView blackOnBlack_v = new ImageView(blackOnBlack);
	ImageView blackOnWhite_v = new ImageView(blackOnWhite);
	ImageView whiteOnBlack_v = new ImageView(whiteOnBlack);
	ImageView whiteOnWhite_v = new ImageView(whiteOnWhite);
	
	public CheckerBoard() {
		//create the checkers 
		int count = 0;
	    double s = 80; // side of rectangle
	    for (int i = 0; i < 8; i++) {
	    count++;
	      for (int j = 0; j < 8; j++) {
	    	Button c = new Button();
	    	c.setMinSize(s,s);
	        if (count % 2 == 0)
	          c.setStyle("-fx-background-color: #FFFFFF; ");

	        else
	          c.setStyle("-fx-background-color: #000000; ");
	        this.add(c, j, i);
	        list.add(c);
	        count++;
	      }
	    }
	    //
	    ds = new DataStructure();
	    this.updateBoard();
	    ds.printBoard();
	}
	
	
	public void updateBoard() {
		int x = 0;
		int y = 0;
		
		for(Button node: this.list) {
			
			int state=(this.ds.getSquareState(x,y));
			System.out.println(state);
			if(state == 1) { //state is black
				if((x+y)%2==0)
					node.setGraphic(blackOnBlack_v);
				else
					node.setGraphic(blackOnWhite_v);
			}
			else if(state == 2){//state is white
				if((x+y)%2==0)
					node.setGraphic(whiteOnBlack_v);
				else
					node.setGraphic(whiteOnWhite_v);
			}
			y++;
			if(y==8) {
				x++;
				y=0;
			}
		}
	}
	
}	





class CustomPane extends StackPane {
	public CustomPane(String title) {
		getChildren().add(new Label(title));
		setStyle("-fx-border-color: red");
		setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
	}
}