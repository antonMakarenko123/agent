/**
 * @author w495
 * �������� ������ ����.
 * ��� ��� �������� ��� ������ �������.
 * ������� ����������� ���� �������
 * � agents.TargetAgent
 */

package objects;

import java.util.Random;
import util2.Coord;

public class Target {
    static int number = 0;
    static String names[] = {"Tank", "Rocket", "Ack-ack"};
    // ���� �������

    int num;                // �����
    private Coord coord;    // ��������� ����
    private String name;    // ��� ����

    private Random generator;
    /**
     * ������� ������.
     * ����������� ��� ���������� �����.
     * ��������� ������� ������ ���.
     * ��������� ������� ������ ����������.
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
     * ���������� ���������  ����.
     */
    public Coord getCoord(){
        return this.coord;
    }
    /**
     * ���������� ��������� ���� � ���� ������.
     */
    public String getCoordString(){
        return this.coord.toString();
    }
    /*
     * ���������� ��� ����.
     */
    public String getName(){
        return this.name;
    }
    /**
     * �������� ��������� �� ����� ���� ����
     * @param s ���������
     */
    public void say(String s){
        System.out.println("Target("+ this.num +"): "  + ": (" + s + ");");
    }
    /**
     * �������� ��������� � ������� ���� � �� ����������
     */
    public void say_about(){
        System.out.println("Target("+ this.num +"): " + this.name  + ": (" + this.coord.toString()+");");
    }
}