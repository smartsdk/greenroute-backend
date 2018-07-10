/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.utils;
import java.text.DecimalFormat;

/**
 *
 * @author yolanda.baca
 */
public class AlertCatalog {
    
    static DecimalFormat decimalformat = new DecimalFormat("##.##");
    
    public AlertCatalog(){
    }
    
    public static String setAlertTemperature(String valor){
        Float temperature = Float.parseFloat(valor);
        String format = decimalformat.format(temperature);
        
        if(temperature<=-40){
            return ("Temperature: " + format + " °C <br> Classed as: Hyper-glacial. <br> Advice: Thermal very cold and extreme cold stress.");
        }
        else if(temperature>=-39&&temperature<=-20){
            return ("Temperature: " + format + " °C <br> Classed as: Glacial. <br> Advice: Thermal sensitivity cold and strong cold stress.");
        }
        else if(temperature>=-19&&temperature<=-10){
            return ("Temperature: " + format + " °C <br> Classed as: Extremely cold. <br> Advice: Thermal sensitivity cool and moderate cold stress.");
        }
        else if(temperature>=-9&&temperature<=-1){
            return ("Temperature: " + format + " °C <br> Classed as: Very cold. <br> Advice: Thermal sensitivity slightly cool and slight cold stress.");
        }
        else if(temperature>=0&&temperature<=13){
            return ("Temperature: " + format + " °C <br> Classed as: Cold. <br> Advice: Thermal sensitivity comfortable and no thermal stress.");
        }
        else if(temperature>=27&&temperature<=32){
            return ("Temperature: " + format + " °C <br> Classed as: Caution. <br> Advice: Fatigue is possible with prolonged exposure and activity. Continuing activity could result in heat cramps.");
        }
        else if(temperature>=33&&temperature<=41){
            return ("Temperature: " + format + " °C <br> Classed as: Alert. <br> Advice: Heat cramps and heat exhaustion are possible. Continuing activity could result in heat stroke.");
        }
        else if(temperature>=42&&temperature<=54){
            return ("Temperature: " + format + " °C <br> Classed as: Warning. <br> Advice: Heat cramps and heat exhaustion are likely; heat stroke is probable with continued activity.");
        }
        else if(temperature>54){
            return("Temperature: " + format + " °C <br> Classed as: Danger. <br> Advice: Extreme danger, heat stroke is imminent.");
        }
          return ("Temperature: " + format + " °C");  
    }
    
    public static String seteventObservedTemperature(String valor){
        Float temperature = Float.parseFloat(valor);
        if(temperature<=-10){
            return("Cold alert");
        }
        else if(temperature>=-9&&temperature<=32){
            return("Temperature");
        }
        else if(temperature>=33){
            return("Heat alert");
        }
        
        return valor;
    }
    
    public static String setAlertHumidity(String valor){
        Float humidity = Float.parseFloat(valor);
        String format = decimalformat.format(humidity);
        if(humidity<=29.99){
            return("Relative humidity: "+ format + " %. <br> Advice: No discomfort.");
        }else if(humidity>=30&&humidity<=39.99){
            return("Relative humidity: "+ format + " %. <br> Advice: Some discomfort.");
        }else if(humidity>=40&&humidity<=45.99){
            return("Relative humidity: "+ format + " %. <br> Advice: Great discomfort: avoid exertion.");
        }else if(humidity>=46&&humidity<=54.99){
            return("Relative humidity: "+ format + " %. <br> Advice: Dangerous.");
        }else if(humidity>54){
            return("Relative humidity: "+ format + " %. <br> Advice: Heat stroke imminent.");
        }
        
        return ("Relative humidity: " + format);
    }
    
