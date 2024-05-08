import {Component} from '@angular/core';
import {ReedSolomonOperationsService} from "../../services/reed-solomon-operations-service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'reed-solomon-error-locators-poly',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './reed-solomon-error-locators-poly.component.html',
  styleUrl: './reed-solomon-error-locators-poly.component.css'
})
export class ReedSolomonErrorLocatorsPolyComponent {
  field: number = 11;
  errorsCount: number = 2;
  errorsSyndrome: string = "3,1,0,3";
  result: string = "7x^2 + 8x + 1";

  constructor(private reedSolomonOperationsService: ReedSolomonOperationsService) { }

  findErrorLocatorsPoly() {
    this.reedSolomonOperationsService.getErrorLocatorsPoly(this.field, this.parseErrorsSyndrome(), this.errorsCount).subscribe({
      next: value => {
        this.result = value.poly ?? 'error'
      }
    });
  }

  parseErrorsSyndrome() {
    return this.errorsSyndrome.split(",").map(x => Number(x)).reverse()
  }
}

