package com.serhatmerak.cozveoyna.helpers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Serhat Merak on 25.02.2018.
 */

public class ButtonHoverAnimation extends ClickListener {
    private ImageButton button;

    public ButtonHoverAnimation(ImageButton button){
        this.button = button;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        button.addAction(Actions.repeat(3,Actions.scaleBy(0.1f,0.1f)));
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        button.addAction(Actions.repeat(3,Actions.scaleBy(-0.1f,-0.1f)));
    }
}