    public static String AlertPollution(String valor, String pollutant){
        Float pollution = Float.parseFloat(valor);
        
        switch(pollutant){
            case "PM10":
                if(pollution>=0&&pollution<=50){
                    return ("Particulate matter index: " + valor + " GQ <br> Air Quality: Good. <br> Advice: Air quality is considered satisfactory, and air pollution poses little or no risk.");
                }else if(pollution>=51&&pollution<=100){
                    return ("Particulate matter index: " + valor + " GQ <br> Air Quality: Acceptable. <br> Advice: Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution.");
                }else if(pollution>=101&&pollution<=250){
                    return ("Particulate matter index: " + valor + " GQ <br> Air Quality: Moderately polluted. <br> Advice: Children, older adults, people who engage in intense physical activity or with respiratory and cardiovascular diseases, should limit prolonged outdoor efforts.");
                }else if(pollution>=251&&pollution<=350){
                    return ("Particulate matter index: " + valor + " GQ <br> Air Quality: Damage to health. <br> Advice: Everyone can experience health effects; Who belong to sensitive groups may experience serious health effects.");
                }else if(pollution>=351&&pollution<=430)
                    return ("Particulate matter index: " + valor + " GQ <br> Air Quality: Very danger. <br> Advice: It represents an emergency condition. The entire population is likely to be affected.");
                break;
            case "NO2":
                if(pollution>=0&&pollution<=40){
                    return ("Nitrogen dioxide index: " + valor + " PPB <br> Air Quality: Good. <br> Advice: Air quality is considered satisfactory, and air pollution poses little or no risk.");
                }else if(pollution>=41&&pollution<=80){
                    return ("Nitrogen dioxide index: " + valor + " PPB <br> Air Quality: Acceptable. <br> Advice: Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution.");
                }else if(pollution>=81&&pollution<=180){
                    return ("Nitrogen dioxide index: " + valor + " PPB <br> Air Quality: Moderately polluted. <br> Advice: Children, older adults, people who engage in intense physical activity or with respiratory and cardiovascular diseases, should limit prolonged outdoor efforts.");
                }else if(pollution>=181&&pollution<=280){
                    return ("Nitrogen dioxide index: " + valor + " PPB <br> Air Quality: Damage to health. <br> Advice: Everyone can experience health effects; Who belong to sensitive groups may experience serious health effects.");
                }else if(pollution>=281&&pollution<=400){
                    return ("Nitrogen dioxide index: " + valor + " PPB <br> Air Quality: Very danger. <br> Advice: It represents an emergency condition. The entire population is likely to be affected.");
                }
                break;
            case "O3":
                if(pollution>=0&&pollution<=50){
                    return ("Ozone  index: " + valor + " PPB <br> Air Quality: Good. <br> Advice: Air quality is considered satisfactory, and air pollution poses little or no risk.");
                }else if(pollution>=51&&pollution<=100){
                    return ("Ozone index: " + valor + " PPB <br> Air Quality: Acceptable. <br> Advice: Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution.");
                }else if(pollution>=101&&pollution<=168){
                    return ("Ozone index: " + valor + " PPB <br> Air Quality: Moderately polluted. <br> Advice: Children, older adults, people who engage in intense physical activity or with respiratory and cardiovascular diseases, should limit prolonged outdoor efforts.");
                }else if(pollution>=169&&pollution<=208){
                    return ("Ozone index: " + valor + " PPB <br> Air Quality: Damage to health. <br> Advice: Everyone can experience health effects; Who belong to sensitive groups may experience serious health effects.");
                }else if(pollution>=209&&pollution<=748){
                    return("Ozone index: " + valor + " PPB <br> Air Quality: Very danger. <br> Advice: It represents an emergency condition. The entire population is likely to be affected.");
                }
                break;
            case "CO":
                if(pollution>=0&&pollution<=40){
                    return ("Carbon monoxide index: " + valor + " PPM <br> Air Quality: Good. <br> Advice: Air quality is considered satisfactory, and air pollution poses little or no risk.");
                }else if(pollution>=41&&pollution<=80){
                    return ("Carbon monoxide index: " + valor + " PPM <br> Air Quality: Acceptable. <br> Advice: Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution.");
                }else if(pollution>=81&&pollution<=380){
                    return ("Carbon monoxide index: " + valor + " PPM <br> Air Quality: Moderately polluted. <br> Advice: Children, older adults, people who engage in intense physical activity or with respiratory and cardiovascular diseases, should limit prolonged outdoor efforts.");
                }else if(pollution>=381&&pollution<=800){
                    return ("Carbon monoxide index: " + valor + " PPM <br> Air Quality: Damage to health. <br> Advice: Everyone can experience health effects; Who belong to sensitive groups may experience serious health effects.");
                }else if(pollution>=801&&pollution<=1600){
                    return ("Carbon monoxide index: " + valor + " PPM <br> Air Quality: Very danger. <br> Advice: It represents an emergency condition. The entire population is likely to be affected.");
                }
                break;
            case "SO2":
                if(pollution>=0&&pollution<=40){
                    return ("Sulfur monoxide index: " + valor + " PPB <br> Air Quality: Good. <br> Advice: Air quality is considered satisfactory, and air pollution poses little or no risk.");
                }else if(pollution>=41&&pollution<=80){
                    return ("Sulfur monoxide index: " + valor + " PPB <br> Air Quality: Acceptable. <br> Advice: Air quality is acceptable; however, for some pollutants there may be a moderate health concern for a very small number of people who are unusually sensitive to air pollution.");
                }else if(pollution>=81&&pollution<=380){
                    return ("Sulfur monoxide index: " + valor + " PPB <br> Air Quality: Moderately polluted. <br> Advice: Children, older adults, people who engage in intense physical activity or with respiratory and cardiovascular diseases, should limit prolonged outdoor efforts.");
                }else if(pollution>=381&&pollution<=800){
                    return ("Sulfur monoxide index: " + valor + " PPB <br> Air Quality: Damage to health. <br> Advice: Everyone can experience health effects; Who belong to sensitive groups may experience serious health effects.");
                }else if(pollution>=801&&pollution<=1600){
                    return("Sulfur monoxide index: " + valor + " PPB <br> Air Quality: Very danger. <br> Advice: It represents an emergency condition. The entire population is likely to be affected.");
                }                
                break;
        }
        
        return valor;
    }
    
