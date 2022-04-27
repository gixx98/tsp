package com.example.optimalizalas_alkalmazasai.Graph;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.ArrayList;

public class Node extends ShapeDrawable {
    private ArrayList<Link> links;

    float posX, posY;
    private int numOfLinks;
    public int radius;
    private int defaultRadius;
    private int id;
    private final int DPS = 25;

    int viewportWidth = 360, viewportHeight = 760;

    public float getPosX(){
        return posX;
    }

    public float getPosY(){
        return posY;
    }

    private int color;

    public Node(){
        id = -1;
        numOfLinks = 0;
        links = new ArrayList<Link>();
        color = Color.BLACK;
    }

    public Node(int x, int y, int id, float density) {
        super(new OvalShape());
        if (!(((int) (DPS * density + 0.5f) * viewportWidth * viewportHeight / 787184) == 0)) {
            radius = ((int) (DPS * density + 0.5f) * viewportWidth
                    * viewportHeight / 787184);
        } else
            radius = ((int) (DPS * density + 0.5f) * viewportWidth / 700);
        defaultRadius = radius;
        color = Color.BLACK;
        getPaint().setColor(color);
        getPaint().setAntiAlias(true);
        setPos(x, y, viewportWidth, viewportHeight);
        numOfLinks = 0;
        links = new ArrayList<Link>();
        this.id = id;
    }

    public Node(float posX, float posY, int id, float density) {
        super(new OvalShape());
        if (!(((int) (DPS * density + 0.5f) * viewportWidth * viewportHeight / 787184) == 0)) {
            radius = ((int) (DPS * density + 0.5f) * viewportWidth
                    * viewportHeight / 787184);
        } else
            radius = ((int) (DPS * density + 0.5f) * viewportWidth / 700);

        defaultRadius = radius;

        color = Color.BLACK;
        getPaint().setColor(color);
        getPaint().setAntiAlias(true);
        setPosF(posX, posY, viewportWidth, viewportHeight);
        numOfLinks = 0;
        links = new ArrayList<Link>();
        this.id = id;

    }


    public void initNode(int id_) {
        id = id_;
        numOfLinks = 0;
        links = new ArrayList<Link>();
        color = Color.BLACK;
    }

    public void setPos(int x, int y, float viewportWidth, float viewportHeight) {
        posX = x / viewportWidth;
        posY = y / viewportHeight;
        setBounds(x - radius, y - radius, x + radius, y + radius);
        posX = x;
        posY = y;
    }

    public void setPosF(float posX, float posY, int viewportWidth,
                        int viewportHeight) {
        this.posX = posX;
        this.posY = posY;
        setBounds(Math.round(posX * viewportWidth) - radius,
                Math.round(posY * viewportHeight) - radius,
                Math.round(posX * viewportWidth) + radius,
                Math.round(posY * viewportHeight) + radius);
    }


    public int getCenterX() {
        return getBounds().centerX();
    }

    public int getCenterY() {
        return getBounds().centerY();
    }

    public int getId() {
        return id;
    }

    public void String(int id) {
        this.id = id;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public int getNumOfLinks() {
        return numOfLinks;
    }

    public void setNumOfLinks(int numOfLinks) {
        this.numOfLinks = numOfLinks;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        getPaint().setColor(color);
    }

    public void addLink(int idf, int weight) {
        links.add(new Link(idf, weight));
        numOfLinks++;

    }

    public void removeLink(int idf) {
        for (int i = 0; i < links.size(); i++) {
            if (links.get(i).getIdf() == idf) {
                links.remove(i);
                numOfLinks--;
                i = 0;
            }
        }
    }
}
