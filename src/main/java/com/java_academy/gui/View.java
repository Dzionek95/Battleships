package com.java_academy.gui;

import com.java_academy.logic.tools.I18NResolver;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class View {

    public void drawShips(Pane pane){
        pane.setStyle("-fx-background-color: black");
    }

    public void drawMiss(Pane pane){
    	pane.setStyle("-fx-background-color: cornflowerblue");
    }

    public void drawShot(Pane pane){
        pane.setStyle("-fx-background-color: red");
    }
    public void setLabelText(String s, Label l){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                l.setText(I18NResolver.getMsgByKey(s));
            }
        });
    }

}
