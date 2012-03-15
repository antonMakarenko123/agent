/**
 * @author w495
 * Описание класса цели.
 * Это его описание как боевой единицы.
 * Система обнаружения цели описана
 * в agents.TargetAgent
 */

package objects;

import java.util.Random;
import util2.Coord;

public class Target {
    static int number = 0;
    static String names[] = {"Tank", "Rocket", "Ack-ack"};
    // виды техники

    int num;                // номер
    private Coord coord;    // координты цели
    private String name;    // имя цели

    private Random generator;
    /**
     * Создает объект.
     * Присваивает ему уникальный номер.
     * Случайным образом задает тип.
     * Случайным образом задает координаты.
     */
    public Target(){
        Target.number += 1;
        this.num = Target.number;
        this.generator = new Random();
        this.name = new String( Target.names[this.generator.nextInt( Target.names.length )]) ;
        this.coord = new Coord();
        this.say_about();
    }
    /**
     * Возвращает координты  цели.
     */
    public Coord getCoord(){
        return this.coord;
    }
    /**
     * Возвращает координты цели в виде строки.
     */
    public String getCoordString(){
        return this.coord.toString();
    }
    /*
     * Возвращает тип цели.
     */
    public String getName(){
        return this.name;
    }
    /**
     * Печатает сообщение от имени этой цели
     * @param s сообщение
     */
    public void say(String s){
        System.out.println("Target("+ this.num +"): "  + ": (" + s + ");");
    }
    /**
     * Печатает сообщение о запуске цели и ее кординатах
     */
    public void say_about(){
        System.out.println("Target("+ this.num +"): " + this.name  + ": (" + this.coord.toString()+");");
    }
}