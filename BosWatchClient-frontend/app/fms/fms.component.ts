import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable }        from 'rxjs/Observable';

import { Fms } from './fms';
import { FmsGroup } from './fms-group';
import { FmsService } from './fms.service';

@Component({
  selector: 'my-fms',
  templateUrl: 'app/fms/fms.component.html',
  styleUrls:  ['app/fms/fms.component.css']
})


export class FmsComponent implements OnInit{
  selectedFms: Fms;
  latestFms : Fms;

  groups : Observable<FmsGroup[]>;

  fmsList : Observable<Fms[]>;
  fmsHistory : Observable<Fms[]>;

  constructor(
     private router: Router,
     private fmsService: FmsService) { }

  ngOnInit(): void {
    this.getFmsList();
    setInterval(() => this.checkUpdate(), 1000);
  }

  checkUpdate(): void {
    this.fmsService.getLatest().subscribe( (fms) => {
      if(this.latestFms === undefined || fms.id !== this.latestFms.id) {      
        this.getFmsList(); //Fetch new Alarms
        this.latestFms = fms;
        this.onSelect(this.latestFms);        
      }
    });    
  }

  getFmsList(): void {
    //this.fmsList = this.fmsService.getFmsList();
    this.groups = this.fmsService.getFmsList();
    this.fmsHistory = this.fmsService.getFmsHistory();
  }

  onSelect(fms: Fms): void {
    this.selectedFms = fms;
  }

}
