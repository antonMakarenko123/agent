/**
 * @author w495
 * Описание цели как агента.
 * Агент испускает шум.
 * По этому шушму есго находит бомбардировщик.
 * Бомбардировщик запрашивает координаты цели.
 * Получив их, бомбардировщик уничтожает цель.
 */

package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import objects.Target;

public class TargetAgent  extends Agent {
    Target target;
    boolean isDetected;
        // сама цель как боевая единица
    AMSAgentDescription [] env_agents;
        // окружающие объекты
    public void setup() {
        this.isDetected = false;
        this.target = new Target();
         // Поведение агента исполняемое в цикле
        this.addBehaviour(new CyclicBehaviour(this){
            public void action() {
                ACLMessage input_message = receive();
                if (null != input_message) {
                    say_coords(input_message);
                    die(input_message);
                }
                live(input_message);
                block();
                // блокируем пока нет сообщения
            }
        });
        this.look_over();
        this.make_noise("start noise");
    }
    /**
     * Осматривается.
     * Получает список всех агентов вокруг
     */
    public void look_over(){
        this.env_agents = null;
        // список всех агентов
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            this.env_agents = AMSService.search(this, new AMSAgentDescription(), c);
        } 
        catch (Exception e){
            System.out.println( "Problem searching AMS: " + e);
            e.printStackTrace();
        }
    }
    /**
     * Создает естесвенный "шум" цели.
     * С физической точки зрения это может быть звуковой, тепловой, радиопомехи
     * @param noise тип шама
     */
    public void make_noise(String noise){
        for(int i = 0; i != this.env_agents.length ; ++i){
            AID agentID = env_agents[i].getName();

            // не будем посылать сообщения шума сами себе
            if(this.getAID().getName().equals(agentID.getName()))
                continue;
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(agentID);
                // id агента которому отправляем сообщение
            msg.setLanguage("English");
                //язык
            msg.setContent(noise);
                //содержимое сообщения
            send(msg);
                //отправляем сообщение
        }
    }
    /**
     * Эиулирование жизни агента
     * @param input_message входящее сообщение (фиктивный параметр)
     */
    public void live(ACLMessage input_message ){
            look_over();
            make_noise("noise");
    }
    /**
     * Сообщает коордиинаты цели
     * @param input_message входящее сообщение
     */
    public void say_coords(ACLMessage input_message ){
        String msg_string = input_message.getContent().toString();
        if(!this.isDetected &&  msg_string.equals("detected")){
            this.isDetected = true;
            this.target.say("was detected");     
            ACLMessage reply = input_message.createReply();
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent("coords:" + this.target.getCoordString());
            send(reply);
        }
    }
    /**
     * Убивает агента. Сообщает кто ему послал последний сигнал
     * @param input_message входящее сообщение
     */
    public void die(ACLMessage input_message ){
        String message_string = input_message.getContent().toString();
        if(this.isDetected && message_string.split(":")[0].equals("you was killed")){
            this.target.say("was killed by " + message_string.split(":")[1]);
            this.doDelete();
        }   
    }
}