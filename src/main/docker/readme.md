# Docker Deployment Guide

The docker deployment uses the following services:

- Back-end:                 greenroute-back:8080
- FrontEnd:                 greenroute-front:80
- MongoDB:                  mongodb://greenroute-mongodb:27017
- Keyrock IdentityManager:  greenroute-keyrock:5000

Additional Services:
- OrionContextBroker:       smart-sdk-orion:1026
- QuantumLeap		    quantumleap:8668
- Grafana		    grafana:3000
- CrateDB		    cratedb:4200

Using the ***docker-compose.yml*** file you can be able to test and deploy the application
using the previosuly made docker images from each component.

However, in order to adapt the application to your needs (the ideal way), it is
necessary to perform a few configurations on the smart-sdk-back and smart-sdk-front.

## Configure greenroute-back to deploy and generate docker image.

1. Modify configuration in ***application.yml***

**Mail server configuration, It uses gmail smtp server fo testing purposes (Need to allow third-party applications use google acccount):**

```
  spring.mail.host: smtp.gmail.com
  spring.mail.port: 587
  spring.mail.username: acccount@gmail.com
  spring.mail.password: XXXXXXX
```

**Front-end references**

```
front.url: localhost/#
front.image-url: localhost
```

2. Deploy and create **.war** at root of smartcity-back/

```
sudo mvn -P production clean package docker:build
```

3. Add tag to recently created image and push *(optional)*

```
docker login
docker tag greenroute-back your-docker-account/greenroute-back:0.1
docker push your-docker-account/greenroute-back:0.1
```

## Configure, compile and create docker image for greenroute-front.

1. Modify ***/src/environments/environment.prod.ts*** to target backend URL, alerts application, grafana dashboard and routing map.

```
export const environment = {
  production: true,
  backend_sdk: 'http://localhost:8080/back-sdk',
  alerts_url: 'http://localhost:8443/#/',
  statistics_url: 'http://localhost:3000/dashboard/db/airquality-dashboard',
  routingmap_url: 'http://localhost:8081/'
};
```

2. Build angular application in ***greenroute-front/*** root

```
ng build --env=prod
```

3. Build docker image. Dockerfile uses ***nginx-alpine*** and copy compiled ***/dist*** files.

```
sudo docker build -t greenroute-front .
```

4. Tag and push image *(optional)*

```
sudo docker tag greenroute-front your-docker-account/greenroute-front:latest

sudo docker push your-docker-account/greenroute-front:latest
```


## Mongo seeder. To load initial data to mongoDB.

1. Verify the ***HOST*** on *smartcity-back/src/main/docker/mongo-import/sample-data/import.sh* corresponds to the container name in ***docker-compose.yml*** file.

HOST=greenroute-mongodb
PORT=27017

2. Create docker image from *greenroute-back/src/main/docker/mongo-import* *(optional)*

```
docker build -t mongo-seeder .
docker tag mongo-seeder your-docker-account/mongo-seeder:latest
docker push your-docker-account/mongo-seeder:latest
```

## Configure Grafana, CrateDB, QuantumLeap with data from Mexico City's AirQualityObserved.

Once the services are running, you can access Grafana on the **0.0.0.0:3000** CrateDB on **0.0.0.0:4200** and QuantumLeap in **0.0.0.0:8668**.

### QuantumLeap

Verify that QuantumLeap is working correclty by querying:

```
0.0.0.0:8668/v2/version
```

1. Create a subscription in OCB for AirQualityObserved where the notifications fall into QuantumLeap's endpoint ***http://0.0.0.0:8668/v2/notify***.

```
curl -v localhost:1026/v2/subscriptions -s -S -H 'Content-Type: application/json' --header "Fiware-Service:airquality" --header "Fiware-ServicePath:/" -d @- <<EOF
 {
  "description": "QuantumLeap AirQaulityCDMX",
  "subject": {
    "entities": [
      {
        "idPattern": ".* ",
        "type": "AirQualityObserved"
      }
    ],
    "condition": {
      "attrs": [
        "CO",
        "O3",
        "PM10",
        "SO2",
        "NO2",
        "temperature",
        "relativeHumidity",
        "dateObserved"
      ]
    }
  },
  "notification": {
    "attrs": [
      "id",
      "CO",
      "O3",
      "PM10",
      "SO2",
      "NO2",
      "temperature",
      "relativeHumidity",
      "dateObserved",
      "address",
      "location",
      "latitude",
      "longitude"
    ],
    "attrsFormat": "normalized",
    "http": {
      "url": "http://0.0.0.0:8668/v2/notify"
    },
    "metadata": [
      "dateCreated",
      "dateModified"
    ]
  },
  "throttling": 1
}
EOF
```

