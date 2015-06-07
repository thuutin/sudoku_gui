package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by tin on 6/8/15.
 */
public class NumberLabel extends Label {

    public NumberLabel(CharSequence text, Skin skin, String font) {
        super(text, skin, font);
        if(!text.equals("0")){
            setColor(Color.GREEN);
        }
        addListener(new EventListener(){
            @Override
            public boolean handle(Event event) {
                long id = Thread.currentThread().getId();
                if(event instanceof OnNumberChange){
                    OnNumberChange e = (OnNumberChange) event;
                    setText(e.getText());
                    return true;
                }
                return false;
            }
        });

    }



    public static class OnNumberChange extends Event {
        private String text;

        public OnNumberChange(String s){
            this.text = s;
        }

        public String getText(){
            return text;
        }
    }
}
