package sample;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;



public class Main extends Application {
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };
    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.setTitle("Lab 07");

        Group root = new Group();
        Canvas canvas = new Canvas(600,300);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();


        String inFile="weatherwarnings-2015.csv";
        String delimiter=",";
        BufferedReader br=new BufferedReader(new FileReader(inFile));
        List<String> lineList=br.lines().map(Object::toString).collect(Collectors.toList());
        br.close();
        Map<String, Integer> warnings=new TreeMap<>();
        for(String line:lineList){
            String[] data=line.split(delimiter);
            String warn=data[5];
            if(warnings.containsKey(warn)){
                int amount=warnings.get(warn);
                warnings.put(warn,amount+1);
            }else{
                warnings.put(warn,1);
            }
        }
        double total=getSum(warnings);

        Set<String> keys=warnings.keySet();
        Iterator<String>keyIterator=keys.iterator();
        double startpoint=0;
        int count=0;
        gc.setStroke(Color.BLACK);
        while(keyIterator.hasNext()){
            gc.setFill(pieColours[count]);
            count++;
            String warn=keyIterator.next();
            double width=360*warnings.get(warn)/total;
            gc.fillArc(325,25,250,250, startpoint,width,ArcType.ROUND);
            gc.strokeArc(325,25,250,250, startpoint,width,ArcType.ROUND);
            gc.fillRect(20,40+30*count,30,20);
            gc.strokeRect(20,40+30*count,30,20);
            gc.setFill(Color.BLACK);
            gc.fillText(warn,70,55+30*count);


            startpoint+=width;

        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public int getSum(Map<String,Integer> warnings){
        int sum=0;
        Set<String> keys=warnings.keySet();
        Iterator<String>keyIterator=keys.iterator();
        while(keyIterator.hasNext()){
            String warn=keyIterator.next();
            sum+=warnings.get(warn);
        }
        return sum;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
