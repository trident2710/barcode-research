import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-div-poly',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-div-poly.component.html',
  styleUrl: './gfp-div-poly.component.css'
})
export class GfpDivPolyComponent {
  divisible: string = 'x^2 + 2x + 2';
  divisor: string = 'x + 1';
  field: number = 5;
  result: string = 'x + 1';
  rest: string = '1';

  constructor(private gfOperationsService: GfOperationsService) { }

  findMul() {
    this.gfOperationsService.getGfpDivPoly(this.field, this.divisible, this.divisor).subscribe({
      next: value => {
        this.result = value.result ?? 'error'
        this.rest = value.rest ?? 'error'
      }
    });
  }
}
