import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";

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

  getGfpExp(fieldPrime: number, fieldElement: number, exp: number) {
    return this.http.get<number>(`/finite-fields/gfp/exp?fieldPrime=${fieldPrime}&fieldElement=${fieldElement}&exp=${exp}`);
  }

  getGfpMul(fieldPrime: number, fieldFirstElement: number, fieldSecondElement: number) {
    return this.http.get<number>(`/finite-fields/gfp/mul?fieldPrime=${fieldPrime}&fieldFirstElement=${fieldFirstElement}&fieldSecondElement=${fieldSecondElement}`);
  }

  getGfpNeg(fieldPrime: number, fieldElement: number) {
    return this.http.get<number>(`/finite-fields/gfp/neg?fieldPrime=${fieldPrime}&fieldElement=${fieldElement}`);
  }

  getGfpMulPoly(fieldPrime: number, firstPoly: string, secondPoly: string) {
    return this.http.post<MulPolyResponse>(`/finite-fields/gfp/mul-poly`, {
      fieldPrime: fieldPrime,
      polyFirst: firstPoly,
      polySecond: secondPoly
    });
  }

  getGfpDivPoly(fieldPrime: number, divisible: string, divisor: string) {
    return this.http.post<DivPolyResponse>(`/finite-fields/gfp/div-poly`, {
      fieldPrime: fieldPrime,
      divisible:  divisible,
      divisor: divisor
    });
  }

  getGfpPolyValue(fieldPrime: number, poly: string, point: number): Observable<number> {
    return this.http.post<number>(`/finite-fields/gfp/poly-val`,  {
      fieldPrime: fieldPrime,
      point:  point,
      poly: poly
    });
  }
}

export class MulPolyResponse {
  res?: string
}

export class DivPolyResponse {
  result?: string
  rest?: string
}