### CrateDB

In order to provide a better interface in the Grafana dashboard, we need to create two additional tables with the information of airquality stations and pollutants.

We can access cratedb via command-line or through web browser in the **localhost:4200**

1. To access via console, connect to the container:

```
docker exec -ti cratedb /bin/sh
```

2. Call the cratedb bash console (named crash) from inside the container:
```
crash
```

3. Create the stations an pollutants tables and insert data.

***TODO: FIX IMPORT WITH JSON FILE***

*Create Tables:*
```
CREATE TABLE IF NOT EXISTS "doc"."etstation" (
  "entity_id" STRING,
  "name" STRING,
  "acron" STRING,
  PRIMARY KEY ("entity_id")
)

CREATE TABLE IF NOT EXISTS "doc"."etpollutant" (
  "idpollutant" INTEGER,
  "name" STRING,
  "namefront" STRING,
  PRIMARY KEY ("idpollutant")
)
```
*Insert Data:*

```
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150020109','Acolman','ACO');
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090120609','Ajusco Medio','AJM');
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090120400','Ajusco','AJU');
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050301','Aragón','ARA');
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150130101','Atizapán','ATI');
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090020201','Azcapotzalco','AZC');
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090140201','Benito Juárez','BJU')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090020301','Camarones','CAM')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090030501','Centro de Ciencias de la Atmósfera','CCA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090070111','Cerro de la Estrella','CES')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150250109','Chalco','CHO')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090030303','Coyoacán','COY')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090040109','Cuajimalpa','CUA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150950109','Cuautitlán','CUT')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150570109','FES Acatlán','FAC')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050809','Gustavo A. Madero','GAM')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090150409','Hospital General de México','HGM')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050209','Inst. Mexicano del Petróleo','IMP')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150620109','Investigaciones Nucleares','INN')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150990113','Montecillo','MON')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090060101','Iztacalco','IZT')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484151040203','La Presa','LPR')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090170127','Merced','MER')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090160609','Miguel Hidalgo','MGH')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090090104','Milpa Alta','MPA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150580115','Nezahualcóyotl','NEZ')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090100127','Pedregal','PED')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150330327','San Agustín','SAG')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090040309','Santa fe','SFE')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050701','San Juan Aragón','SJA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090030109','Santa Ursula','SUR')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090130309','Tlahuac','TAH')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050404','Cerro del Tepeyac','TEC')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484151040115','Tlalnepantla','TLA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484151090101','Tultitlán','TLI')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090120209','Tlalpan','TPN')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090030401','UAM Xochimilco','UAX')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150330415','Xalostoc','XAL')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090150101','Lagunilla','LAG')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150330201','Los Laureles','LLA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090100209','Plateros','PLA')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090160309','Tacuba','TAC')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090030201','Taxqueña','TAX')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090070219','UAM Iztapalapa','UIZ')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484150200109','Villa de las flores','VIF')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050501','Vallejo','VAL')
insert into etstation (entity_id, name, acron) values('CDMX-AmbientObserved-484090050101','La Villa','LVI')

insert into etpollutant (idpollutant, name, namefront) values(1,'temperature','Temperature');
insert into etpollutant (idpollutant, name, namefront) values(2,'relativehumidity','Relative Humidity');
insert into etpollutant (idpollutant, name, namefront) values(3,'co','Carbon Monoxide (CO)');
insert into etpollutant (idpollutant, name, namefront) values(4,'no2','Nitrogen Dioxide (NO2)');
insert into etpollutant (idpollutant, name, namefront) values(5,'o3','Ozone (O3)');
insert into etpollutant (idpollutant, name, namefront) values(6,'so2','Sulfur Dioxide (SO2)');
insert into etpollutant (idpollutant, name, namefront) values(7,'pm10','Particulate Matter (PM10)');
```

