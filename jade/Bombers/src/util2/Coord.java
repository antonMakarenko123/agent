/**
 * @author w495
 * �������� ��������� ��������� ���� � ���������������
 */

package util2;

import java.util.Random;
import java.util.TreeMap;
import static java.lang.Math.sqrt;

public class Coord {
    private TreeMap<String, Integer> data;
    private TreeMap<String, Integer> range;
    private Random generator;
    /** -----------------------------------------
     * ������ ���������� �����������
     * -----------------------------------------
     */
    public void setRange(){
        this.range.put("x", new Integer(100));
        this.range.put("y", new Integer(100));
    }
    /**
     * ������� ������
     * �������������� ��������� ��������� �����.
     * ������ �������� ��������� � ���� ����������.
     */
    public Coord() {
        this.generator = new Random();
        this.data = new TreeMap<String, Integer>();
        this.range = new TreeMap<String, Integer>();
        this.setRange();
        this.data.put("x", this.generator.nextInt(this.range.get("x")));
        this.data.put("y", this.generator.nextInt(this.range.get("y")));
    }
    /**
     * ��������� ���������� ����� ����� �������.
     * ����������� ������ ��� ������������ ������.
     * @param another ����� �� ������ ����������� ����������
     * @return $$\sqrt((x_1 - x_2)^2 + (y_1 - y_2)^2)  $$
     */
    public double distance(Coord another){
        int x_part = (another.data.get("x") - this.data.get("x"))^2;
        int y_part = (another.data.get("y") - this.data.get("y"))^2;
        double res = sqrt(x_part + y_part);
        return res;
    }
    /**
     * ��������� ��������� � ������
     * @return ������ ���� x,y
     */
    public String toString(){
        return ""
                + this.data.get("x").toString()
                + ","
                + this.data.get("y").toString();
    }
    /**
     * ��������� ��������� � ������
     * @return ������ ���� [x, y]
     */
    public String toBrackets(){
        return "["
                + this.data.get("x").toString()
                + ", "
                + this.data.get("y").toString()
                + "]";
    }
    /**
     * ��������� ������ ���� x,y � ����������.
     * �������������� ��� ������� ��������
     * @param ������ ���� x,y
     */
    public void FromString(String str){
        String format[] = str.split(",");
        this.data.put("x", Integer.parseInt(format[0]));
        this.data.put("y", Integer.parseInt(format[1]));

    }
}