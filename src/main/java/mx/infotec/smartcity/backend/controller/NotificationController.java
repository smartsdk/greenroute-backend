package mx.infotec.smartcity.backend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.smartcity.backend.model.Group;
import mx.infotec.smartcity.backend.model.Notification;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.AlertRepository;
import mx.infotec.smartcity.backend.persistence.NotificationRepository;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Adrian Molina
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private AlertRepository alertRepository;

    /**
     * Returns the list of notifications and its subnotifications
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Notification> getAll() {
        List<Notification> res = notificationRepository.findAll();

        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }

    /**
     * Returns a notification and its subnotifications for a given id
     * 
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Notification getById(@PathVariable("id") String id) {
        return notificationRepository.findById(id);
    }

    /**
     * Returns the notifications of a given user
     * 
     * @param id
     * @return
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/notifications")
    public ResponseEntity<?> getUserGroups(@PathVariable("id") String id) throws ParseException {
        
        // codigo para obtener los parametros de fechas y pageable
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        Date date = sdfDate.parse(strDate);
        Pageable pageableRequest = new PageRequest(0, 1);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        // termina codigo para obtener los parametros de fechas y pageable
        
        UserProfile userProfile = userProfileRepository.findOne(id);
        String str = "";
        if (userProfile != null) {
            List<Notification> notifications = this.notificationRepository.findAll();
            List<Notification> userNotifications = new ArrayList<Notification>();
            if (userProfile.getGroups() != null) {
                for (Group group : userProfile.getGroups()) {
                    for (String userNotification : group.getNotificationIds()) {
                        for (Notification notification : notifications) {
                        	if (userNotification.equals(notification.getId())) {
                            	
                                if (!userNotifications.contains(notification)) {
                                    //notification.setCount(this.alertRepository.findByAlertTypeAndDateTimeBetweenOrderByDateTimeAsc(notification.getId(),date, c.getTime()).size());
                                    notification.setCount(this.alertRepository.findByAlertType(notification.getId()).size());
                                    userNotifications.add(notification);
                                }	
                            }
                        }
                    }
                }
                
                // Se agregan estas líneas de codigo solo para devolver la lista de notificaciones ordenada por nombre en orden alfabetico (Manu)
                if(userNotifications.size() > 0)
                {
                    Collections.sort(userNotifications, (Notification n1, Notification n2) -> n1.getName().compareTo(n2.getName()));
                }
            }
           // return ResponseEntity.accepted().body(notifications);
           return ResponseEntity.accepted().body(userNotifications);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }
  
}
