import {Component} from '@angular/core';
import {GfOperationsService} from "../../services/gf-operations.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'gfp-add',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './gfp-add.component.html',
  styleUrl: './gfp-add.component.css'
})
export class GfpAddComponent {
  fieldSumFirstArgument: number = 2;
  fieldSumSecondArgument: number = 4;
  field: number = 5;
  sum: number = 1;

  constructor(private gfOperationsService: GfOperationsService) { }

  findSum() {
    this.gfOperationsService.getGfpSum(this.field, this.fieldSumFirstArgument, this.fieldSumSecondArgument).subscribe({
      next: value => this.sum = value,
      error: err => console.error('Error while calculating sum: ' + err)
    });
  }
}
