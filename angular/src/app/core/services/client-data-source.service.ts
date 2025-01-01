import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "@core/modules/openapi";



interface DatasourceService<T> {
  get(): BehaviorSubject<T>
  refresh(): void
}

@Injectable({
  providedIn: 'root'
})
export class ClientDataSourceService {
  private source: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);

  constructor(private httpClient: HttpClient) {
    let save = localStorage.getItem('client_data')
    if (save) {
      this.source.next(JSON.parse(save));
    }
    this.refresh();
  }

  get() {
    return this.source.asObservable();
  }

  refresh() {
    this.httpClient.get<User>('/api/v1/users/client', {
      observe: 'response',
    }).subscribe(data =>{
      if (data.status === 200) {
        localStorage.setItem('client_data', JSON.stringify(data.body));
        this.source.next(data.body || null);
      }
    })
  }


}
