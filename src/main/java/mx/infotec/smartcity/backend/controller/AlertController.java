package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import mx.infotec.smartcity.backend.controller.websocket.AlertSocketController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.smartcity.backend.model.Alert;
import mx.infotec.smartcity.backend.model.Data;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.AlertRepository;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import mx.infotec.smartcity.backend.model.OrionAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

/**
 * Return alerts and create notifications
 * @author Adrian Molina
 */
@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    public AlertSocketController socket = new AlertSocketController();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    // @RequestMapping(method = RequestMethod.GET)
    // public List<Alert> getAll() {
    // List<Alert> res = alertRepository.findAllByOrderByDateTimeDesc();
    //  
    // if (res == null) {
    // return new ArrayList<>();
    // } else {
    // return res;
    // }
    // }

    /**
     * Returns list of paginated alerts
     * 
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/page/{page}/items/{size}")
    public Page<Alert> getByPageSize(@PathVariable("page") String page, @PathVariable("size") String size) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        return alertRepository.findAllByOrderByDateTimeDesc(pageable);
    }

    /**
     * Returns list of paginated alerts filtered by date (YYYY-MM-dd)
     * 
     * @param date
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/date/{date}/page/{page}/items/{size}")
    public Page<Alert> getByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") Date date,
            @PathVariable("page") String page, @PathVariable("size") String size) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        return alertRepository.findByDateTimeBetweenOrderByDateTimeDesc(date, c.getTime(), pageable);
    }

    /**
     * Search alerts by id
     * 
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Alert getById(@PathVariable("id") String id) {
        return alertRepository.findById(id);
    }

    /**
     * Returns the alerts for a specific user
     * 
     * @param id
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}/page/{page}/items/{size}")
    public Page<Alert> getAllByUser(@PathVariable("id") String id, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        UserProfile profile = userProfileRepository.findOne(id);
        List<String> alertTypes = new ArrayList<>();
        profile.getGroups().forEach((group) -> {
            alertTypes.addAll(group.getNotificationIds());
        });

//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date today = new Date();
//        try {
//            today = formatter.parse(formatter.format(new Date()));
//        } catch (ParseException ex) {
//            Logger.getLogger(AlertController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(today);
//        c.add(Calendar.DATE, 1);
//        c.add(Calendar.SECOND, -1);
//
//        Page<Alert> res = alertRepository.findByAlertTypeInAndDateTimeBetweenOrderByDateTimeDesc(alertTypes, today,
//                c.getTime(), pageable);

          Page<Alert> res = alertRepository.findByAlertTypeInOrderByDateTimeDesc(alertTypes, pageable);
        return res;
    }

    /**
     * Returns alerts of a specific alert type
     * 
     * @param alertType
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/type/{alertType}/page/{page}/items/{size}")
    public Page<Alert> getAllByAlert(@PathVariable("alertType") String alertType, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Page<Alert> res = alertRepository.findByAlertTypeOrderByDateTimeDesc(alertType, pageable);
        return res;
    }

    /**
     * Returns alerts of a specific type and eventObserved
     * 
     * @param alertType
     * @param subtype
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/type/{alertType}/subtype/{subtype}/page/{page}/items/{size}")
    public Page<Alert> getAllByAlertAndSubalert(@PathVariable("alertType") String alertType,
            @PathVariable("subtype") String subtype, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Page<Alert> res = alertRepository.findByAlertTypeAndEventObservedOrderByDateTimeDesc(alertType, subtype,
                pageable);
        return res;
    }

    /**
     * Search alerts by type and date (yyyy-MM-dd)
     * 
     * @param alertType
     * @param date
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/type/{alertType}/date/{date}/page/{page}/items/{size}")
    public Page<Alert> getAllByAlertAndDate(@PathVariable("alertType") String alertType,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") Date date, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        Page<Alert> res = alertRepository.findByAlertTypeAndDateTimeBetweenOrderByDateTimeDesc(alertType, date,
                c.getTime(), pageable);
        return res;
    }

    /**
     * Search alerts by alertType, eventObserved and date (yyyy-MM-dd)
     * 
     * @param alertType
     * @param subtype
     * @param date
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/type/{alertType}/subtype/{subtype}/date/{date}/page/{page}/items/{size}")
    public Page<Alert> getAllByAlertAndSubalertAndDate(@PathVariable("alertType") String alertType,
            @PathVariable("subtype") String subtype,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") Date date, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        Page<Alert> res = alertRepository.findByAlertTypeAndEventObservedAndDateTimeBetweenOrderByDateTimeDesc(
                alertType, subtype, date, c.getTime(), pageable);
        return res;
    }

    /**
     * Returns alerts generated by the refered user (its own alerts)
     * 
     * @param id
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/page/{page}/items/{size}")
    public Page<Alert> getAllEventsByUser(@PathVariable("id") String id, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Page<Alert> res = alertRepository.findByRefUserOrderByDateTimeDesc(id, pageable);
        return res;
    }

    /**
     * Returns alerts generated by the refered user (its own alerts) and filtered by type
     * 
     * @param id
     * @param alertType
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/page/{page}/items/{size}")
    public Page<Alert> getAllEventsByUserAndAlertType(@PathVariable("id") String id,
            @PathVariable("alertType") String alertType, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Page<Alert> res = alertRepository.findByRefUserAndAlertTypeOrderByDateTimeDesc(id, alertType, pageable);
        return res;
    }

    /**
     * Returns alerts generated by the refered user (its own alerts) and filtered by type and eventObserved
     * 
     * @param id
     * @param alertType
     * @param subtype
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/subtype/{subtype}/page/{page}/items/{size}")
    public Page<Alert> getAllEventsByUserAndAlertTypeAndSubtype(@PathVariable("id") String id,
            @PathVariable("alertType") String alertType, @PathVariable("subtype") String subtype,
            @PathVariable("page") String page, @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Page<Alert> res = alertRepository.findByRefUserAndAlertTypeAndEventObservedOrderByDateTimeDesc(id, alertType,
                subtype, pageable);
        return res;
    }

    /**
     * Return alerts generated by the refered user (its own alerts) and specific date (yyyy-MM-dd)
     * 
     * @param id
     * @param date
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/date/{date}/page/{page}/items/{size}")
    public Page<Alert> getAllEventsByUserAndDate(@PathVariable("id") String id,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") Date date, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        Page<Alert> res = alertRepository.findByRefUserAndDateTimeBetweenOrderByDateTimeDesc(id, date, c.getTime(),
                pageable);
        return res;
    }

    /**
     * Return alerts generated by the refered user (its own alerts) filtered by type and specific date (yyy-MM-dd)
     * 
     * @param id
     * @param date
     * @param alertType
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/date/{date}/page/{page}/items/{size}")
    public Page<Alert> getAllEventsByUserAndAlertTypeAndDate(@PathVariable("id") String id,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") Date date,
            @PathVariable("alertType") String alertType, @PathVariable("page") String page,
            @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        Page<Alert> res = alertRepository.findByRefUserAndAlertTypeAndDateTimeBetweenOrderByDateTimeDesc(id, alertType,
                date, c.getTime(), pageable);
        return res;
    }

    /**
     * Return alerts generated by the refered user (its own alerts) filtered by type, eventObserved and specific date (yyyy-MM-dd)
     * 
     * @param id
     * @param date
     * @param alertType
     * @param subtype
     * @param page
     * @param size
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/subtype/{subtype}/date/{date}/page/{page}/items/{size}")
    public Page<Alert> getAllEventsByUserAndAlertTypeAndSubtypeAndDate(@PathVariable("id") String id,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") Date date,
            @PathVariable("alertType") String alertType, @PathVariable("subtype") String subtype,
            @PathVariable("page") String page, @PathVariable("size") String size, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        Page<Alert> res = alertRepository
                .findByRefUserAndAlertTypeAndEventObservedAndDateTimeBetweenOrderByDateTimeDesc(id, alertType, subtype,
                        date, c.getTime(), pageable);
        return res;
    }
    
    /**
     * Creates a new notification (alert)
     * 
     * @param orionAlert
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNotification(@RequestBody OrionAlert orionAlert, UriComponentsBuilder ucBuilder) {
        /* Se obtienen las entidades de la clave principal determinada por el campo DATA */
        List<Data> datos = orionAlert.getData();
       
        Map <String, String> eventsObserved = new HashMap <>();
        eventsObserved.put("precipitation", "Weather");
        eventsObserved.put("relativeHumidity", "Weather");
        eventsObserved.put("temperature", "Weather");
        eventsObserved.put("windDirection", "Weather");
        eventsObserved.put("windSpeed", "Weather");
        eventsObserved.put("altitude", "Weather");
        eventsObserved.put("barometricPressure", "Weather");
        eventsObserved.put("luminosity", "Weather");
        eventsObserved.put("CO","Environment");
        eventsObserved.put("NO2","Environment");
        eventsObserved.put("NOx","Environment");
        eventsObserved.put("SO2","Environment");
        eventsObserved.put("O3","Environment");
        eventsObserved.put("PM10","Environment");
        eventsObserved.put("TVOC","Environment");
        eventsObserved.put("CO2","Environment");
        
        for(Data data : datos){
            if(data.getType().equals("AirQualityObserved")){
                for(Map.Entry<String, String> entry : eventsObserved.entrySet() ){
                    Alert alert = new Alert(data, entry.getKey(), entry.getValue());
                    if(alert.getFound())
                        alertRepository.save(alert);
                }
            }else if(data.getType().equals("Alert")){
                Alert alerta = new Alert(data, "", "");
                alertRepository.save(alerta);
                
                try{
                    socket.pushAlert(alerta);
                }catch (Exception e){
                    LOGGER.error(e.toString());
                }
            }
        }
        
        
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
        
}
