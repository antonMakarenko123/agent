/**
 * @author w495
 * �������� ���� ��� ������.
 * ����� ��������� ���.
 * �� ����� ����� ���� ������� ��������������.
 * �������������� ����������� ���������� ����.
 * ������� ��, �������������� ���������� ����.
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
        // ���� ���� ��� ������ �������
    AMSAgentDescription [] env_agents;
        // ���������� �������
    public void setup() {
        this.isDetected = false;
        this.target = new Target();
         // ��������� ������ ����������� � �����
        this.addBehaviour(new CyclicBehaviour(this){
            public void action() {
                ACLMessage input_message = receive();
                if (null != input_message) {
                    say_coords(input_message);
                    die(input_message);
                }
                live(input_message);
                block();
                // ��������� ���� ��� ���������
            }
        });
        this.look_over();
        this.make_noise("start noise");
    }
    /**
     * �������������.
     * �������� ������ ���� ������� ������
     */
    public void look_over(){
        this.env_agents = null;
        // ������ ���� �������
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
     * ������� ����������� "���" ����.
     * � ���������� ����� ������ ��� ����� ���� ��������, ��������, �����������
     * @param noise ��� ����
     */
    public void make_noise(String noise){
        for(int i = 0; i != this.env_agents.length ; ++i){
            AID agentID = env_agents[i].getName();

            // �� ����� �������� ��������� ���� ���� ����
            if(this.getAID().getName().equals(agentID.getName()))
                continue;
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(agentID);
                // id ������ �������� ���������� ���������
            msg.setLanguage("English");
                //����
            msg.setContent(noise);
                //���������� ���������
            send(msg);
                //���������� ���������
        }
    }
    /**
     * ������������ ����� ������
     * @param input_message �������� ��������� (��������� ��������)
     */
    public void live(ACLMessage input_message ){
            look_over();
            make_noise("noise");
    }
    /**
     * �������� ����������� ����
     * @param input_message �������� ���������
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
     * ������� ������. �������� ��� ��� ������ ��������� ������
     * @param input_message �������� ���������
     */
    public void die(ACLMessage input_message ){
        String message_string = input_message.getContent().toString();
        if(this.isDetected && message_string.split(":")[0].equals("you was killed")){
            this.target.say("was killed by " + message_string.split(":")[1]);
            this.doDelete();
        }   
    }
}