package progetto.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class Controller {
    enum function{
        SIN,
        COS,
        TAN
    }

    @FXML
    RadioButton sin,cos,tan;

    @FXML
    Canvas canvas;

    GraphicsContext graphicContext;

    function type;

    double objectSize = 0.3; 
    double objectHeightScale = 50;

    double originPointX;
    double originPointY;

    @FXML
    Slider sliderScaleX, sliderScaleY;

    @FXML
    ToggleGroup group;

    @FXML
    private void initialize(){
        graphicContext = canvas.getGraphicsContext2D();
        sliderScaleX.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                if(sliderScaleX.getValue() < 1) sliderScaleX.setValue(1);
                objectSize = sliderScaleX.getValue() / 20;
                if (objectSize < 0.3) objectSize = 0.3;
                repaint();
            }
            
        });
        sliderScaleY.setValue(5);
        sliderScaleY.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                objectHeightScale = sliderScaleY.getValue() * 10;
                if(objectHeightScale > 900) objectHeightScale = 900;
                if(objectHeightScale < 10) objectHeightScale = 10;
                repaint();
            }
            
        });
        repaint();
    }

    private void drawPlane(){
        graphicContext.setStroke(Color.WHITE);
        graphicContext.setLineWidth(1);
        if(type == function.SIN || type == function.COS){
            graphicContext.strokeLine(canvas.getWidth()/6,(double)0,canvas.getWidth()/6,canvas.getHeight());
            graphicContext.strokeLine((double)0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
        }else if(type == function.TAN){
            graphicContext.strokeLine(canvas.getWidth()/2,(double)0,canvas.getWidth()/2,canvas.getHeight());
            graphicContext.strokeLine((double)0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
        }
        
        if(type != null){
            for(int i = 1 ; i < canvas.getHeight()/objectHeightScale; i++){
                graphicContext.strokeLine(originPointX - 5,originPointY - objectSize - objectHeightScale*i,originPointX + 5, originPointY - objectSize - objectHeightScale*i);
                graphicContext.strokeLine(originPointX - 5,originPointY - objectSize + objectHeightScale*i,originPointX + 5, originPointY - objectSize + objectHeightScale*i);
            }
            for(int i = 1; i < canvas.getWidth()/objectSize; i++){
                graphicContext.strokeLine(originPointX + 90*objectSize * i,originPointY - 5,originPointX + 90*objectSize * i,originPointY + 5);
                graphicContext.strokeLine(originPointX - 90*objectSize * i,originPointY - 5,originPointX - 90*objectSize * i,originPointY + 5);
            }
        }
    }

    @FXML
    private void drawSin(){
        type = function.SIN;
        originPointX = canvas.getWidth()/6;
        originPointY = canvas.getHeight()/2;
        repaint();
    }

    @FXML
    private void drawCos(){
        type = function.COS;
        originPointX = canvas.getWidth()/6;
        originPointY = canvas.getHeight()/2;
        repaint();
    }

    @FXML
    private void drawTan(){
        type = function.TAN;
        originPointX = canvas.getWidth()/2;
        originPointY = canvas.getHeight()/2;
        repaint();
    }

    private void repaint(){
        clearView();
        drawPlane();
        if(type != null)
            switch(type){
                case SIN:{
                    double lastPoint = 0;
                    while(true){
                        for(int i = -360; i <= 360; i++){
                            graphicContext.setFill(Color.WHITE);
                            if(i >= 0)  graphicContext.fillRect(originPointX + i*objectSize + lastPoint,originPointY - objectSize - Math.sin(Math.toRadians(i))*objectHeightScale,objectSize,objectSize);
                            else graphicContext.fillRect(originPointX + i*objectSize,originPointY - objectSize - Math.sin(Math.toRadians(i))*objectHeightScale,objectSize,objectSize);
                        }
                        lastPoint += 360*objectSize;
                        if(lastPoint >= canvas.getWidth())  break;
                    }
                    break;
                }
                case COS:{
                    double lastPoint = 0;
                    while(true){
                        for(int i = -360; i <= 360; i++){
                            graphicContext.setFill(Color.WHITE);
                            if(i >= 0)  graphicContext.fillRect(originPointX + i*objectSize + lastPoint,originPointY - objectSize - Math.cos(Math.toRadians(i))*objectHeightScale,objectSize,objectSize);
                            else graphicContext.fillRect(originPointX + i*objectSize,originPointY - objectSize - Math.cos(Math.toRadians(i))*objectHeightScale,objectSize,objectSize);
                        }
                        lastPoint += 360*objectSize;
                        if(lastPoint >= canvas.getWidth())  break;
                    }
                    break;
                }
                case TAN:{
                    int lastPoint = 0;
                    while(true){
                        for(int i = -1000; i <= 360; i++){
                            graphicContext.setFill(Color.WHITE);
                            if(i >= 0)  graphicContext.fillRect(originPointX + i*objectSize + lastPoint,originPointY - objectSize - Math.tan(Math.toRadians(i))*objectHeightScale,objectSize,objectSize);
                            else graphicContext.fillRect(originPointX + i*objectSize,originPointY - objectSize - Math.tan(Math.toRadians(i))*objectHeightScale,objectSize,objectSize);
                        }
                        lastPoint += 360*objectSize;
                        if(lastPoint >= canvas.getWidth())  break;
                    }
                    break;
                }
                default: break;
            }
    }

    private void clearView(){
        graphicContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicContext.setFill(Color.BLACK);
        graphicContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void clear(){
        sin.setSelected(false);
        cos.setSelected(false);
        tan.setSelected(false);
        clearView();
    }

    
}
