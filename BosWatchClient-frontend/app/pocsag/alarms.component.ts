import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable }        from 'rxjs/Observable';


import { Alarm } from './alarm';
import { AlarmService } from './alarm.service';

@Component({
  selector: 'my-alarms',
  templateUrl: 'app/pocsag/alarms.component.html',
  styleUrls:  ['app/pocsag/alarms.component.css']
})


export class AlarmsComponent implements OnInit{
  selectedAlarm: Alarm;
  latestAlarm : Alarm;

  alarms : Observable<Alarm[]>;

  constructor(
     private router: Router,
     private alarmService: AlarmService) {}

  ngOnInit(): void {
    this.getAlarms();
    setInterval(() => this.checkUpdate(), 1000);
  }


  checkUpdate(): void {
    this.alarmService.getLatest().subscribe( (a) => {
      if(this.latestAlarm === undefined || a.id !== this.latestAlarm.id) {      
        this.getAlarms(); //Fetch new Alarms
        this.latestAlarm = a;
        this.onSelect(this.latestAlarm);        
      }
    });    
  }

  getAlarms(): void {
    this.alarms = this.alarmService.getAlarms();
  }

  onSelect(alarm: Alarm): void {
    this.selectedAlarm = alarm;
  }

}
