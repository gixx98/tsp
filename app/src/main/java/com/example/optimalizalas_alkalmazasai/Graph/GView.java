package com.example.optimalizalas_alkalmazasai.Graph;

import static java.lang.System.exit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class GView extends View {

    private Graph g;

    private Paint paint;
    private Paint fontPaint;
    private Path path;
    Node secondNode, thirdNode, fourthNode, fifthNode, sixthNode;
    ArrayList<Node> travelled;
    Node n1,n2,n3,n4,n5,n6;
    int count = 0;

    private static final String[] label = { "Kredit", "Vár söröző", "Campus", "Bárka", "Blues", "Tibi atya" };

    public GView(Context context) {
        super(context);
        init();
    }

    public GView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public GView(Context context, AttributeSet attrs, int params) {
        super(context, attrs, params);

        init();
    }

    private void init() {
        g = new Graph();

        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);

        fontPaint = new Paint();
        fontPaint.setTextAlign(Paint.Align.CENTER);
        fontPaint.setTextSize(26);

        n1 = new Node(200,750,0,3);
        n2 = new Node(800,850,1,3);
        n3 = new Node(250,250,2,3);
        n4 = new Node(900,300,3,3);
        n5 = new Node(500,800,4,3);
        n6 = new Node(600,500,5,3);

        travelled = new ArrayList<>();
        travelled.add(n1);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(count == 0){
            secondNode = calculateShortest(n1);
            count++;
        }
        else if(count == 1){
            thirdNode = calculateShortest(secondNode);
            count++;
        }else if(count == 2){
            fourthNode = calculateShortest(thirdNode);
            count++;
        }
        else if(count == 3){
            fifthNode = calculateShortest(fourthNode);
            count++;
        }
        else if(count == 4){
            sixthNode = calculateShortest(fifthNode);
            count++;
        }else if(count == 5){
            addArrow(sixthNode, n1);
            count++;
        }

        invalidate();
        return super.onTouchEvent(event);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        for (int ns : g.getNames()) {
            Node n = g.getVertex().get(ns);

            if(n.getId() == 0){
                n.setColor(Color.RED);
            }else{
                n.setColor(Color.BLACK);
            }
            n.draw(canvas);
            canvas.drawText(label[n.getId()], n.getCenterX(), n.getCenterY()
                    - n.radius - 20, fontPaint);
        }

        for (int i = 0; i < g.getArrows().size(); i++) {
            Arrow a = g.getArrows().get(i);

            paint.setColor(g.getArrows().get(i).color);
            canvas.drawLine(a.start[0], a.start[1], a.stop[0], a.stop[1], paint);
        }

    }

    public Node calculateShortest(Node startingNode){
        //ellenőrizni kell, hogy benne van már -e a láncban és van vmi gecis hiba
        System.out.println("calculate shortest starting node id: " + startingNode.getId());
        Hashtable<Double, Node> distances = new Hashtable<>();
        Node n;

        System.out.println("SIZE: " + g.getVertex().size());
        System.out.println("TRAVELLED ARRAYLIST: " + travelled.toString());
        for(int i = 0; i < g.getNames().size() ; i++){
                if(startingNode.getId() != g.getVertex().get(i).getId() && !travelled.contains(g.getVertex().get(i))){
                    n = g.getVertex().get(i);
                    float a = startingNode.getPosX() - n.getPosX();
                    float b = startingNode.getPosY() - n.getPosY();

                    double c = Math.hypot(a,b);
                    distances.put(c, n);
                }
            System.out.println(i + ". node - " + g.getVertex().get(i));
        }

        //the question is how to get the NODE
        // we have to calculate all node from this
        //addArrow(startingNode, n);
        Enumeration<Double> e = distances.keys();
        double min = 521521521.00;
        while(e.hasMoreElements()){
            double key = e.nextElement();
            if(key < min ){
                min = key;
            }
            System.out.println("Value " + key + " Node:" + distances.get(key));
        }

        n = distances.get(min);
        System.out.println("Node id: " + n.getId());
        System.out.println("Starting node id: " + startingNode.getId());
        addArrow(startingNode, n);
        travelled.add(n);
        return n;
    }

    public void deleteNode(Node n) {
        if (n != null) {
            g.deleteNode(n.getId());
        }
    }

    public void addArrow(Node n1, Node n2) {
        System.out.println("add row starts");
        if (n1 != null && n2 != null) {
            System.out.println("n1 id: " + n1.getId() + ", n2 id: " + n2.getId());
            g.addLink(n1.getId(), n2.getId(), Math.round(n1.getPosX()), Math.round(n1.getPosY()), Math.round(n2.getPosX()), Math.round(n2.getPosY()), 20);
        }
    }

    public void deleteArrow(Node n1, Node n2) {
        g.deleteLink(n1.getId(), n2.getId());
    }
}
