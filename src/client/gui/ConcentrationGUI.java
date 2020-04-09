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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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
    private final static int LABEL_FONT_SIZE = 8;
    /** number of columns */
    private int COLS;
    /** number of rows */
    private int ROWS;
    /** 2d array of PokemonButton*/
    private PokemonButton[][] board;
    /** label that has the number of moves in the game*/
    private Label moves;
    /** label that has the number of matches in the game*/
    private Label matches;
    /**label that states the status of the game*/
    private Label status;


    /** HashMap with string and Image */
    HashMap<String, Image> pics = new HashMap<String, Image>();
    private Image abra = new Image(getClass().getResourceAsStream("images/abra.png"));
    private Image bulbasaur = new Image(getClass().getResourceAsStream("images/bulbasaur.png"));
    private Image charizard = new Image(getClass().getResourceAsStream("images/charizard.png"));
    private Image diglett = new Image(getClass().getResourceAsStream("images/diglett.png"));
    private Image golbat = new Image(getClass().getResourceAsStream("images/golbat.png"));
    private Image golem = new Image(getClass().getResourceAsStream("images/golem.png"));
    private Image jigglypuff = new Image(getClass().getResourceAsStream("images/jigglypuff.png"));
    private Image magikarp = new Image(getClass().getResourceAsStream("images/magikarp.png"));
    private Image meowth = new Image(getClass().getResourceAsStream("images/meowth.png"));
    private Image mewtwo = new Image(getClass().getResourceAsStream("images/mewtwo.png"));
    private Image natu = new Image(getClass().getResourceAsStream("images/natu.png"));
    private Image pidgey = new Image(getClass().getResourceAsStream("images/pidgey.png"));
    private Image pikachu = new Image(getClass().getResourceAsStream("images/pikachu.png"));
    private Image pokeball = new Image(getClass().getResourceAsStream("images/pokeball.png"));
    private Image poliwag = new Image(getClass().getResourceAsStream("images/poliwag.png"));
    private Image psyduck = new Image(getClass().getResourceAsStream("images/psyduck.png"));
    private Image rattata = new Image(getClass().getResourceAsStream("images/rattata.png"));
    private Image slowpoke = new Image(getClass().getResourceAsStream("images/slowpoke.png"));
    private Image snorlak = new Image(getClass().getResourceAsStream("images/snorlak.png"));
    private Image squirtle = new Image(getClass().getResourceAsStream("images/squirtle.png"));
    public ConcentrationGUI(){
        this.pics.put(".", pokeball);
        this.pics.put("A", abra);
        this.pics.put("B", bulbasaur);
        this.pics.put("C", charizard);
        this.pics.put("D", diglett);
        this.pics.put("E", golbat);
        this.pics.put("F", golem);
        this.pics.put("G", jigglypuff);
        this.pics.put("H", magikarp);
        this.pics.put("I", meowth);
        this.pics.put("J", mewtwo);
        this.pics.put("K", natu);
        this.pics.put("L", pidgey);
        this.pics.put("M", pikachu);
        this.pics.put("N", poliwag);
        this.pics.put("O", psyduck);
        this.pics.put("P", rattata);
        this.pics.put("Q", slowpoke);
        this.pics.put("R", snorlak);
        this.pics.put("S", squirtle);
    }

    HashMap<Character,String> CharImageMap = new HashMap<>();

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
            //ROWS = this.model.getDIM();
            //COLS = this.model.getDIM();
            //this.board = new PokemonButton[ROWS][COLS];

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


    @Override
    public void start(Stage stage) throws Exception {

        // create the label for moves
        this.moves = new Label("Moves");
        //create the label
        this.matches = new Label("Matches");
        //create status label
        this.status = new Label("OK");
        //form bottom label using hbox
        HBox hBox = new HBox(this.moves,this.matches,this.status);
        // get the grid pane from the helper method
        GridPane gridPane = makeGridPane();

        // put gridPane and hbox in vbox for format
        VBox vbox = new VBox(gridPane,hBox);
        // store the vbox into the scene and display it
        Scene scene = new Scene(vbox);
        stage.setTitle("Concentration GUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    @Override
    public void update(ConcentrationModel model, ConcentrationModel.CardUpdate card) {
       if (card != null){
            Platform.runLater(() -> refresh(card));
            Platform.runLater(() -> this.moves.setText("Moves: " + this.model.getNumMoves()));
        } else{
            Platform.runLater(() -> this.matches.setText("Matches: " + this.model.getNumMatches()));
            if(model.getStatus() != ConcentrationModel.Status.OK) {
                Platform.runLater(() -> this.status.setText("GAMEOVER"));
            }
        }

    }

    private void refresh(ConcentrationModel.CardUpdate card) {
        //int col = card.getCol();
        //int row = card.getRow();
        //button = board[row][col];//Add method to pokemon button class to show or hide
        String input = card.getVal();
        Image image = this.pics.get(input);
        PokemonButton button = new PokemonButton(image);
        this.gridPane.add(button, card.getCol(), card.getRow());
        button.setOnAction(null);
        if (!card.isRevealed()) {
            button.setOnAction(event -> this.controller.revealCard(card.getRow(), card.getCol()));
        }
        if (model.getStatus() == ConcentrationModel.Status.GAME_OVER) {
            this.stop();
        }
        if (model.getStatus() == ConcentrationModel.Status.ERROR) {
            this.stop();
        }
    }


    @Override
    public void stop() {
        this.controller.close();
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
