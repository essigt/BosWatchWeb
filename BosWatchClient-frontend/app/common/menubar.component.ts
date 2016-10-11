 import { Component, Input, OnInit } from '@angular/core';
 import { ActivatedRoute, Params } from '@angular/router';


 @Component({
   selector: 'my-menubar',
   templateUrl: 'app/common/menubar.component.html',
   styleUrls: ['app/common/menubar.component.css']
 })

 export class MenubarComponent {

   constructor(
     private route: ActivatedRoute) {
   }




 }
