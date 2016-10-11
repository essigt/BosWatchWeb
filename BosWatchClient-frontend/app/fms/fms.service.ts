import { Injectable }    from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

//import 'rxjs/add/operator/toPromise';

import { Fms } from './fms';
import { FmsGroup } from './fms-group';

@Injectable()
export class FmsService {
  private fmsUrl = 'http://localhost:8080/BosWatch/resources/fms/';  // URL to web api
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) { }


  getFmsList(): Observable<FmsGroup[]> {
    return this.http
              .get(this.fmsUrl + 'current')
               .map((r: Response) => r.json() as FmsGroup[]);
  }

  getLatest(): Observable<Fms> {
    return this.http
              .get(this.fmsUrl + 'latest')
               .map((r: Response) => r.json() as Fms);
  }


  getFmsHistory(): Observable<Fms[]> {
    return this.http
              .get(this.fmsUrl)
               .map((r: Response) => r.json() as Fms[]);    
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
