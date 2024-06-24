import jogamp.newt.Debug;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    ArrayList<PImage> img;
    HashMap<String,Boolean> pressed;
    HashMap<String,Pair<PImage,Pos>> hm;
    CopyOnWriteArrayList<Triple<PImage,Pos,Integer>> list;
    int timer;
    float delay;

    public void settings() {
        fullScreen();
        pressed = new HashMap<>();
        hm = new HashMap<>();
        list = new CopyOnWriteArrayList<>();
        img = new ArrayList<PImage>(45);
        for (int i = 0 ; i < 45 ; i++) {
            img.add(loadImage("Resources/pinceladas/"+(i+1)+".png"));
            img.get(i).resize(round(img.get(i).width / 12.0f), round(img.get(i).height / 12.0f));
            println(i);
        }
        timer = millis();
        delay = 0.1f;
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
                continue;
            }
            tint(255,alpha);
            image(triple.first, triple.second.x, triple.second.y);
        }
    }

    public void keyPressed()
    {
        if ((millis() - timer)/1000.0f < delay) {
            return;
        }
        else {
            timer = millis();
            println("aaa");
        }
        int n = 1;
        if (key >= 'a' && key <= 'f') {
            n = floor(random(1,10));
        }
        else if (key >= 'g' && key <= 'l') {
            n = floor(random(11,20));
        }
        else if (key >= 'm' && key <= 't') {
            n = floor(random(21, 30));
        }
        else if (key >= 'u' && key <= 'z') {
            n = floor(random(31, 45));
        }
        else {
            return;
        }
        pressed.putIfAbsent(str(key), false);
        if (!pressed.get(str(key))) {
            pressed.put(str(key),true);
            hm.put(str(key), new Pair<>(img.get(n), new Pos(random(width-100), random(height-100))));
        }
    }

    public void keyReleased()
    {
        if (pressed.containsKey(str(key)) && pressed.get(str(key))) {
            pressed.put(str(key),false);
            Pair<PImage,Pos> pair = hm.get(str(key));
            list.add(new Triple<>(pair.first, pair.second, millis()));
            hm.remove(str(key));
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}