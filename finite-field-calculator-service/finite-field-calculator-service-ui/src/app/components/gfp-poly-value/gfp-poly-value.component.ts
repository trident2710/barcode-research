import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-poly-value',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-poly-value.component.html',
  styleUrl: './gfp-poly-value.component.css'
})
export class GfpPolyValueComponent {
  poly: string = 'x^2 + 2x + 2';
  point: number = 2;
  field: number = 5;
  result: number = 0;

  constructor(private gfOperationsService: GfOperationsService) { }

  findPolyVal() {
    this.gfOperationsService.getGfpPolyValue(this.field, this.poly, this.point).subscribe({
      next: value => {
        this.result = value ?? 'error'
      }
    });
  }
}

