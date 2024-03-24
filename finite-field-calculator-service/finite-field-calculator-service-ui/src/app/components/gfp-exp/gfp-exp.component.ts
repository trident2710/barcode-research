import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-exp',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-exp.component.html',
  styleUrl: './gfp-exp.component.css'
})
export class GfpExpComponent {
  fieldElement: number = 2;
  exp: number = 2;
  field: number = 5;
  result: number = 4;

  constructor(private gfOperationsService: GfOperationsService) { }

  findExp() {
    this.gfOperationsService.getGfpExp(this.field, this.fieldElement, this.exp).subscribe({
      next: value => this.result = value,
      error: err => console.error('Error while calculating exp: ' + err)
    });
  }
}
