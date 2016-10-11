import { Component, OnInit, Input } from '@angular/core';

import {Alarm} from "./alarm";
declare var google: any;

//Source: http://stackoverflow.com/questions/37326572/how-can-i-integrate-google-maps-apis-inside-an-angular-2-component
@Component({
    selector: 'my-map',
    template: `    
    <div id="googleMap" style="width:100%;height:75%;">
    </div>
    <h2>Dauer: {{durationText}} - {{distanceText}}</h2>
    `,
    styleUrls: ['app/pocsag/map.component.css']
})
export class MapComponent {
    private directionsService = new google.maps.DirectionsService;
    private directionsDisplay = new google.maps.DirectionsRenderer;

    _alarm: Alarm;
    durationText : string = 'unbekannt';
    distanceText : string = 'unbekannt';

    @Input()
    set alarm(alarm: Alarm) {
        this._alarm = alarm;
        if(alarm !== undefined)
          this.calculateRoute(alarm);
    }
    get alarm() { return this.alarm; }

    constructor() { }

    ngOnInit() {
        var mapProp = {
            center: new google.maps.LatLng(51.508742, -0.120850),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

        this.directionsDisplay.setMap(map);
        
    }



    calculateRoute(alarm: Alarm): void {
        var that = this;
        if(alarm.addr === undefined) {
            that.directionsDisplay.setDirections({routes: []});
            return;
        }

        this.directionsService.route({
            origin: 'Hardstraße 5, Mühlburg',
            destination: alarm.addr,
            travelMode: google.maps.TravelMode.DRIVING
        }, function(response, status) {
            if (status === google.maps.DirectionsStatus.OK) {
                
                that.directionsDisplay.setDirections(response);
                that.durationText = response.routes[0].legs[0].duration.text;
                that.distanceText = response.routes[0].legs[0].distance.text;
                console.log('Responce: ');
                console.log(response);
            } else {
                console.log('Directions request failed due to ' + status);
            }
        });

    }

}
