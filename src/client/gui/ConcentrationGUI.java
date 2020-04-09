package client.gui;

import client.controller.ConcentrationController;
import client.model.ConcentrationModel;
import client.model.Observer;

import common.ConcentrationException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

/**
 * A JavaFX GUI client for the network concentration game.  It represent the "View"
 * component of the MVC architecture in use here.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class ConcentrationGUI extends Application implements Observer<ConcentrationModel, ConcentrationModel.CardUpdate> {
    /** the model */
    private ConcentrationModel model;
    /** the controller */
    private ConcentrationController controller;
    /** the label with the name of the last pokemon selected */
    private Label label;
    /** the font size of the label */
    private final static int LABEL_FONT_SIZE = 40;
    /** number of columns */
    private int COLS;
    /** number of rows */
    private int ROWS;
    /** 2d array of PokemonButton*/
    private PokemonButton[][] board;

    private Label moves;
    private Label matches;
    private Label status;

    @Override
    public void init() throws ConcentrationException {
        List<String> args = getParameters().getRaw();
        try{
            // get host and port from command line
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            // create the model, and add ourselves as an observer
            this.model = new ConcentrationModel();
            this.model.addObserver(this);
            // initiate the controller
            this.controller = new ConcentrationController(host, port, this.model);
            ROWS = this.model.getDIM();
            COLS = this.model.getDIM();
            this.board = new PokemonButton[ROWS][COLS];

        } catch (ConcentrationException |
            ArrayIndexOutOfBoundsException |
            NumberFormatException e) {
          throw new ConcentrationException(e);
        }
    }

    /**
     * A helper function that builds a grid of buttons to return.
     *
     * @return the grid pane
     */
    private GridPane makeGridPane(){
        GridPane gridPane = new GridPane();

        PokemonButton.Pokemon pokemon[] = PokemonButton.Pokemon.values();
        int i = 0;
        for (int row=0; row<ROWS; ++row) {
            for (int col=0; col<COLS; ++col) {
                // get the next type of pokemon and create a button for it
                PokemonButton button = new PokemonButton();

                // reveal the card when pressed
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(event ->
                        this.controller.revealCard(finalRow,finalCol));

                // JavaFX uses (x, y) pixel coordinates instead of
                // (row, col), so must invert when adding
                gridPane.add(button, col, row);
                board[row][col] = button;
            }
        }

        return gridPane;
    }

    /**
     * A helper function that builds a flow pane at the bottom with
     * 3 labels of buttons to return.
     *
     * @return the grid pane
     */
    private FlowPane makeFlowPane(Label){
        FlowPane FlowPane = new FlowPane();
        }

    @Override
    public void start(Stage stage) throws Exception {
        // create the border pane that holds the grid and label
        BorderPane borderPane = new BorderPane();

        // create the label that will update
        this.label = new Label("UNKNOWN");
        this.label.setStyle("-fx-font: " + LABEL_FONT_SIZE + " arial;");
        borderPane.setBottom(this.label);
        BorderPane.setAlignment(this.label, Pos.CENTER);



        // get the grid pane from the helper method
        GridPane gridPane = makeGridPane();
        //get the flowpane from the helper method
        FlowPane flowPane = makeFlowPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(flowPane);

        // store the grid into the scene and display it
        Scene scene = new Scene(borderPane);
        stage.setTitle("Concentration GUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    @Override
    public void update(ConcentrationModel model, ConcentrationModel.CardUpdate card) {
        Platform.runLater(() -> refresh(card));
        Platform.runLater(() -> this.moves.setText("Moves: " + this.model.getNumMoves()));
        if (card = null){
            Platform.runLater(() -> this.matches.setText("Matches: " + this.model.getNumMatches()));
        }
        Platform.runLater(() -> refresh(status)); //status of game
    }

    private void refresh(ConcentrationModel.CardUpdate card) {
        int col = card.getCol();
        int row = card.getRow();
        board[row][col] = null; //Add method to pokemon button class to show or hide

    }


    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ConcentrationGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
