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
        private enum Pokemon {
            BULBASAUR,
            CHARMANDER,
            PIKACHU,
            SNORLAX
        }

        /** bulbasaur image */
        private Image bulbasaur = new Image(getClass().getResourceAsStream(
                "bulbasaur.png"));
        /** charmander image */
        private Image charmander = new Image(getClass().getResourceAsStream(
                "charmander.png"));
        /** pikachu image */
        private Image pikachu = new Image(getClass().getResourceAsStream(
                "pikachu.png"));
        /** snorlax image */
        private Image snorlax = new Image(getClass().getResourceAsStream(
                "snorlax.png"));

        /** a definition of white for the button background */
        private static final Background WHITE =
                new Background( new BackgroundFill(Color.WHITE, null, null));

        /** the type of this pokemon */
        private Pokemon pokemon;


        public PokemonButton() {
            this.pokemon = pokemon;
    }
        /**
         * Create the button with the image based on the pokemon.
         *
         * @param pokemon the pokemon
         */
        public PokemonButton(Pokemon pokemon) {
            this.pokemon = pokemon;
            Image image;
            switch (pokemon) {
                case BULBASAUR:
                    image = bulbasaur;
                    break;
                case CHARMANDER:
                    image = charmander;
                    break;
                case PIKACHU:
                    image = pikachu;
                    break;
                case SNORLAX:
                default:
                    image = snorlax;
            }

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
    