 import { Component, Input, OnInit } from '@angular/core';
 import { ActivatedRoute, Params } from '@angular/router';

import { Alarm } from './alarm';

 @Component({
   selector: 'alarm-details',
   //templateUrl: 'app/menubar.component.html',
   template: `<div class="details">
      <h1>{{alarm.msg}}</h1>
      <span class="label">Address:</span><span>{{alarm.addr}}</span><br>
      <span class="label">RICs:</span><span>{{alarm.ric}}</span>
    </div>`,
   styleUrls: ['app/pocsag/alarm-details.component.css']
 })

 export class AlarmDetailsComponent {

     @Input()
     alarm : Alarm;

   constructor(
     private route: ActivatedRoute) {
   }




 }
