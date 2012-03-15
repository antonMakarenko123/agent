/**
 * @author w495
 * �������� ������ ���������������.
 * ��� ��� �������� ��� ������ �������.
 * ������� ����������� ���� �������
 * � agents.BomberAgent
 */

package objects;

import java.util.Random;
import util2.Coord;


public class Bomber  {
    static int number = 0;
    static String names[] = {"T-50", "S-37", "MIG-31", "SU-24", "SU-27"};
        // ���� �������
    int num = 0;                // �������� �����
    private String name;        // ������ ������
    private Random generator;
    public Coord coord;         // ����������
    /**
     * ������� ������.
     * ����������� ��� ���������� �����.
     * ��������� ������� ������ ��� (������).
     * ��������� ������� ������ ����������.
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
     * ���������� ��� ������ ������.
     */
    public String getName(){
        return this.name;
    }
    /**
     * �������� ��������� �� ����� ���� ������ (�����)
     * @param s ���������
     */
    public void say(String s){
        System.out.println("Bomber("+ this.num +"): " + ": (" + s + ");");
    }
    /**
     * �������� ��������� � ������� ������ � �� ����������
     */
    public void say_about(){
        System.out.println("Bomber("+ this.num +"): " + this.name  +  ": (" + this.coord.toString()+");");
    }
}