import { Component, Input, OnInit } from '@angular/core';
import { Router }            from '@angular/router';
import { Observable }        from 'rxjs/Observable';
import { Subject }           from 'rxjs/Subject';

import { Fms } from './fms';
import { FmsGroup } from './fms-group';

@Component({
    selector: 'fms-group-card',
    templateUrl: 'app/fms/fms-group-card.component.html',
   // styleUrls:  ['app/fms/fms-group-card.component.css'],
})
export class FmsGroupCardComponent /*implements OnInit*/ {

    @Input()
    group: FmsGroup;

}
