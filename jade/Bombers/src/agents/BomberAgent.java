/**
 * @author w495
 * Описание бомбардировщика как агента.
 * Агент испускает шум.
 * По этому шушму есго находит бомбардировщик.
 * Бомбардировщик запрашивает координаты цели.
 * Получив их, бомбардировщик уничтожает цель.
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
        // бомбардировщик как боевая единица
    Vector<AID> targets;
        // список просмотренных целей
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
     * Захватывает цель и прицеливается
     * Обнаруживает цель по "шуму" и отправляет сообщение о том что цель найдена.
     * @param ACLMessage input_message входящее сообщение
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
     * Прицеливается и уничтожает
     * По ответу цели определяет ее координаты.
     * @param ACLMessage input_message входящее сообщение
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