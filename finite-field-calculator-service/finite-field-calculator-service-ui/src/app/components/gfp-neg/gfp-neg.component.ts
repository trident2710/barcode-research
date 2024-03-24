import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-neg',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-neg.component.html',
  styleUrl: './gfp-neg.component.css'
})
export class GfpNegComponent {
  fieldElement: number = 2;
  field: number = 5;
  result: number = 3;

  constructor(private gfOperationsService: GfOperationsService) { }

  findNeg() {
    this.gfOperationsService.getGfpNeg(this.field, this.fieldElement).subscribe({
      next: value => this.result = value,
      error: err => console.error('Error while calculating neg: ' + err)
    });
  }
}