    public static String setSubCategoryAlert (String eventObserved){
        switch (eventObserved) {
            case "trafficJam":
                eventObserved="Traffic jam";
                break;
            case "carAccident":
                eventObserved="Car accident";
                break;
            case "carWrongDirecion":
                eventObserved="Car wrong direction";
                break;
            case "carStopped":
                eventObserved="Car stopped";
                break;
            case "roadClosed":
                eventObserved="Road closed";
                break;
            case "roadWorks":
                eventObserved="Road works";
                break;
            case "hazardOnRoad":
                eventObserved="Hazard on road";
                break;
            case "injuredBiker":
                eventObserved="Injured biker";
                break;
            case "highTemperature":
                eventObserved="High temperature";
                break;
            case "lowTemperature":
                eventObserved="Low temperature";
                break;
            case "heatWave":
                eventObserved="Heat wave";
                break;
            case "tropicalCyclone":
                eventObserved="Tropical cyclone";
                break;
            case "airPollution":
                eventObserved="Air pollution";
                break;
            case "waterPollution":
                eventObserved="Water pollution";
                break;
            case "pollenConcentration":
                eventObserved="Pollen concentration";
                break;
            case "asthmaAttack":
                eventObserved="Asthma attack";
                break;
            case "bumpedPatient":
                eventObserved="Bumped patient";
                break;
            case "fallenPatient":
                eventObserved="Fallen patient";
                break;
            case "heartAttack":
                eventObserved="Heart attack";
                break;
            case "suspiciousAction":
                eventObserved="Suspicious action";
                break;
            default:
                break;
        }
        
        return eventObserved;
    }
        
    /*public static void main(String [] args){
            DecimalFormat decimalformat = new DecimalFormat("##.##");
            System.out.println(decimalformat.format(10.232323232323232));

    }   */
   
}


