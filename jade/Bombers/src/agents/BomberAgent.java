/**
 * @author w495
 * �������� ��������������� ��� ������.
 * ����� ��������� ���.
 * �� ����� ����� ���� ������� ��������������.
 * �������������� ����������� ���������� ����.
 * ������� ��, �������������� ���������� ����.
 */

package agents;


import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Vector;

import objects.Bomber;
import util2.Coord;

public class BomberAgent extends Agent {
    Bomber bomber;
        // �������������� ��� ������ �������
    Vector<AID> targets;
        // ������ ������������� �����
    protected void setup() {
        this.bomber  = new Bomber();
        this.targets = new Vector<AID>();

        this.addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage input_message = receive();
                if (null != input_message) {
                    detect_target(input_message);
                    kill(input_message);
                }
                block();
            }
        });
    }
    /**
     * ����������� ���� � �������������
     * ������������ ���� �� "����" � ���������� ��������� � ��� ��� ���� �������.
     * @param ACLMessage input_message �������� ���������
     */
    public void detect_target(ACLMessage input_message){
        String message_string = input_message.getContent().toString();
        AID sender =  input_message.getSender();
        if(message_string.equals("noise") && (! targets.contains(sender))){
            this.targets.add(sender);
            // --------------------------------
            // this.bomber.say("" + sender );
            // --------------------------------
            ACLMessage reply = input_message.createReply();
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent( "detected");
            send(reply);
        }
    }
    /**
     * ������������� � ����������
     * �� ������ ���� ���������� �� ����������.
     * @param ACLMessage input_message �������� ���������
     */
    public void kill(ACLMessage input_message){
        String message_string = input_message.getContent().toString();
        AID sender =  input_message.getSender();
        Coord target_coord = new Coord();
        if(message_string.split(":")[0].equals("coords") && (this.targets.contains(sender))){
            target_coord.FromString(message_string.split(":")[1]);
            this.bomber.say("target at " + target_coord.toBrackets());
            ACLMessage reply = input_message.createReply();
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent( "you was killed:" + this.bomber.getName());
            send(reply);
        }
    }
}