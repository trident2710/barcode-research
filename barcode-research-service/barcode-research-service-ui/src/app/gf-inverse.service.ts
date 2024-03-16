import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GfInverseService {

  constructor(private http: HttpClient) {
  }
  getInverse(value: number, field: number) {
    return this.http.get<number>(`/finite-fields/gfp/inverse?fieldPrime=${field}&fieldElement=${value}`);
  }
}
