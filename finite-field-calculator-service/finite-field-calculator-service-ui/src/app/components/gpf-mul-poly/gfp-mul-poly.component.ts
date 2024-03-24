import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";
import {throwError} from "rxjs";

@Component({
  selector: 'gfp-mul-poly',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-mul-poly.component.html',
  styleUrl: './gfp-mul-poly.component.css'
})
export class GfpMulPolyComponent {
  firstPoly: string = 'x + 1';
  secondPoly: string = 'x + 1';
  field: number = 5;
  res: string = 'x^2 + 2x + 1';

  constructor(private gfOperationsService: GfOperationsService) { }

  findMul() {
    this.gfOperationsService.getGfpMulPoly(this.field, this.firstPoly, this.secondPoly).subscribe({
      next: value => this.res = value.res ?? 'error'
    });
  }
}
