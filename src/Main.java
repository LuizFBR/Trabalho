import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashMap;

class Pair <V,E> {
    public V first;
    public E second;
    public Pair(V first, E second)
    {
        this.first = first;
        this.second = second;
    }
}

class Pos {
    float x,y;
    public Pos(float x, float y) {this.x=x;this.y=y;}
}

class Triple <V,E,M> {
    public V first;
    public E second;
    public M third;
    public Triple(V first, E second, M third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}

public class Main extends PApplet {
    PImage img;
    HashMap<String,Boolean> pressed;
    HashMap<String,Pair<PImage,Pos>> hm;
    ArrayList<Triple<PImage,Pos,Integer>> list;

    public void settings() {
        size(1600,1600);
        img = loadImage("Resources/laurinha.png");
        img.resize(round(img.width/10.0f),round(img.height/10.0f));
        pressed = new HashMap<>();
        hm = new HashMap<>();
        list = new ArrayList<>();
    }

    public void setup() {

    }

    public void draw() {
        background(255);
        tint(255,255);
        for (Pair<PImage,Pos> pair : hm.values()) {
            image(pair.first, pair.second.x, pair.second.y);
        }
        for (Triple<PImage,Pos,Integer> triple : list) {
            int alpha = round(255*(1 - (millis()-triple.third)/10000.0f));
            if(alpha < 0) {
                list.remove(triple);
                break;
            }
            println(alpha);
            tint(255,alpha);
            image(triple.first, triple.second.x, triple.second.y);
        }
    }

    public void keyPressed()
    {
        pressed.putIfAbsent(str(key), false);
        if (!pressed.get(str(key))) {
            pressed.put(str(key),true);
            hm.put(str(key), new Pair<>(img, new Pos(random(width), random(height))));
        }
    }

    public void keyReleased()
    {
        if (pressed.get(str(key))) {
            pressed.put(str(key),false);
            Pair<PImage, Pos> pair = hm.get(str(key));
            list.add(new Triple<>(pair.first, pair.second, millis()));
            hm.remove(str(key));
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}