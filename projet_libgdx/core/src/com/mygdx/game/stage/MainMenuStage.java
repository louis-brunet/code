package com.mygdx.game.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.utils.Assets;

public class MainMenuStage extends Stage {
    public MainMenuStage() {
        super(new ScreenViewport());

        this.initTable();
    }

    private void initTable() {
        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        this.addActor(table);

        Label title = new Label("Zero to Hero",
                new Label.LabelStyle(Assets.getLargeFont(), Color.WHITE));
        table.add(title);

        table.row();

        Label instructions = new Label("Click anywhere to begin",
                new Label.LabelStyle(Assets.getSmallFont(), Color.WHITE));
        table.add(instructions);
    }
}
