import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-mul',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-mul.component.html',
  styleUrl: './gfp-mul.component.css'
})
export class GfpMulComponent {
  fieldFirstArgument: number = 2;
  fieldSecondArgument: number = 4;
  field: number = 5;
  res: number = 3;

  constructor(private gfOperationsService: GfOperationsService) { }

  findMul() {
    this.gfOperationsService.getGfpMul(this.field, this.fieldFirstArgument, this.fieldSecondArgument).subscribe({
      next: value => this.res = value,
      error: err => console.error('Error while calculating mul: ' + err)
    });
  }
}
