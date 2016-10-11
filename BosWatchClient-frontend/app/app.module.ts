import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { HttpModule }    from '@angular/http';

import './rxjs-extensions';


import { AppComponent }        from './app.component';
import { AlarmsComponent } from './pocsag/alarms.component';
import { AlarmListItemComponent } from './pocsag/alarm-list-item.component';
import { MapComponent } from './pocsag/map.component';
import { MenubarComponent } from './common/menubar.component';
import { AlarmDetailsComponent } from './pocsag/alarm-details.component';

import { FmsComponent } from './fms/fms.component';
import { FmsCardComponent } from './fms/fms-card.component';
import { FmsGroupCardComponent } from './fms/fms-group-card.component';

import { AlarmService }         from './pocsag/alarm.service';
import { FmsService }         from './fms/fms.service';

import { routing } from './app.routing';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing,
  ],
  declarations: [
    AppComponent,
    AlarmsComponent,
    AlarmListItemComponent,
    MapComponent,
    MenubarComponent,
    AlarmDetailsComponent,
    FmsComponent,
    FmsCardComponent,
    FmsGroupCardComponent,
  ],
  providers: [
    AlarmService,
    FmsService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}
