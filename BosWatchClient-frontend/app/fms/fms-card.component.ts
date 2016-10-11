import { Component, Input, OnInit } from '@angular/core';
import { Router }            from '@angular/router';
import { Observable }        from 'rxjs/Observable';
import { Subject }           from 'rxjs/Subject';

import { Fms } from './fms';

@Component({
    selector: 'fms-card',
    templateUrl: 'app/fms/fms-card.component.html',
    styleUrls:  ['app/fms/fms-card.component.css'],
    //providers: [HeroSearchService]
})
export class FmsCardComponent /*implements OnInit*/ {

    @Input()
    fms: Fms;

}
