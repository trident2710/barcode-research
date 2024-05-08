import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReedSolomonOperationsService {

  constructor(private http: HttpClient) {
  }

  getReedSolomonGeneratorPoly(fieldPrime: number, primitiveElement: number, power: number) {
    return this.http.get<PolyResponse>(`/reed-solomon/generator-poly?fieldPrime=${fieldPrime}&primitiveElement=${primitiveElement}&power=${power}`);
  }

  getErasureLocatorsPoly(fieldPrime: number, primitiveElement: number, erasurePositions: number[]) {
    return this.http.post<PolyResponse>(`/reed-solomon/erasure-locators-poly`,  {
      fieldPrime: fieldPrime,
      primitiveElement:  primitiveElement,
      erasurePositions: erasurePositions
    });
  }
}

export class PolyResponse {
  poly?: string
}
