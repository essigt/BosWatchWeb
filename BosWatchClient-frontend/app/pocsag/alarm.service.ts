import { Injectable }    from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

//import 'rxjs/add/operator/toPromise';

import { Alarm } from './alarm';

@Injectable()
export class AlarmService {
  private alarmsUrl = 'http://localhost:8080/BosWatch/resources/pocsag/';  // URL to web api
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) { }

  update(alarm: Alarm): Promise<Alarm> {
    const url = `${this.alarmsUrl}/${alarm.id}`;
    return this.http
      .put(url, JSON.stringify(alarm), {headers: this.headers})
      .toPromise()
      .then(() => alarm)
      .catch(this.handleError);      
  }

  getAlarms(): Observable<Alarm[]> {
    return this.http
              .get(this.alarmsUrl)
               .map((r: Response) => r.json() as Alarm[]);
  }

  getLatest(): Observable<Alarm> {
    return this.http
              .get(this.alarmsUrl + 'latest')
               .map((r: Response) => r.json() as Alarm);
  }


  create(name: string): Promise<Alarm> {
    return this.http
      .post(this.alarmsUrl, JSON.stringify({name: name}), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    let url = `${this.alarmsUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