### Grafana

Grafana comes with an API which lets you manage many options including the configuration of dashboards and datasources.

1. As a first step, you need to obtain a key to use the API:

```
POST http://admin:admin@localhost:3000/api/auth/keys
Content-Type: application/json
{"name":"apikeycurl", "role": "Admin"}
```
It returns a key that you'll need to send as an "Authorization" header in the following steps.
```
{
	"name": "apikeycurl",
	"key": "eyJrIjoiUDNGQlM5YldXbUdVU2JreDJiVkZDYW81aWZCTlZFSlkiLCJuIjoiYXBpa2V5Y3VybCIsImlkIjoxfQ=="
}
```

2. Create a crateDB datasource by indicating the crateDB URL within the json payload ***url: http://localhost:4200***.

```
POST http://localhost:3000/api/datasources
Content-Type: "application/json"
Authorization: "Bearer eyJrIjoiUDNGQlM5YldXbUdVU2JreDJiVkZDYW81aWZCTlZFSlkiLCJuIjoiYXBpa2V5Y3VybCIsImlkIjoxfQ=="

{
  "id": null,
  "orgId": 1,
  "name": "AIRQUALITY",
  "type": "crate-datasource",
  "typeLogoUrl": "public/plugins/crate-datasource/img/crate_logo.png",
  "access": "proxy",
  "url": "http://localhost:4200",
  "password": "",
  "user": "",
  "database": "",
  "basicAuth": false,
  "isDefault": true,
  "jsonData": {
    "keepCookies": [],
    "schema": "doc",
    "table": "etairqualityobserved",
    "timeColumn": "dateobserved",
    "timeInterval": "auto_gf"
  },
  "readOnly": false
}
```

3. Import the Grafana dashboard described in the ***rawdash.json*** file.

```
curl -X POST  -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJrIjoiUDNGQlM5YldXbUdVU2JreDJiVkZDYW81aWZCTlZFSlkiLCJuIjoiYXBpa2V5Y3VybCIsImlkIjoxfQ==" -d @rawdash.json http://localhost:3000/api/dashboards/db
```


# Install application with swarm (step by step using docker-machine VMs)

**Pre-requisites:**

- Install virtualbox
```
sudo apt-get install virtualbox
```
- Install docker-machine

https://docs.docker.com/machine/install-machine/#install-machine-directly

1. Create two VMs

```
docker-machine create --driver virtualbox vm1
docker-machine create --driver virtualbox vm2
```

2. Set main node for swarm.

```
docker-machine ls  #To see the virtual ip adress of the main node
docker-machine ssh vm1 "docker swarm init --advertise-addr 192.168.99.100"
```

Returns token to add workers to the swarm

3. Add worker node to the swarm
```
docker-machine ssh vm2 "docker swarm join --token SWMTKN-1-4kwbrascwqpye99rcs252okjgbj67eg5hpachf9dppkh6x5ff7-3dxfvrmtzd71yyezx2e0l1v7z 192.168.99.100:2377"

docker-machine ssh vm1 "docker node ls"
```
4. Use environment from main docker node

```
docker-machine env myvm1
eval $(docker-machine env myvm1)
```

5. Deploy application using the docker-compose file
```
docker stack deploy -c docker-compose.yml greenroute
docker stack ps greenroute
```

6. Remove application
```
docker stack rm greenroute
```

7. Close docker main node environment
```
eval $(docker-machine env -u)
```

# Further information

Alternatively, you can use the compose ***docker-compose-reverse.yml*** file using a reverse-proxy and ssl for a more secure application.

### Create a docker image for reverseproxy found at smartcity-back/src/main/docker/reverseproxy

1. Change ***server_name*** in ***nginx.conf*** for domain name to use.

**TODO Further changes to include ssl**

2. Build image

  ```
  sudo docker build -t reverseproxy .
  ```

***Notice that the docker service references in the src/main/docker/reverseproxy/nginx.conf file corresponds to the previous deployed services***.
