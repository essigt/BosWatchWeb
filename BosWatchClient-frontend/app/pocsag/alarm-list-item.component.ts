import { Component, Input, OnInit } from '@angular/core';
import { Router }            from '@angular/router';
import { Observable }        from 'rxjs/Observable';
import { Subject }           from 'rxjs/Subject';

import { Alarm } from './alarm';

@Component({
    selector: 'alarm-list-item',
    templateUrl: 'app/pocsag/alarm-list-item.component.html',
    styleUrls:  ['app/pocsag/alarm-list-item.component.css'],
    //providers: [HeroSearchService]
})
export class AlarmListItemComponent /*implements OnInit*/ {

    @Input()
    alarm: Alarm;

    gotoDetail(alarm: Alarm): void {
        let link = ['/detail', alarm.id];
        //this.router.navigate(link);
    }
}
