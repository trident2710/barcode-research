import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GfOperationsService {

  constructor(private http: HttpClient) {
  }

  getGfpInverse(fieldPrime: number, fieldElement: number) {
    return this.http.get<number>(`/finite-fields/gfp/inverse?fieldPrime=${fieldPrime}&fieldElement=${fieldElement}`);
  }

  getGfpSum(fieldPrime: number, fieldFirstElement: number, fieldSecondElement: number) {
    return this.http.get<number>(`/finite-fields/gfp/sum?fieldPrime=${fieldPrime}&fieldFirstElement=${fieldFirstElement}&fieldSecondElement=${fieldSecondElement}`);
  }
}