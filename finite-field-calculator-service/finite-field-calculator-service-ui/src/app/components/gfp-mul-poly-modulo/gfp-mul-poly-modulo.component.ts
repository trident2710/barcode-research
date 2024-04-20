import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-mul-poly-modulo',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-mul-poly-modulo.component.html',
  styleUrl: './gfp-mul-poly-modulo.component.css'
})
export class GfpMulPolyModuloComponent {
  firstPoly: string = 'x + 1';
  secondPoly: string = 'x + 1';
  modulo: string = 'x^2'
  field: number = 5;
  res: string = '2x + 1';

  constructor(private gfOperationsService: GfOperationsService) { }

  findMulByModulo() {
    this.gfOperationsService.getGfpMulPolyByModulo(this.field, this.firstPoly, this.secondPoly, this.modulo).subscribe({
      next: value => this.res = value.res ?? 'error'
    });
  }
}

