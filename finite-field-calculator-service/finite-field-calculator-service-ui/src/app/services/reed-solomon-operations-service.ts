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
}

export class PolyResponse {
  poly?: string
}
