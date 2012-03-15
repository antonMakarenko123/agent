/**
 * @author w495
 * Описание класса бомбардировщика.
 * Это его описание как боевой единицы.
 * Система обнаружения цели описана
 * в agents.BomberAgent
 */

package objects;

import java.util.Random;
import util2.Coord;


public class Bomber  {
    static int number = 0;
    static String names[] = {"T-50", "S-37", "MIG-31", "SU-24", "SU-27"};
        // виды техники
    int num = 0;                // бортовой номер
    private String name;        // модель машины
    private Random generator;
    public Coord coord;         // координаты
    /**
     * Создает объект.
     * Присваивает ему уникальный номер.
     * Случайным образом задает тип (модель).
     * Случайным образом задает координаты.
     */
    public Bomber(){
        Bomber.number += 1;
        this.num = Bomber.number;
        this.generator = new Random();
        this.name = new String( Bomber.names[this.generator.nextInt( Bomber.names.length )]) ;
        this.coord = new Coord();
        this.say_about();
    }
    /**
     * Возвращает имя модели машины.
     */
    public String getName(){
        return this.name;
    }
    /**
     * Печатает сообщение от имени этой машины (борта)
     * @param s сообщение
     */
    public void say(String s){
        System.out.println("Bomber("+ this.num +"): " + ": (" + s + ");");
    }
    /**
     * Печатает сообщение о запуске машины и ее кординатах
     */
    public void say_about(){
        System.out.println("Bomber("+ this.num +"): " + this.name  +  ": (" + this.coord.toString()+");");
    }
}