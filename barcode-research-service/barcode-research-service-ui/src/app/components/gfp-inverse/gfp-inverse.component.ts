import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {GfOperationsService} from "../../services/gf-operations.service";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'gfp-inverse',
  standalone: true,
  imports: [RouterOutlet, FormsModule],
  templateUrl: './gfp-inverse.component.html',
  styleUrl: './gfp-inverse.component.css'
})
export class GfpInverseComponent {
  fieldValue: number = 2;
  field: number = 5;
  inverse: number = 3;

  constructor(private gfOperationsService: GfOperationsService) { }

  findInverse() {
    this.gfOperationsService.getGfpInverse(this.field, this.fieldValue).subscribe({
      next: value => this.inverse = value,
      error: err => console.error('Error while calculating inverse: ' + err)
    });
  }
}
