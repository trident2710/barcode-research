import {Component} from '@angular/core';
import {ReedSolomonOperationsService} from "../../services/reed-solomon-operations-service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'reed-solomon-generator-poly',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './reed-solomon-generator-poly.component.html',
  styleUrl: './reed-solomon-generator-poly.component.css'
})
export class ReedSolomonGeneratorPolyComponent {
  primitiveElement: number = 2;
  field: number = 11;
  power: number = 6;
  result: string = "x^6 + 6x^5 + 5x^4 + 7x^3 + 2x^2 + 8x + 2";

  constructor(private reedSolomonOperationsService: ReedSolomonOperationsService) { }

  findPolyVal() {
    this.reedSolomonOperationsService.getReedSolomonGeneratorPoly(this.field, this.primitiveElement, this.power).subscribe({
      next: value => {
        this.result = value.poly ?? 'error'
      }
    });
  }
}

