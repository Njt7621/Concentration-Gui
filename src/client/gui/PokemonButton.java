package client.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * 
 *
 * @author RIT CS
 */
public class PokemonButton extends Button {

    /** the four types of pokemon we have */
        public enum Pokemon {
            BULBASAUR,
            GOLEM,
            PIKACHU,
            SNORLAX
        }

        /** bulbasaur image */
        private Image bulbasaur = new Image(getClass().getResourceAsStream(
                "images/bulbasaur.png"));
        /** charmander image */
        private Image golem = new Image(getClass().getResourceAsStream(
                "images/golem.png"));
        /** pikachu image */
        private Image pikachu = new Image(getClass().getResourceAsStream(
                "images/pikachu.png"));
        /** snorlax image */
        private Image snorlax = new Image(getClass().getResourceAsStream(
                "images/snorlak.png"));

        /** a definition of white for the button background */
        private static final Background WHITE =
                new Background( new BackgroundFill(Color.WHITE, null, null));

        /** the type of this pokemon */
        private Pokemon pokemon;

        private Image pokeball = new Image(getClass().getResourceAsStream(
            "images/pokeball.png"));


        public PokemonButton() {
            this.pokemon = pokemon;
            Image image = pokeball;

            // set the graphic on the button and make the background white
            this.setGraphic(new ImageView(image));
            this.setBackground(WHITE);
            }
        /**
         * Get the pokemon type.
         *
         * @return this pokemon's type
         */
        public Pokemon getType() {
            return this.pokemon;
        }
}
