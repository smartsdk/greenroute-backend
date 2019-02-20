package mx.infotec.smartcity.backend.controller.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import mx.infotec.smartcity.backend.model.Alert;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Return alerts and create notifications
 * @author Adrian Molina
 */
@Controller
public class AlertSocketController {

    @Autowired
    private SimpMessagingTemplate template;
    
    public AlertSocketController(){
    }
    
    public AlertSocketController(SimpMessagingTemplate template){
        this.template = template;
    }
    
    /**
     * Broadcast a new alert notification
     * @param alert
     * @throws Exception 
     */
    @SendTo("/websocket/alerts")
    public void pushAlert(Alert alert) throws Exception {
        this.template.convertAndSend("/websocket/alerts", alert);
    }    
    
}
