import {Component} from '@angular/core';
import {ReedSolomonOperationsService} from "../../services/reed-solomon-operations-service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'reed-solomon-erasure-locators-poly',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './reed-solomon-erasure-locators-poly.component.html',
  styleUrl: './reed-solomon-erasure-locators-poly.component.css'
})
export class ReedSolomonErasureLocatorsPolyComponent {
  primitiveElement: number = 2;
  field: number = 11;
  erasurePositions: string = "4,7";
  result: string = "2x^2 + 10x + 1";

  constructor(private reedSolomonOperationsService: ReedSolomonOperationsService) { }

  findErasureLocatorsPoly() {
    this.reedSolomonOperationsService.getErasureLocatorsPoly(this.field, this.primitiveElement, this.parseErasurePositions()).subscribe({
      next: value => {
        this.result = value.poly ?? 'error'
      }
    });
  }

  parseErasurePositions() {
    return this.erasurePositions.split(",").map(x => Number(x))
  }
}

